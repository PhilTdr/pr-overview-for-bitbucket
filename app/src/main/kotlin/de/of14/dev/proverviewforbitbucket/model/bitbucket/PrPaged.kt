package de.of14.dev.proverviewforbitbucket.model.bitbucket

import com.google.gson.annotations.SerializedName

data class PrPaged(
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("isLastPage")
    val isLastPage: Boolean = false,
    @SerializedName("values")
    val values: List<PullRequestValue> = listOf(),
    @SerializedName("start")
    val start: Int = 0,
    @SerializedName("nextPageStart")
    val nextPageStart: Int = 0
) {
    data class PullRequestValue(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("version")
        val version: Int = 0,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("description")
        val description: String = "",
        @SerializedName("state")
        val state: String = "",
        @SerializedName("open")
        val `open`: Boolean = false,
        @SerializedName("closed")
        val closed: Boolean = false,
        @SerializedName("createdDate")
        val createdDate: Long = 0,
        @SerializedName("updatedDate")
        val updatedDate: Long = 0,
        @SerializedName("fromRef")
        val fromRef: FromRef = FromRef(),
        @SerializedName("toRef")
        val toRef: ToRef = ToRef(),
        @SerializedName("locked")
        val locked: Boolean = false,
        @SerializedName("author")
        val author: Author = Author(),
        @SerializedName("reviewers")
        val reviewers: List<Reviewer> = listOf(),
        @SerializedName("participants")
        val participants: List<Any> = listOf(),
        @SerializedName("properties")
        val properties: Properties = Properties(),
        @SerializedName("links")
        val links: Links = Links(),
        @SerializedName("closedDate")
        val closedDate: Long = 0
    ) {
        data class FromRef(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("displayId")
            val displayId: String = "",
            @SerializedName("latestCommit")
            val latestCommit: String = "",
            @SerializedName("repository")
            val repository: Repository = Repository()
        ) {
            data class Repository(
                @SerializedName("slug")
                val slug: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("hierarchyId")
                val hierarchyId: String = "",
                @SerializedName("scmId")
                val scmId: String = "",
                @SerializedName("state")
                val state: String = "",
                @SerializedName("statusMessage")
                val statusMessage: String = "",
                @SerializedName("forkable")
                val forkable: Boolean = false,
                @SerializedName("project")
                val project: Project = Project(),
                @SerializedName("public")
                val `public`: Boolean = false,
                @SerializedName("links")
                val links: Links = Links()
            ) {
                data class Project(
                    @SerializedName("key")
                    val key: String = "",
                    @SerializedName("id")
                    val id: Int = 0,
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("public")
                    val `public`: Boolean = false,
                    @SerializedName("type")
                    val type: String = "",
                    @SerializedName("links")
                    val links: Links = Links()
                ) {
                    data class Links(
                        @SerializedName("self")
                        val self: List<Self> = listOf()
                    ) {
                        data class Self(
                            @SerializedName("href")
                            val href: String = ""
                        )
                    }
                }

                data class Links(
                    @SerializedName("clone")
                    val clone: List<Clone> = listOf(),
                    @SerializedName("self")
                    val self: List<Self> = listOf()
                ) {
                    data class Clone(
                        @SerializedName("href")
                        val href: String = "",
                        @SerializedName("name")
                        val name: String = ""
                    )

                    data class Self(
                        @SerializedName("href")
                        val href: String = ""
                    )
                }
            }
        }

        data class ToRef(
            @SerializedName("id")
            val id: String = "",
            @SerializedName("displayId")
            val displayId: String = "",
            @SerializedName("latestCommit")
            val latestCommit: String = "",
            @SerializedName("repository")
            val repository: Repository = Repository()
        ) {
            data class Repository(
                @SerializedName("slug")
                val slug: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("hierarchyId")
                val hierarchyId: String = "",
                @SerializedName("scmId")
                val scmId: String = "",
                @SerializedName("state")
                val state: String = "",
                @SerializedName("statusMessage")
                val statusMessage: String = "",
                @SerializedName("forkable")
                val forkable: Boolean = false,
                @SerializedName("project")
                val project: Project = Project(),
                @SerializedName("public")
                val `public`: Boolean = false,
                @SerializedName("links")
                val links: Links = Links()
            ) {
                data class Project(
                    @SerializedName("key")
                    val key: String = "",
                    @SerializedName("id")
                    val id: Int = 0,
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("public")
                    val `public`: Boolean = false,
                    @SerializedName("type")
                    val type: String = "",
                    @SerializedName("links")
                    val links: Links = Links()
                ) {
                    data class Links(
                        @SerializedName("self")
                        val self: List<Self> = listOf()
                    ) {
                        data class Self(
                            @SerializedName("href")
                            val href: String = ""
                        )
                    }
                }

                data class Links(
                    @SerializedName("clone")
                    val clone: List<Clone> = listOf(),
                    @SerializedName("self")
                    val self: List<Self> = listOf()
                ) {
                    data class Clone(
                        @SerializedName("href")
                        val href: String = "",
                        @SerializedName("name")
                        val name: String = ""
                    )

                    data class Self(
                        @SerializedName("href")
                        val href: String = ""
                    )
                }
            }
        }

        data class Author(
            @SerializedName("user")
            val user: User = User(),
            @SerializedName("role")
            val role: String = "",
            @SerializedName("approved")
            val approved: Boolean = false,
            @SerializedName("status")
            val status: String = ""
        )

        data class Reviewer(
            @SerializedName("user")
            val user: User = User(),
            @SerializedName("role")
            val role: String = "",
            @SerializedName("approved")
            val approved: Boolean = false,
            @SerializedName("status")
            val status: String = "",
            @SerializedName("lastReviewedCommit")
            val lastReviewedCommit: String = ""
        )

        data class Properties(
            @SerializedName("mergeResult")
            val mergeResult: MergeResult = MergeResult(),
            @SerializedName("resolvedTaskCount")
            val resolvedTaskCount: Int = 0,
            @SerializedName("openTaskCount")
            val openTaskCount: Int = 0,
            @SerializedName("commentCount")
            val commentCount: Int = 0
        ) {
            data class MergeResult(
                @SerializedName("outcome")
                val outcome: String = "",
                @SerializedName("current")
                val current: Boolean = false
            )
        }

        data class Links(
            @SerializedName("self")
            val self: List<Self> = listOf()
        ) {
            data class Self(
                @SerializedName("href")
                val href: String = ""
            )
        }
    }
}
