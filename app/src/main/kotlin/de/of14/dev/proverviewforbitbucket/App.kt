package de.of14.dev.proverviewforbitbucket

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.of14.dev.proverviewforbitbucket.model.local.OutputFormat
import de.of14.dev.proverviewforbitbucket.model.local.OutputFormat.Companion.toOutputFormat
import de.of14.dev.proverviewforbitbucket.model.local.PrInfo
import de.of14.dev.proverviewforbitbucket.model.local.ProjectRepo
import de.of14.dev.proverviewforbitbucket.model.local.TableCell
import de.of14.dev.proverviewforbitbucket.model.mattermost.MattermostSlashResponseBody
import de.of14.dev.proverviewforbitbucket.service.BitbucketService
import de.of14.dev.proverviewforbitbucket.utils.flattenedComments
import de.of14.dev.proverviewforbitbucket.utils.flattenedWithChildComments
import de.of14.dev.proverviewforbitbucket.utils.getFirstname
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import okhttp3.OkHttpClient
import org.slf4j.event.Level
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

private val client = OkHttpClient.Builder()
    .followRedirects(true)
    .followSslRedirects(true)
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .build()

private val gson: Gson = GsonBuilder()
    .setPrettyPrinting()
    .create()

private val bitbucketService = BitbucketService(client, gson)

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(CallLogging) {
            level = Level.INFO
        }
        install(StatusPages) {
            exception<Throwable> { e ->
                call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
            }
        }
        routing {
            get(path = "/message") {
                val output = call.parameters["output"]
                    .let { it ?: throw BadRequestException("Query parameter 'output' is missing.") }
                    .toOutputFormat()
                    ?: throw BadRequestException("Unknown output format '${call.parameters["output"]}'")

                val repos = call.parameters["repos"]
                    ?.let { ProjectRepo.parse(it) }
                    ?.let { if (it.isNotEmpty()) it else throw BadRequestException("Could not read information from query parameter 'repos'") }
                    ?: throw BadRequestException("Query parameter 'repos' is missing.")

                val prs = repos.flatMap { bitbucketService.getPrInfoFromBitbucket(it) }

                when (output) {
                    OutputFormat.Mattermost -> {
                        val message = generateMarkdownOutput(prs)
                        call.respondText(contentType = ContentType.parse("application/json")) {
                            @Suppress("NAME_SHADOWING") val message = if (message.length < 16383) message
                            else "There are too many PRs. I gave up. Get your shit together!"
                            gson.toJson(MattermostSlashResponseBody(text = message))
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}

private fun generateMarkdownOutput(prs: List<PrInfo>): String {
    if (prs.isEmpty()) return "Failed to read Pull Requests from Bitbucket"

    val table: String = prs
        .sortedBy { it.value.createdDate }
        .map { prInfo ->
            listOf(
                TableCell(
                    "ID",
                    "[${prInfo.value.id}](${prInfo.value.links.self.firstOrNull()?.href ?: ""})"
                ),
                TableCell("Title", prInfo.value.title.chunked(30).let {
                    (it.firstOrNull() ?: "") + if (it.size > 1) ".." else ""
                }),
                TableCell(
                    "to Branch",
                    prInfo.value.toRef.displayId + if (prInfo.mergeTestResult.canMerge) " :white_check_mark:" else ""
                ),
                TableCell(
                    "Time",
                    run {
                        val created = ChronoUnit.DAYS.between(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(prInfo.value.createdDate),
                                TimeZone.getDefault().toZoneId()
                            ),
                            LocalDateTime.now()
                        ).toString() + "d"

                        val updated = ChronoUnit.DAYS.between(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(prInfo.value.updatedDate),
                                TimeZone.getDefault().toZoneId()
                            ),
                            LocalDateTime.now()
                        ).let {
                            val prefix = if (it >= 3) "**" else ""
                            val postfix = if (it >= 3) "**" else ""
                            "↻$prefix${it}d$postfix"
                        }

                        "$created ($updated)"
                    }
                ),
                TableCell(
                    "Open Tasks",
                    run {
                        val openTaskCount = prInfo.value.properties.openTaskCount
                        val resolvedTaskCount = prInfo.value.properties.resolvedTaskCount
                        val allTasksCount = openTaskCount + resolvedTaskCount
                        "`$openTaskCount (of $allTasksCount)` "
                    }
                ),
                // fill information for the author
                TableCell(
                    prInfo.value.author.user.getFirstname(),
                    run {
                        val authorStates = ArrayList<String>()

                        // author tag
                        authorStates.add("`author`")

                        // needs work
                        prInfo.activities
                            .flattenedComments()
                            .filter { it.severity == "BLOCKER" }
                            .filter { it.state == "OPEN" }
                            .map { comment ->
                                val latestAnswer = comment.flattenedWithChildComments()
                                    .filter { it.id != comment.id }
                                    .maxByOrNull { it.createdDate }
                                latestAnswer == null || latestAnswer.author.name != prInfo.value.author.user.name
                            }
                            .let { if (it.contains(true)) authorStates.add(":wrench:") }


                        if (prInfo.mergeTestResult.outcome == "CONFLICTED") {
                            authorStates.add(":warning:")
                        }

                        authorStates.joinToString(separator = " ")
                    }
                )
            ) + prInfo.value.reviewers
                .sortedBy { it.user.name }
                .map { reviewer ->
                    val reviewStates = ArrayList<String>()

                    // type of the review
                    reviewStates.add(
                        when {
                            reviewer.approved -> ":green_heart:"
                            reviewer.status == "NEEDS_WORK" -> ":broken_heart:"
                            else -> ":grey_question:"
                        }
                    )

                    // new commits available
                    if (reviewer.lastReviewedCommit.isNotBlank() && reviewer.lastReviewedCommit != prInfo.value.fromRef.latestCommit) {
                        reviewStates.add(":new:")
                    }

                    // rereview needed
                    prInfo.activities
                        .flattenedComments()
                        .asSequence()
                        .filter { it.severity == "BLOCKER" }
                        .filter { it.state == "OPEN" }
                        .filter { it.author.name == reviewer.user.name }
                        .map { comment ->
                            val latestAnswer = comment.flattenedWithChildComments()
                                .filter { it.id != comment.id }
                                .maxByOrNull { it.createdDate }
                            latestAnswer != null && latestAnswer.author.name != reviewer.user.name
                        }
                        .any { it }
                        .let { if (it) reviewStates.add(":ballot_box_with_check:") }

                    TableCell(
                        reviewer.user.getFirstname(),
                        reviewStates.joinToString(separator = " ")
                    )
                }

        }
        .let { rows ->
            val allHeaders = rows
                .flatten()
                .map { it.header }
                .distinct()

            ArrayList<String>().apply {
                add(allHeaders.joinToString(prefix = "| ", separator = " | ", postfix = " |"))
                add(allHeaders.joinToString(prefix = "| ", separator = " | ", postfix = " |") { "---" })
                addAll(
                    rows.map { cells ->
                        allHeaders
                            .map { header ->
                                cells.firstOrNull { it.header == header }
                                    ?: TableCell(
                                        header,
                                        ""
                                    )
                            }
                            .joinToString(prefix = "| ", separator = " | ", postfix = " |") { it.content }
                    }
                )
            }.joinToString(separator = "\n")
        }

    val legend =
        """
            Legend: 
            
            | | | | | | | | | 
            | --- | --- | --- | --- | --- | --- | --- | --- | 
            | :green_heart: | Approved | :broken_heart: | Needs Work | :warning: | Merge conflict | :ballot_box_with_check: | Answers for your Tasks | 
            | :new: | Unreviewed commits | :grey_question: | Missing Review | :wrench: | Needs Work | :white_check_mark: | Ready to Merge | 
        """.trimIndent()

    return table + "\n\n" + legend
}
