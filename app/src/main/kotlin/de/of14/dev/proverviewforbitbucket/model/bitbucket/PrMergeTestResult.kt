package de.of14.dev.proverviewforbitbucket.model.bitbucket

import com.google.gson.annotations.SerializedName

data class PrMergeTestResult(
    val canMerge: Boolean = false,
    val conflicted: Boolean = false,
    val outcome: String = "",
    val properties: Properties = Properties(),
    val vetoes: List<Veto> = listOf()
) {
    data class Properties(
        @SerializedName("branch.from-ref-stability")
        val branch_from_ref_stability: String = ""
    )

    data class Veto(
        val detailedMessage: String = "",
        val summaryMessage: String = ""
    )
}
