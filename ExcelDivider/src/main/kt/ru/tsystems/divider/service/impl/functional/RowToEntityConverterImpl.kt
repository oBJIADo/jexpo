package ru.tsystems.divider.service.impl.functional

import org.apache.log4j.Logger
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.tsystems.divider.dao.api.CommentDao
import ru.tsystems.divider.dao.api.TaskDao
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Consumables
import ru.tsystems.divider.entity.Dates
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Epics
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Statistics
import ru.tsystems.divider.entity.Task
import ru.tsystems.divider.entity.Workers
import ru.tsystems.divider.service.api.functional.EntityBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.service.api.functional.RowToEntityConverter
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.NATURE_COMPONENT
import ru.tsystems.divider.utils.constants.NATURE_DEFAULT
import ru.tsystems.divider.utils.constants.NATURE_EPIC_COLOR
import ru.tsystems.divider.utils.constants.NATURE_EPIC_STATUS
import ru.tsystems.divider.utils.constants.NATURE_ISSUE_TYPE
import ru.tsystems.divider.utils.constants.NATURE_KEYWORD
import ru.tsystems.divider.utils.constants.NATURE_LABEL
import ru.tsystems.divider.utils.constants.NATURE_PRIORITY
import ru.tsystems.divider.utils.constants.NATURE_RESOLUTION
import ru.tsystems.divider.utils.constants.NATURE_SPRINT
import ru.tsystems.divider.utils.constants.NATURE_STATUS
import ru.tsystems.divider.utils.constants.NATURE_TEAM
import ru.tsystems.divider.utils.constants.NATURE_VERSION
import ru.tsystems.divider.utils.constants.PROPS_COLUMN_INDEX_SOURCE
import ru.tsystems.divider.utils.constants.PROPS_MODIFICATOR_KEYS_PRE
import java.util.*
import kotlin.collections.HashSet

