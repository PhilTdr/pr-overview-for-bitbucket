package de.of14.dev.proverviewforbitbucket.model.local

data class ProjectRepo(
    val project: String,
    val repository: String
) {
    companion object {
        fun parse(repoString: String): List<ProjectRepo> {
            return repoString
                .split(",")
                .mapNotNull {
                    if (it.contains(":")) {
                        val parts = it.split(":")
                        ProjectRepo(parts[0], parts[1])
                    } else null
                }
        }
    }
}
