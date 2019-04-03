package ru.tsystems.jirexpo.dto

data class TaskDto(
        var keys: String? = null,
        var summary: String? = null,

        var issueType: String? = null,
        var status: String? = null,
        var priority: String? = null,
        var resolution: String? = null,

        var assignee: EmployeeDto? = null,
        var reporter: EmployeeDto? = null,
        var creater: EmployeeDto? = null,

        var created: String? = null,
        var lastViewed: String? = null,
        var updated: String? = null,
        var resolved: String? = null,
        var dueDate: String? = null,

        var originalEstimate: Int? = null,
        var remainingEstimate: Int? = null,
        var timeSpent: Int? = null,
        var workRation: Int? = null,
        var progress: Int? = null,

        var description: String? = null,

        var sumProgress: Int? = null,
        var sumTimeSpant: Int? = null,
        var sumRemainingEstimate: Int? = null,
        var sumOriginalEstimate: Int? = null,

        var epicName: String? = null,
        var epicStatus: String? = null,
        var epicColor: String? = null,
        var epicLink: String? = null,

        var sprint: String? = null,
        var orderNumber: String? = null,
        var deliveredVersion: String? = null,
        var drcNumber: String? = null,
        var keyword: String? = null,
        var fixPriority: String? = null,

        var comments: kotlin.collections.List<CommentDto>? = null,

        var affectsVersions: kotlin.collections.List<String> = ArrayList<String>(),
        var fixVersions: kotlin.collections.List<String> = ArrayList<String>(),

        var components: kotlin.collections.List<String> = ArrayList<String>(),
        var labels: kotlin.collections.List<String> = ArrayList<String>(),
        var teams: kotlin.collections.List<String> = ArrayList<String>(),

        var subTasks: kotlin.collections.List<String> = ArrayList<String>(),
        var parentTasks: kotlin.collections.List<String> = ArrayList<String>(),
        var relationTasks: kotlin.collections.List<String> = ArrayList<String>(),
        var duplicateTasks: kotlin.collections.List<String> = ArrayList<String>()
)