package ru.tsystems.jirexpo.dto

import java.util.*

data class TaskView(
        var keys: String? = null,
        var summary: String? = null,
        var issueType: String? = null,
        var created: Date? = null
)