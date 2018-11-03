package ru.tsystems.jirexpo.components.impl

import ru.tsystems.jirexpo.dto.CommentDto
import ru.tsystems.jirexpo.dto.EmployeeDto
import ru.tsystems.jirexpo.dto.TaskDto
import ru.tsystems.jirexpo.entity.Comment
import ru.tsystems.jirexpo.entity.Employee
import ru.tsystems.jirexpo.entity.OneParamEntity
import ru.tsystems.jirexpo.entity.Task
import java.util.*
import kotlin.streams.toList

class Converter {

    companion object {
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
        fun convertToDto(entity: Task?) = if (entity == null) null else TaskDto(
                //main fields
                entity.keys, // 1
                entity.summary,


                getOrNull(entity.issueType),
                getOrNull((entity.status)),
                getOrNull(entity.priority),// 5
                getOrNull(entity.resolution),
                //Employees
                convertToDto(entity.assignee),
                convertToDto(entity.reporter),
                convertToDto(entity.creater),
                //Times
                convertTo(entity.created),// 10
                convertTo(entity.lastViewed),
                convertTo(entity.updated),// 11
                convertTo(entity.resolved),
                convertTo(entity.dueDate),
                //Characteristics
                entity.originalEstimate,
                entity.remainingEstimate,
                entity.timeSpent,// 20
                entity.workRation,
                entity.progress,
                //Description
                entity.description,
                //Sum Characteristics
                entity.sumProgress,// 25
                entity.sumTimeSpant,
                entity.sumRemainingEstimate,
                entity.sumOriginalEstimate,
                //Epics
                entity.epicName,
                getOrNull(entity.epicStatus),
                getOrNull(entity.epicColor),
                entity.epicLink,// 35

                getOrNull(entity.sprint),
                entity.orderNumber,
                entity.deliveredVersion,
                entity.drcNumber,
                getOrNull(entity.keyword),
                getOrNull(entity.fixPriority),// 40
                //Comments
                entity.comments.stream().map { convertToDto(it) }.toList(),
                //Versions
                entity.affectsVersions.stream().map { it.param }.toList(),
                entity.fixVersions.stream().map { it.param }.toList(),// 15
                //MTM components
                entity.components.stream().map { it.param }.toList(),// 16
                entity.labels.stream().map { it.param }.toList(),
                entity.teams.stream().map { it.param }.toList(),// 30
                //Links to another tasks
                entity.subTasks.stream().map { it.keys }.toList(),
                entity.parentTasks.stream().map { it.keys }.toList(),
                entity.relationTasks.stream().map { it.keys }.toList(),
                entity.duplicateTasks.stream().map { it.keys }.toList()

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
        fun convertToDto(entity: Employee?) = if (entity == null) null else EmployeeDto(
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
                entity.task?.keys,
                entity.commentDate.toString(),
                convertToDto(entity.author),
                entity.comment
        )


        /**
         * Get entity's field if it exist, either null
         *
         * @param entity
         *            {@link OneParamEntity}
         * @return {@link OneParamEntity}'s field
         * @see OneParamEntity
         */
        private fun getOrNull(entity: OneParamEntity?) = if (entity == null) null else entity.param

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
