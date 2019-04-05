package ru.tsystems.jirexpo.components.impl

import ru.tsystems.jirexpo.dto.CommentDto
import ru.tsystems.jirexpo.dto.EmployeeDto
import ru.tsystems.jirexpo.dto.TaskDto
import ru.tsystems.jirexpo.dto.TaskView
import ru.tsystems.jirexpo.entity.Comment
import ru.tsystems.jirexpo.entity.Employee
import ru.tsystems.jirexpo.entity.Feature
import ru.tsystems.jirexpo.entity.Task
import java.util.*
import kotlin.streams.toList

private val UN_EMP = Employee(firstname = "unassigned", secondname = "unassigned")

class Mapper {
    companion object {
        @JvmStatic
        fun convertToView(entity: Task?) = if (entity == null) null else TaskView(
                keys = entity.keys,
                created = entity.created,
                issueType = entity.issueType?.title,
                summary = entity.summary
        )

        /**
         * Convert {@link Task} which entity to dto. Some links changed to a string
         *
         * @param entity
         *            {@link Task} which should be converted.
         * @return {@link TaskDto} with all fields from entity.
         * @see Task
         * @see TaskDto
         */
        @JvmStatic
        fun convertToDto(entity: Task) = TaskDto(
                //main fields
                keys = entity.keys, // 1
                summary = entity.summary,


                issueType = getOrNull(entity.issueType),
                status = getOrNull(entity.consumables?.status),
                priority = getOrNull(entity.consumables?.priority),// 5
                resolution = getOrNull(entity.consumables?.resolution),
                //Employees
                assignee = convertToDto(entity.consumables?.workers?.assignee ?: UN_EMP),
                reporter = convertToDto(entity.consumables?.workers?.reporter ?: UN_EMP),
                creater = convertToDto(entity.consumables?.workers?.creater ?: UN_EMP),
                //Times
                created = convertTo(entity.created),// 10
                lastViewed = convertTo(entity.consumables?.dates?.lastViewed),
                updated = convertTo(entity.consumables?.dates?.updated),// 11
                resolved = convertTo(entity.consumables?.dates?.resolved),
                dueDate = convertTo(entity.consumables?.dates?.dueDate),
                //Characteristics
                originalEstimate = entity.consumables?.statistics?.originalEstimate,
                remainingEstimate = entity.consumables?.statistics?.remainingEstimate,
                timeSpent = entity.consumables?.statistics?.timeSpent,// 20
                workRation = entity.consumables?.statistics?.workRation,
                progress = entity.consumables?.statistics?.progress,
                //Description
                description = entity.consumables?.description,
                //Sum Characteristics
                sumProgress = entity.consumables?.statistics?.sumProgress,// 25
                sumTimeSpant = entity.consumables?.statistics?.sumTimeSpant,
                sumRemainingEstimate = entity.consumables?.statistics?.sumRemainingEstimate,
                sumOriginalEstimate = entity.consumables?.statistics?.sumOriginalEstimate,
                //Epics
                epicName = entity.consumables?.epics?.epicName,
                epicStatus = getOrNull(entity.consumables?.epics?.epicStatus),
                epicColor = getOrNull(entity.consumables?.epics?.epicColor),
                epicLink = entity.consumables?.epics?.epicLink,// 35

                sprint = getOrNull(entity.consumables?.sprint),
                orderNumber = entity.consumables?.orderNumber,
                deliveredVersion = entity.consumables?.deliveredVersion?.title,
                drcNumber = entity.consumables?.drcNumber,
                keyword = getOrNull(entity.consumables?.keyword),
                fixPriority = getOrNull(entity.consumables?.fixPriority),// 40
                //Comments
                comments = entity.comments.stream().map { convertToDto(it) }.toList(),
                //Versions
                affectsVersions = featureSetToList(entity.consumables?.affectsVersions),
                fixVersions = featureSetToList(entity.consumables?.fixVersions),// 15
                //MTM components
                components = featureSetToList(entity.consumables?.components),// 16
                labels = featureSetToList(entity.consumables?.labels),
                teams = featureSetToList(entity.consumables?.teams),// 30
                //Links to another tasks
                subTasks = subTaskSetToList(entity.subTasks),
                parentTasks = subTaskSetToList(entity.parentTasks),
                relationTasks = subTaskSetToList(entity.relationTasks),
                duplicateTasks = subTaskSetToList(entity.duplicateTasks)

        )

        /**
         * Convert {@link Employee} which entity to dto. Some links changed to a string
         *
         * @param entity
         *            {@link Employee} which should be converted.
         * @return {@link EmployeeDto} with all fields from entity.
         * @see Employee
         * @see EmployeeDto
         */
        @JvmStatic
        fun convertToDto(entity: Employee) = EmployeeDto(
                entity.firstname,
                entity.secondname
        )

        /**
         * Convert {@link Comment} which entity to dto. Some links changed to a string
         *
         * @param entity
         *            {@link Comment} which should be converted.
         * @return {@link CommentDto} with all fields from entity.
         * @see Comment
         * @see CommentDto
         */
        @JvmStatic
        fun convertToDto(entity: Comment) = CommentDto(
                task = entity.task?.keys,
                commentDate = entity.commentDate.toString(),
                author = convertToDto(entity.author ?: UN_EMP),
                comment = entity.comment
        )


        /**
         * Get entity's field if it exist, either null
         *
         * @param entity
         *            {@link OneParamEntity}
         * @return {@link OneParamEntity}'s field
         * @see OneParamEntity
         */
        private fun getOrNull(entity: Feature?) = entity?.title

        private fun featureSetToList(features: Set<Feature>?): List<String> =
                if (features == null || features.isEmpty()) {
                    ArrayList()
                } else {
                    features.stream().map { it.title ?: "notFound" }.toList()
                }

        private fun subTaskSetToList(features: Set<Task>?): List<String> =
                if (features == null || features.isEmpty()) {
                    ArrayList()
                } else {
                    features.stream().map { it.keys ?: "notFound" }.toList()
                }


        /**
         * Converting {@link Date} to {@link String}. Also remove seconds and ms in a result.
         *
         * @param date
         *            {@link Date}
         * @return {@link String} with date
         */
        private fun convertTo(date: Date?) = if (date == null) {
            null
        } else {
            val textDate: String = date.toString()
            textDate.substring(0, textDate.length - 5)
        }
    }
}
