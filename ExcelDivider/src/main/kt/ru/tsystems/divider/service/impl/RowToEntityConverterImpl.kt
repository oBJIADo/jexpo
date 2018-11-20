package ru.tsystems.divider.service.impl

import org.apache.log4j.Logger
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.tsystems.divider.components.api.MessageWorker
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Consumables
import ru.tsystems.divider.entity.Dates
import ru.tsystems.divider.entity.Epics
import ru.tsystems.divider.entity.Statistics
import ru.tsystems.divider.entity.Task
import ru.tsystems.divider.entity.Workers
import ru.tsystems.divider.service.api.CommentService
import ru.tsystems.divider.service.api.EntityBuilder
import ru.tsystems.divider.service.api.FieldBuilder
import ru.tsystems.divider.service.api.RowToEntityConverter
import ru.tsystems.divider.service.api.TaskService
import java.util.*

@Service
open class RowToEntityConverterImpl(
        @Autowired messageWorker: MessageWorker,
        @Autowired private val builder: EntityBuilder,
        @Autowired private val fieldBuilder: FieldBuilder,
        @Autowired private val taskService: TaskService,
        @Autowired private val commentService: CommentService
) : RowToEntityConverter {
    private val logger = Logger.getLogger(RowToEntityConverterImpl::class.java)

    private val FIELDS: MutableMap<String, Int> //todo: to map

    private val KEY_MODIFICATOR: String?

    private val SOURCE = "column.index."

    init {

        KEY_MODIFICATOR = messageWorker.getSourceValue("modificator.keys.pre")

        val getIndex: (fieldName: String) -> Int =
                { fieldName ->
                    try {
                        Integer.valueOf(messageWorker.getSourceValue(SOURCE, fieldName) ?: "-1")
                    } catch (nfexc: NumberFormatException) {
                        logger.error("Wrong number: $fieldName; Returned -1")
                        -1
                    }
                }

        FIELDS = HashMap()
        FIELDS["keys"] = getIndex("keys")
        FIELDS["summary"] = getIndex("summary")
        FIELDS["issueType"] = getIndex("issueType")
        FIELDS["status"] = getIndex("status")
        FIELDS["priority"] = getIndex("priority")
        FIELDS["resolution"] = getIndex("resolution")
        FIELDS["assignee"] = getIndex("assignee")
        FIELDS["reporter"] = getIndex("reporter")
        FIELDS["creater"] = getIndex("creater")
        FIELDS["created"] = getIndex("created")
        FIELDS["lastViewed"] = getIndex("lastViewed")
        FIELDS["updated"] = getIndex("updated")
        FIELDS["resolved"] = getIndex("resolved")
        FIELDS["affectsVersion"] = getIndex("affectsVersion")
        FIELDS["fixVersion"] = getIndex("fixVersion")
        FIELDS["components"] = getIndex("components")
        FIELDS["dueDate"] = getIndex("dueDate")
        FIELDS["originalEstimate"] = getIndex("originalEstimate")
        FIELDS["remainingEstimate"] = getIndex("remainingEstimate")
        FIELDS["timeSpent"] = getIndex("timeSpent")
        FIELDS["workRatio"] = getIndex("workRatio")
        FIELDS["description"] = getIndex("description")
        FIELDS["progress"] = getIndex("progress")
        FIELDS["sumProgress"] = getIndex("sumProgress")
        FIELDS["sumTimeSpent"] = getIndex("sumTimeSpent")
        FIELDS["sumRemainingEstimate"] = getIndex("sumRemainingEstimate")
        FIELDS["sumOriginalEstimate"] = getIndex("sumOriginalEstimate")
        FIELDS["labels"] = getIndex("labels")
        FIELDS["teams"] = getIndex("teams")
        FIELDS["epicName"] = getIndex("epicName")
        FIELDS["epicStatus"] = getIndex("epicStatus")
        FIELDS["epicColor"] = getIndex("epicColor")
        FIELDS["sprint"] = getIndex("sprint")
        FIELDS["epicLink"] = getIndex("epicLink")
        FIELDS["orderNumber"] = getIndex("orderNumber")
        FIELDS["deliveredVersion"] = getIndex("deliveredVersion")
        FIELDS["drcNumber"] = getIndex("drcNumber")
        FIELDS["keyword"] = getIndex("keyword")
        FIELDS["fixPriority"] = getIndex("fixPriority")
        FIELDS["commentStart"] = getIndex("commentStart")
        FIELDS["subTasks"] = getIndex("subTasks")
        FIELDS["relationTasks"] = getIndex("relationTasks")
        FIELDS["duplicateTasks"] = getIndex("duplicateTasks")
        FIELDS["commentMode.commentStart"] = getIndex("commentMode.commentStart")
    }


    /**
     * Convert row to Entities. Save all entities into DB.
     *
     * @param row
     * Row which should be converted.
     */
    @Transactional
    override fun addTaskFromRow(row: Row) {
        var key = getStringCellValue(getCell(row, "keys"))
        if (KEY_MODIFICATOR != null)
            key = fieldBuilder.buildTaskKey(key, KEY_MODIFICATOR)
        if (taskService.findByKey(key) == null) {
            this.createNewTask(row)
        }
    }

    private fun createNewTask(row: Row) {
        val task = setUpTask(row)
        taskService.persist(task)
        saveAllComments(row, task)
    }

    private fun setUpTask(row: Row): Task = Task(
            keys = fieldBuilder.buildTaskKey(getStringCellValue(getCell(row, "keys")), KEY_MODIFICATOR),
            summary = getStringCellValue(getCell(row, "summary")),
            issueType = builder.buildIssueType(getStringCellValue(getCell(row, "issueType"))),
            created = getDateCellValue(getCell(row, "created")),
            consumables = setUpConsumables(row)
    )

    private fun setUpConsumables(row: Row): Consumables = Consumables(
            affectsVersions = builder.buildVersions(getStringCellValue(getCell(row, "affectsVersion"))),
            components = builder.buildComponents(getStringCellValue(getCell(row, "components"))),
            deliveredVersion = builder.buildDeliveredVersion(getStringCellValue(getCell(row, "deliveredVersion"))),
            description = getStringCellValue(getCell(row, "description")),
            drcNumber = getStringCellValue(getCell(row, "drcNumber")),
            fixPriority = builder.buildPriority(getStringCellValue(getCell(row, "fixPriority"))),
            fixVersions = builder.buildVersions(getStringCellValue(getCell(row, "fixVersion"))),
            keyword = builder.buildKeyword(getStringCellValue(getCell(row, "keyword"))),
            labels = builder.buildLabels(getStringCellValue(getCell(row, "labels"))),
            orderNumber = getStringCellValue(getCell(row, "orderNumber")),
            priority = builder.buildPriority(getStringCellValue(getCell(row, "priority"))),
            resolution = builder.buildResolution(getStringCellValue(getCell(row, "resolution"))),
            sprint = builder.buildSprint(getStringCellValue(getCell(row, "sprint"))),
            status = builder.buildStatus(getStringCellValue(getCell(row, "status"))),
            teams = builder.buildTeams(getStringCellValue(getCell(row, "teams"))),

            dates = setUpDates(row),
            epics = setUpEpics(row),
            statistics = setUpStatistics(row),
            workers = setUpWorkers(row)
    )

    private fun setUpWorkers(row: Row): Workers = Workers(
            assignee = builder.buildEmployee(getStringCellValue(getCell(row, "assignee"))),
            creater = builder.buildEmployee(getStringCellValue(getCell(row, "creater"))),
            reporter = builder.buildEmployee(getStringCellValue(getCell(row, "reporter")))
    )

    private fun setUpEpics(row: Row): Epics = Epics(
            epicColor = builder.buildEpicColor(getStringCellValue(getCell(row, "epicColor"))),
            epicLink = getStringCellValue(getCell(row, "epicLink")),
            epicName = getStringCellValue(getCell(row, "epicName")),
            epicStatus = builder.buildStatus(getStringCellValue(getCell(row, "epicStatus")))
    )

    private fun setUpStatistics(row: Row): Statistics = Statistics(
            original_estimate = getNumericCellValue(getCell(row, "originalEstimate")),
            progress = getPercentCellValue(getCell(row, "progress")),
            remaining_estimate = getNumericCellValue(getCell(row, "remainingEstimate")),
            time_spent = getNumericCellValue(getCell(row, "timeSpent")),
            work_ration = getPercentCellValue(getCell(row, "workRatio")),
            sum_progress = getPercentCellValue(getCell(row, "sumProgress")),
            sum_time_spant = getNumericCellValue(getCell(row, "sumTimeSpent")),
            sum_remaining_estimate = getNumericCellValue(getCell(row, "sumRemainingEstimate")),
            sum_original_estimate = getNumericCellValue(getCell(row, "sumOriginalEstimate"))
    )

    private fun setUpDates(row: Row): Dates = Dates(
            lastViewed = getDateCellValue(getCell(row, "lastViewed")),
            updated = getDateCellValue(getCell(row, "updated")),
            resolved = getDateCellValue(getCell(row, "resolved")),
            dueDate = getDateCellValue(getCell(row, "dueDate"))
    )


    @Transactional
    override fun addTasksConnectFromRow(row: Row) {
        val key = fieldBuilder.buildTaskKey(getStringCellValue(getCell(row, "keys")), KEY_MODIFICATOR)
        val task = taskService.findByKey(key) ?: throw IllegalArgumentException()

        task.subTasks = builder.buildConnectionToAnotherTasks(getStringCellValue(getCell(row, "subTasks")))
        task.relationTasks = builder.buildConnectionToAnotherTasks(getStringCellValue(getCell(row, "relationTasks")))
        task.duplicateTasks = builder
                .buildConnectionToAnotherTasks(getStringCellValue(getCell(row, "duplicateTasks")))
        taskService.merge(task)
    }

    override fun saveAllComments(row: Row, task: Task) {
        var currentIndex = FIELDS["commentStart"]
                ?: throw java.lang.IllegalArgumentException("commentStart cannot to be null!")
        var commentString: String?
        var curComment: Comment

        commentString = getStringCellValue(row.getCell(currentIndex++))  //todo : delete duplicates
        while (commentString != null) {
            curComment = builder.buildComments(commentString)
            curComment.task = task
            commentService.persist(curComment)
            commentString = getStringCellValue(row.getCell(currentIndex++)) //todo : delete duplicates
        }
    }

    @Transactional
    override fun saveAllComments(row: Row) { //todo: rename or delete method
        var currentIndex: Int = FIELDS["commentMode.commentStart"]
                ?: throw java.lang.IllegalArgumentException("commentMode.commentStart cannot to be null!")
        var commentString: String?
        var curComment: Comment

        commentString = getStringCellValue(row.getCell(currentIndex++))  //todo : delete duplicates
        while (commentString != null) {
            curComment = builder.buildCommentsWithTask(commentString)
            if (curComment != null) {
                commentService.persist(curComment)
            }
            commentString = getStringCellValue(row.getCell(currentIndex++)) //todo : delete duplicates
        }
    }

    private fun getStringCellValue(cell: Cell?): String? {
        if (cell == null)
            return null

        if (cell.cellType == Cell.CELL_TYPE_NUMERIC)
            return cell.numericCellValue.toString()
        return if (cell.cellType == Cell.CELL_TYPE_ERROR) cell.errorCellValue.toString() else cell.stringCellValue // wow
    }

    private fun getDateCellValue(cell: Cell?): Date? {
        return cell?.dateCellValue
    }

    private fun getNumericCellValue(cell: Cell?): Int? {
        return if (cell == null) null else doubleToInt(cell.numericCellValue)
    }

    private fun getPercentCellValue(cell: Cell?): Int? {
        return if (cell == null) null else doubleToInt(cell.numericCellValue) * 100
    }

    private fun doubleToInt(value: Double): Int {
        var value = value
        if (value < 1 && value > 0)
            value *= 100.0
        return value.toInt()
    }

    private fun getCell(row: Row, fieldName: String): Cell? {
        val index: Int = FIELDS[fieldName] ?: -1
        return if (index < 0) null else row.getCell(index)
    }
}