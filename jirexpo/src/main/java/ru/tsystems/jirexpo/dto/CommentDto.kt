package ru.tsystems.jirexpo.dto;

data class CommentDto(
        var task: String? = null,
        var commentDate: String? = null,
        var author: EmployeeDto? = null,
        var comment: String? = null
)