@Service
open class RowToEntityConverterImpl(
        @Autowired messageWorker: MessageWorker,
        @Autowired private val builder: EntityBuilder,
        @Autowired private val fieldBuilder: FieldBuilder,
        @Autowired private val taskDao: TaskDao,
        @Autowired private val commentDao: CommentDao
) : RowToEntityConverter {
    private val logger = Logger.getLogger(RowToEntityConverterImpl::class.java)

    private val FIELDS: MutableMap<String, Int> //todo: to map

    private val KEY_MODIFICATOR: String?

    init {

        KEY_MODIFICATOR = messageWorker.getSourceValue(PROPS_MODIFICATOR_KEYS_PRE)

        val getIndex: (fieldName: String) -> Int =
                { fieldName ->
                    try {
                        Integer.valueOf(messageWorker.getSourceValue(PROPS_COLUMN_INDEX_SOURCE+fieldName) ?: "-1")
                    } catch (nfexc: NumberFormatException) {
                        logger.warn("Wrong number: $fieldName; Returned -1")
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
            key = fieldBuilder.buildTaskKey(key ?: throw java.lang.IllegalArgumentException("Wrong key for sub-task"),
                    KEY_MODIFICATOR)
        if (taskDao.getBykey(key) == null) {
            this.createNewTask(row)
        }
    }

    private fun createNewTask(row: Row) {
        val task = setUpTask(row)
        taskDao.persist(task)
        saveAllComments(row, task)
    }

    private fun setUpTask(row: Row): Task = Task(
            keys = fieldBuilder.buildTaskKey(getStringCellValue(getCell(row, "keys")
                    // todo: default key (DK) to constants or smthng else
            ) ?: throw IllegalArgumentException("Task's key cannot be null"), KEY_MODIFICATOR ?: "DK"),
            summary = getStringCellValue(getCell(row, "summary")),
            issueType = buildFeatureOrNull(getStringCellValue(getCell(row, "issueType")), NATURE_ISSUE_TYPE),
            created = getDateCellValue(getCell(row, "created")),
            consumables = setUpConsumables(row)
    )

    private fun setUpConsumables(row: Row): Consumables = Consumables(
            affectsVersions = buildFeatureSetOrNull(getStringCellValue(getCell(row, "affectsVersion")), NATURE_VERSION),
            components = buildFeatureSetOrNull(getStringCellValue(getCell(row, "components")), NATURE_COMPONENT),
            deliveredVersion = buildFeatureOrNull(getStringCellValue(getCell(row, "deliveredVersion")), NATURE_VERSION),
            description = getStringCellValue(getCell(row, "description")),
            drcNumber = getStringCellValue(getCell(row, "drcNumber")),
            fixPriority = buildFeatureOrNull(getStringCellValue(getCell(row, "fixPriority")), NATURE_PRIORITY),
            fixVersions = buildFeatureSetOrNull(getStringCellValue(getCell(row, "fixVersion")), NATURE_VERSION),
            keyword = buildFeatureOrNull(getStringCellValue(getCell(row, "keyword")), NATURE_KEYWORD),
            labels = buildFeatureSetOrNull(getStringCellValue(getCell(row, "labels")), NATURE_LABEL),
            orderNumber = getStringCellValue(getCell(row, "orderNumber")),
            priority = buildFeatureOrNull(getStringCellValue(getCell(row, "priority")), NATURE_PRIORITY),
            resolution = buildFeatureOrNull(getStringCellValue(getCell(row, "resolution")), NATURE_RESOLUTION),
            sprint = buildFeatureOrNull(getStringCellValue(getCell(row, "sprint")), NATURE_SPRINT),
            status = buildFeatureOrNull(getStringCellValue(getCell(row, "status")), NATURE_STATUS),
            teams = buildFeatureSetOrNull(getStringCellValue(getCell(row, "teams")), NATURE_TEAM),

            dates = setUpDates(row),
            epics = setUpEpics(row),
            statistics = setUpStatistics(row),
            workers = setUpWorkers(row)
    )

    private fun setUpWorkers(row: Row): Workers = Workers(
            assignee = buildEmployeeOrNull(getStringCellValue(getCell(row, "assignee"))),
            creater = buildEmployeeOrNull(getStringCellValue(getCell(row, "creater"))),
            reporter = buildEmployeeOrNull(getStringCellValue(getCell(row, "reporter")))
    )

    private fun setUpEpics(row: Row): Epics = Epics(
            epicColor = buildFeatureOrNull(getStringCellValue(getCell(row, "epicColor")), NATURE_EPIC_COLOR),
            epicLink = getStringCellValue(getCell(row, "epicLink")),
            epicName = getStringCellValue(getCell(row, "epicName")),
            epicStatus = buildFeatureOrNull(getStringCellValue(getCell(row, "epicStatus")), NATURE_EPIC_STATUS)
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

    private fun buildEmployeeOrNull(employee: String?):
            Employee? = if(employee == null) null else builder.buildEmployee(employee)

    private fun buildFeatureOrNull(title: String?, nature: String = NATURE_DEFAULT):
            Feature? = if(title == null) null else builder.buildFeature(title, nature)

    private fun buildFeatureSetOrNull(title: String?, nature: String = NATURE_DEFAULT):
            Set<Feature> = if(title == null) HashSet() else builder.buildFeatureSet(title, nature)

    private fun buildTaskConnection(task: String?) :
            Set<Task> = if(task == null) HashSet() else builder.buildConnectionToAnotherTasks(task)


    @Transactional
    override fun addTasksConnectFromRow(row: Row) {
        val key = fieldBuilder.buildTaskKey(getStringCellValue(getCell(row, "keys"))
        // todo: default key (DK) to constants or smthng else
                ?: throw IllegalArgumentException("Task's key cannot be null"), KEY_MODIFICATOR ?: "DK")
        val task = taskDao.getBykey(key) ?: throw IllegalArgumentException()

        task.subTasks = buildTaskConnection(getStringCellValue(getCell(row, "subTasks")))
        task.relationTasks = buildTaskConnection(getStringCellValue(getCell(row, "relationTasks")))
        task.duplicateTasks = buildTaskConnection(getStringCellValue(getCell(row, "duplicateTasks")))

        taskDao.merge(task)
    }

    @Transactional
    override fun saveAllComments(row: Row, task: Task) {
        var currentIndex = FIELDS["commentStart"]
                ?: throw java.lang.IllegalArgumentException("commentStart cannot to be null!")
        var commentString: String? = null
        var curComment: Comment?

        //todo : delete duplicates
        while ({ commentString = getStringCellValue(row.getCell(currentIndex++)); commentString }() != null) {
            curComment = builder.buildComments(commentString!!) //todo
            curComment!!.task = task
            commentDao.persist(curComment)
        }
    }


    @Transactional
    override fun saveAllComments(row: Row) { //todo: rename or delete method
        var currentIndex: Int = FIELDS["commentMode.commentStart"]
                ?: throw java.lang.IllegalArgumentException("commentMode.commentStart cannot to be null!")
        var commentString: String? = null
        var curComment: Comment?

        while ({ commentString = getStringCellValue(row.getCell(currentIndex++)); commentString }() != null) {
            curComment = builder.buildCommentsWithTask(commentString!!) //todo
            if (curComment != null) {
                commentDao.persist(curComment)
            }
        }
    }

    //todo : methods to EXCEL READER
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
    //todo : methods to EXCEL READER

    private fun doubleToInt(value: Double): Int { //todo: smthng strange
        var lvalue = value
        if (lvalue < 1 && lvalue > 0)
            lvalue *= 100.0
        return lvalue.toInt()
    }

    //todo: not right.. mb get value?
    private fun getCell(row: Row, fieldName: String): Cell? {
        val index: Int = FIELDS[fieldName] ?: -1
        return if (index < 0) null else row.getCell(index)
    }
}