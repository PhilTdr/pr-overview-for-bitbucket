package de.of14.dev.proverviewforbitbucket.model.mattermost

data class MattermostSlashResponseBody(
    val response_type: String = "in_channel",
    val text: String
)
