package de.of14.dev.proverviewforbitbucket.utils

import de.of14.dev.proverviewforbitbucket.model.bitbucket.PrActivitiesPaged
import de.of14.dev.proverviewforbitbucket.model.bitbucket.User

fun List<PrActivitiesPaged.PrActivityValue>.flattenedComments(): List<PrActivitiesPaged.PrActivityValue.Comment> {
    return this
        .mapNotNull {
            it.comment?.let { comment ->
                flatCommentsRecursive(comment)
            }
        }
        .flatten()
}

fun PrActivitiesPaged.PrActivityValue.Comment.flattenedWithChildComments(): List<PrActivitiesPaged.PrActivityValue.Comment> {
    return flatCommentsRecursive(this)
}

private fun flatCommentsRecursive(comment: PrActivitiesPaged.PrActivityValue.Comment): List<PrActivitiesPaged.PrActivityValue.Comment> {
    if (comment.comments.isEmpty()) return listOf(comment)
    return listOf(comment) + comment.comments + comment.comments.flatMap { flatCommentsRecursive(it) }
}

fun User.getFirstname(): String {
    return displayName.split(" ").firstOrNull() ?: displayName
}
