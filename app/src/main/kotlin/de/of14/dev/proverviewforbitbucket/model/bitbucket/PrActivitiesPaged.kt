package de.of14.dev.proverviewforbitbucket.model.bitbucket

import com.google.gson.annotations.SerializedName

data class PrActivitiesPaged(
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("isLastPage")
    val isLastPage: Boolean = false,
    @SerializedName("values")
    val values: List<PrActivityValue> = listOf(),
    @SerializedName("start")
    val start: Int = 0
) {
    data class PrActivityValue(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("createdDate")
        val createdDate: Long = 0,
        @SerializedName("user")
        val user: User = User(),
        @SerializedName("action")
        val action: String = "",
        @SerializedName("commentAction")
        val commentAction: String = "",
        @SerializedName("comment")
        val comment: Comment? = null,
        @SerializedName("fromHash")
        val fromHash: String = "",
        @SerializedName("previousFromHash")
        val previousFromHash: String = "",
        @SerializedName("previousToHash")
        val previousToHash: String = "",
        @SerializedName("toHash")
        val toHash: String = "",
        @SerializedName("added")
        val added: Added = Added(),
        @SerializedName("removed")
        val removed: Removed = Removed(),
        @SerializedName("commentAnchor")
        val commentAnchor: CommentAnchor = CommentAnchor(),
        @SerializedName("diff")
        val diff: Diff = Diff()
    ) {

        data class Comment(
            @SerializedName("properties")
            val properties: Properties = Properties(),
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("version")
            val version: Int = 0,
            @SerializedName("text")
            val text: String = "",
            @SerializedName("author")
            val author: User = User(),
            @SerializedName("createdDate")
            val createdDate: Long = 0,
            @SerializedName("updatedDate")
            val updatedDate: Long = 0,
            @SerializedName("comments")
            val comments: List<Comment> = listOf(),
            @SerializedName("tasks")
            val tasks: List<Any> = listOf(),
            @SerializedName("severity")
            val severity: String = "",
            @SerializedName("state")
            val state: String = "",
            @SerializedName("permittedOperations")
            val permittedOperations: PermittedOperations = PermittedOperations(),
            @SerializedName("resolvedDate")
            val resolvedDate: Long = 0,
            @SerializedName("resolver")
            val resolver: User = User()
        ) {
            data class Properties(
                @SerializedName("repositoryId")
                val repositoryId: Int = 0
            )

            data class PermittedOperations(
                @SerializedName("editable")
                val editable: Boolean = false,
                @SerializedName("transitionable")
                val transitionable: Boolean = false,
                @SerializedName("deletable")
                val deletable: Boolean = false
            )
        }

        data class Added(
            @SerializedName("commits")
            val commits: List<Commit> = listOf(),
            @SerializedName("total")
            val total: Int = 0
        ) {
            data class Commit(
                @SerializedName("id")
                val id: String = "",
                @SerializedName("displayId")
                val displayId: String = "",
                @SerializedName("author")
                val author: User = User(),
                @SerializedName("authorTimestamp")
                val authorTimestamp: Long = 0,
                @SerializedName("committer")
                val committer: User = User(),
                @SerializedName("committerTimestamp")
                val committerTimestamp: Long = 0,
                @SerializedName("message")
                val message: String = "",
                @SerializedName("parents")
                val parents: List<Parent> = listOf(),
                @SerializedName("properties")
                val properties: Properties = Properties()
            ) {
                data class Parent(
                    @SerializedName("id")
                    val id: String = "",
                    @SerializedName("displayId")
                    val displayId: String = ""
                )

                data class Properties(
                    @SerializedName("jira-key")
                    val jiraKey: List<String> = listOf()
                )
            }
        }

        data class Removed(
            @SerializedName("commits")
            val commits: List<Any> = listOf(),
            @SerializedName("total")
            val total: Int = 0
        )

        data class CommentAnchor(
            @SerializedName("fromHash")
            val fromHash: String = "",
            @SerializedName("toHash")
            val toHash: String = "",
            @SerializedName("line")
            val line: Int = 0,
            @SerializedName("lineType")
            val lineType: String = "",
            @SerializedName("fileType")
            val fileType: String = "",
            @SerializedName("path")
            val path: String = "",
            @SerializedName("diffType")
            val diffType: String = "",
            @SerializedName("orphaned")
            val orphaned: Boolean = false
        )

        data class Diff(
            @SerializedName("source")
            val source: Any? = Any(),
            @SerializedName("destination")
            val destination: Destination = Destination(),
            @SerializedName("hunks")
            val hunks: List<Hunk> = listOf(),
            @SerializedName("truncated")
            val truncated: Boolean = false,
            @SerializedName("properties")
            val properties: Properties = Properties()
        ) {
            data class Destination(
                @SerializedName("components")
                val components: List<String> = listOf(),
                @SerializedName("parent")
                val parent: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("extension")
                val extension: String = "",
                @SerializedName("toString")
                val toString: String = ""
            )

            data class Hunk(
                @SerializedName("sourceLine")
                val sourceLine: Int = 0,
                @SerializedName("sourceSpan")
                val sourceSpan: Int = 0,
                @SerializedName("destinationLine")
                val destinationLine: Int = 0,
                @SerializedName("destinationSpan")
                val destinationSpan: Int = 0,
                @SerializedName("segments")
                val segments: List<Segment> = listOf(),
                @SerializedName("truncated")
                val truncated: Boolean = false
            ) {
                data class Segment(
                    @SerializedName("type")
                    val type: String = "",
                    @SerializedName("lines")
                    val lines: List<Line> = listOf(),
                    @SerializedName("truncated")
                    val truncated: Boolean = false
                ) {
                    data class Line(
                        @SerializedName("destination")
                        val destination: Int = 0,
                        @SerializedName("source")
                        val source: Int = 0,
                        @SerializedName("line")
                        val line: String = "",
                        @SerializedName("truncated")
                        val truncated: Boolean = false,
                        @SerializedName("commentIds")
                        val commentIds: List<Int> = listOf()
                    )
                }
            }

            data class Properties(
                @SerializedName("toHash")
                val toHash: String = "",
                @SerializedName("current")
                val current: Boolean = false,
                @SerializedName("fromHash")
                val fromHash: String = ""
            )
        }
    }
}