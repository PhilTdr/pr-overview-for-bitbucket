package de.of14.dev.proverviewforbitbucket.model.local

import de.of14.dev.proverviewforbitbucket.model.bitbucket.PrActivitiesPaged
import de.of14.dev.proverviewforbitbucket.model.bitbucket.PrMergeTestResult
import de.of14.dev.proverviewforbitbucket.model.bitbucket.PrPaged

data class PrInfo(
    val repo: ProjectRepo,
    val value: PrPaged.PullRequestValue,
    val activities: List<PrActivitiesPaged.PrActivityValue>,
    val mergeTestResult: PrMergeTestResult
)
