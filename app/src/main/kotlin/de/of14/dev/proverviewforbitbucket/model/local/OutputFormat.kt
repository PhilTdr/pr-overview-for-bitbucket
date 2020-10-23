package de.of14.dev.proverviewforbitbucket.model.local

enum class OutputFormat {
    Mattermost;

    companion object {
        fun String?.toOutputFormat(): OutputFormat? {
            return OutputFormat.values()
                .firstOrNull { it.name.equals(this, true) }
        }
    }
}
