package de.of14.dev.proverviewforbitbucket.model.bitbucket

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("emailAddress")
    val emailAddress: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("displayName")
    val displayName: String = "",
    @SerializedName("active")
    val active: Boolean = false,
    @SerializedName("slug")
    val slug: String = "",
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
