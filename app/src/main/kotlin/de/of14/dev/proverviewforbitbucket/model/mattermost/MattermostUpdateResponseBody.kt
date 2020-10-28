package de.of14.dev.proverviewforbitbucket.model.mattermost

data class MattermostUpdateResponseBody(
    val update: Update,
) {
    data class Update(
        val message: String
    )
}
