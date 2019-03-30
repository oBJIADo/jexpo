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
        fun convertToDto(entity: Task?) = if (entity == null) null else TaskDto(
                //main fields
                entity.keys, // 1
                entity.summary,


                getOrNull(entity.issueType),
                getOrNull(entity.consumables?.status),
                getOrNull(entity.consumables?.priority),// 5
                getOrNull(entity.consumables?.resolution),
                //Employees
                convertToDto(entity.consumables?.workers?.assignee),
                convertToDto(entity.consumables?.workers?.reporter),
                convertToDto(entity.consumables?.workers?.creater),
                //Times
                convertTo(entity.created),// 10
                convertTo(entity.consumables?.dates?.lastViewed),
                convertTo(entity.consumables?.dates?.updated),// 11
                convertTo(entity.consumables?.dates?.resolved),
                convertTo(entity.consumables?.dates?.dueDate),
                //Characteristics
                entity.consumables?.statistics?.originalEstimate,
                entity.consumables?.statistics?.remainingEstimate,
                entity.consumables?.statistics?.timeSpent,// 20
                entity.consumables?.statistics?.workRation,
                entity.consumables?.statistics?.progress,
                //Description
                entity.consumables?.description,
                //Sum Characteristics
                entity.consumables?.statistics?.sumProgress,// 25
                entity.consumables?.statistics?.sumTimeSpant,
                entity.consumables?.statistics?.sumRemainingEstimate,
                entity.consumables?.statistics?.sumOriginalEstimate,
                //Epics
                entity.consumables?.epics?.epicName,
                getOrNull(entity.consumables?.epics?.epicStatus),
                getOrNull(entity.consumables?.epics?.epicColor),
                entity.consumables?.epics?.epicLink,// 35

                getOrNull(entity.consumables?.sprint),
                entity.consumables?.orderNumber,
                entity.consumables?.deliveredVersion?.title,
                entity.consumables?.drcNumber,
                getOrNull(entity.consumables?.keyword),
                getOrNull(entity.consumables?.fixPriority),// 40
                //Comments
                entity.comments.stream().map { convertToDto(it) }.toList(),
                //Versions
                featureSetToList(entity.consumables?.affectsVersions),
                featureSetToList(entity.consumables?.fixVersions),// 15
                //MTM components
                featureSetToList(entity.consumables?.components),// 16
                featureSetToList(entity.consumables?.labels),
                featureSetToList(entity.consumables?.teams),// 30
                //Links to another tasks
                subTaskSetToList(entity.subTasks),
                subTaskSetToList(entity.parentTasks),
                subTaskSetToList(entity.relationTasks),
                subTaskSetToList(entity.duplicateTasks)

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
