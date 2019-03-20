package ru.tsystems.divider.service.api.functional

import ru.tsystems.divider.entity.Comment

interface CommentBuilder {
    fun buildComment(comment: String): Comment
}