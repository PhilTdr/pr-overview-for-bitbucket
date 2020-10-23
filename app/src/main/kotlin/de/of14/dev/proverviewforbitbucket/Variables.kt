package de.of14.dev.proverviewforbitbucket

object Variables {
    val BITBUCKET_BASE_URL get() = getEnvVar("BITBUCKET_BASE_URL")
    val BITBUCKET_TOKEN get() = getEnvVar("BITBUCKET_TOKEN")

    private fun getEnvVar(name: String): String {
        val envVar = System.getenv(name)
        if (envVar.isNullOrBlank()) throw IllegalArgumentException("Environment variable '$name' is required")
        return envVar
    }
}
