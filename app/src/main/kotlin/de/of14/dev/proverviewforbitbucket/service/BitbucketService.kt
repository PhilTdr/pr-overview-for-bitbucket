package de.of14.dev.proverviewforbitbucket.service

import com.google.gson.Gson
import de.of14.dev.proverviewforbitbucket.Variables
import de.of14.dev.proverviewforbitbucket.model.bitbucket.PrActivitiesPaged
import de.of14.dev.proverviewforbitbucket.model.bitbucket.PrMergeTestResult
import de.of14.dev.proverviewforbitbucket.model.bitbucket.PrPaged
import de.of14.dev.proverviewforbitbucket.model.local.PrInfo
import de.of14.dev.proverviewforbitbucket.model.local.ProjectRepo
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class BitbucketService(
    private val client: OkHttpClient,
    private val gson: Gson
) {

    fun getPrInfoFromBitbucket(repo: ProjectRepo): List<PrInfo> {
        val prRequest = Request.Builder()
            .url(
                Variables.BITBUCKET_BASE_URL.toHttpUrl()
                    .newBuilder()
                    .addPathSegments("rest/api/1.0/projects/${repo.project}/repos/${repo.repository}/pull-requests")
                    .addQueryParameter("limit", "100")
                    .addQueryParameter("state", "OPEN")
                    .build()
            )
            .addHeader("Authorization", "Bearer ${Variables.BITBUCKET_TOKEN}")
            .get()
            .build()

        val prList: List<PrPaged.PullRequestValue> = client
            .newCall(prRequest).execute()
            .body?.string()
            ?.let { gson.fromJson(it, PrPaged::class.java) }
            ?.values
            ?: emptyList()

        val prActivities: Map<Int, List<PrActivitiesPaged.PrActivityValue>> = prList
            .map { pr ->
                val prActivitiesRequest = Request.Builder()
                    .url(
                        Variables.BITBUCKET_BASE_URL.toHttpUrl()
                            .newBuilder()
                            .addPathSegments("rest/api/1.0/projects/${repo.project}/repos/${repo.repository}/pull-requests/${pr.id}/activities")
                            .addQueryParameter("limit", "500")
                            .build()
                    )
                    .addHeader("Authorization", "Bearer ${Variables.BITBUCKET_TOKEN}")
                    .get()
                    .build()

                val prActivities = client
                    .newCall(prActivitiesRequest).execute()
                    .body?.string()
                    ?.let { gson.fromJson(it, PrActivitiesPaged::class.java) }
                    ?.values
                    ?: emptyList()

                pr.id to prActivities
            }
            .toMap()

        val prMergeTestResults: Map<Int, PrMergeTestResult?> = prList
            .map { pr ->
                val prMergeTestRequest = Request.Builder()
                    .url(
                        Variables.BITBUCKET_BASE_URL.toHttpUrl()
                            .newBuilder()
                            .addPathSegments("rest/api/1.0/projects/${repo.project}/repos/${repo.repository}/pull-requests/${pr.id}/merge")
                            .build()
                    )
                    .addHeader("Authorization", "Bearer ${Variables.BITBUCKET_TOKEN}")
                    .get()
                    .build()

                val prMergeTestResult = client
                    .newCall(prMergeTestRequest).execute()
                    .body?.string()
                    ?.let { gson.fromJson(it, PrMergeTestResult::class.java) }

                pr.id to prMergeTestResult
            }
            .toMap()

        assert(prList.isNotEmpty()) { "Failed to read Pull Requests from Bitbucket" }

        return prList
            .sortedBy { it.createdDate }
            .map { pr ->
                PrInfo(
                    repo = repo,
                    value = pr,
                    activities = prActivities[pr.id]
                        ?: throw IllegalStateException("Failed to read Pull Request Activities for PR-${pr.id} from Bitbucket"),
                    mergeTestResult = prMergeTestResults[pr.id]
                        ?: throw IllegalStateException("Failed to read Pull Request Merge Result for PR-${pr.id} from Bitbucket")
                )
            }
    }
}
