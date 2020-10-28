package de.of14.dev.proverviewforbitbucket.model.mattermost

data class MattermostSlashResponseBody(
    val response_type: String = "in_channel",
    val text: String,
    val attachments: List<Attachment> = emptyList()
) {
    data class Attachment(
        val actions: List<Action>
    ) {
        data class Action(
            val id: String,
            val name: String,
            val integration: Integration
        ) {
            data class Integration(
                val url: String,
                val context: Context
            ) {
                data class Context(
                    val action: String
                )
            }
        }
    }
}
