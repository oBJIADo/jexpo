package ru.tsystems.divider.service.impl.functional

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Consumables
import ru.tsystems.divider.entity.Dates
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Epics
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Statistics
import ru.tsystems.divider.entity.Task
import ru.tsystems.divider.entity.Workers
import ru.tsystems.divider.service.api.functional.DataService
import ru.tsystems.divider.service.api.functional.EntityBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.service.api.functional.RowToEntityConverter
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.COLUMN_AFFECTSVERSION
import ru.tsystems.divider.utils.constants.COLUMN_ASSIGNEE
import ru.tsystems.divider.utils.constants.COLUMN_COMMENTSTART
import ru.tsystems.divider.utils.constants.COLUMN_COMPONENTS
import ru.tsystems.divider.utils.constants.COLUMN_CREATED
import ru.tsystems.divider.utils.constants.COLUMN_CREATER
import ru.tsystems.divider.utils.constants.COLUMN_DELIVEREDVERSION
import ru.tsystems.divider.utils.constants.COLUMN_DESCRIPTION
import ru.tsystems.divider.utils.constants.COLUMN_DRCNUMBER
import ru.tsystems.divider.utils.constants.COLUMN_DUEDATE
import ru.tsystems.divider.utils.constants.COLUMN_DUPLICATETASKS
import ru.tsystems.divider.utils.constants.COLUMN_EPICCOLOR
import ru.tsystems.divider.utils.constants.COLUMN_EPICLINK
import ru.tsystems.divider.utils.constants.COLUMN_EPICNAME
import ru.tsystems.divider.utils.constants.COLUMN_EPICSTATUS
import ru.tsystems.divider.utils.constants.COLUMN_FIXPRIORITY
import ru.tsystems.divider.utils.constants.COLUMN_FIXVERSION
import ru.tsystems.divider.utils.constants.COLUMN_ISSUETYPE
import ru.tsystems.divider.utils.constants.COLUMN_KEYS
import ru.tsystems.divider.utils.constants.COLUMN_KEYWORD
import ru.tsystems.divider.utils.constants.COLUMN_LABELS
import ru.tsystems.divider.utils.constants.COLUMN_LASTVIEWED
import ru.tsystems.divider.utils.constants.COLUMN_ORDERNUMBER
import ru.tsystems.divider.utils.constants.COLUMN_ORIGINALESTIMATE
import ru.tsystems.divider.utils.constants.COLUMN_PRIORITY
import ru.tsystems.divider.utils.constants.COLUMN_PROGRESS
import ru.tsystems.divider.utils.constants.COLUMN_RELATIONTASKS
import ru.tsystems.divider.utils.constants.COLUMN_REMAININGESTIMATE
import ru.tsystems.divider.utils.constants.COLUMN_REPORTER
import ru.tsystems.divider.utils.constants.COLUMN_RESOLUTION
import ru.tsystems.divider.utils.constants.COLUMN_RESOLVED
import ru.tsystems.divider.utils.constants.COLUMN_SPRINT
import ru.tsystems.divider.utils.constants.COLUMN_STATUS
import ru.tsystems.divider.utils.constants.COLUMN_SUBTASKS
import ru.tsystems.divider.utils.constants.COLUMN_SUMMARY
import ru.tsystems.divider.utils.constants.COLUMN_SUMORIGINALESTIMATE
import ru.tsystems.divider.utils.constants.COLUMN_SUMPROGRESS
import ru.tsystems.divider.utils.constants.COLUMN_SUMREMAININGESTIMATE
import ru.tsystems.divider.utils.constants.COLUMN_SUMTIMESPENT
import ru.tsystems.divider.utils.constants.COLUMN_TEAMS
import ru.tsystems.divider.utils.constants.COLUMN_TIMESPENT
import ru.tsystems.divider.utils.constants.COLUMN_UPDATED
import ru.tsystems.divider.utils.constants.COLUMN_WORKRATIO
import ru.tsystems.divider.utils.constants.DEFAULT_KEY
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
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS
import java.util.*
import kotlin.collections.HashSet

@Service
class RowToEntityConverterImpl(
    @Autowired messageWorker: MessageWorker,
    @Autowired private val builder: EntityBuilder,
    @Autowired private val fieldBuilder: FieldBuilder,
    @Autowired private val dataService: DataService
) : RowToEntityConverter {

    companion object {
        //private val logger = //logger.getLogger(RowToEntityConverterImpl::class.java)
    }

    private val FIELDS: MutableMap<String, Int> //todo: to map

    private val KEY_MODIFICATOR: String?
    private val ANOTHER_TASKS_DIVIDER: String

    private val commentStart: Int?

    init {

        KEY_MODIFICATOR = messageWorker.getSourceValue(PROPS_MODIFICATOR_KEYS_PRE)
        ANOTHER_TASKS_DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS)

        val getIndex: (fieldName: String) -> Int =
            { fieldName ->
                messageWorker.getIntSourceValue(PROPS_COLUMN_INDEX_SOURCE + fieldName, -1)
            }

        FIELDS = HashMap()
        FIELDS[COLUMN_KEYS] = getIndex(COLUMN_KEYS)
        FIELDS[COLUMN_SUMMARY] = getIndex(COLUMN_SUMMARY)
        FIELDS[COLUMN_ISSUETYPE] = getIndex(COLUMN_ISSUETYPE)
        FIELDS[COLUMN_STATUS] = getIndex(COLUMN_STATUS)
        FIELDS[COLUMN_PRIORITY] = getIndex(COLUMN_PRIORITY)
        FIELDS[COLUMN_RESOLUTION] = getIndex(COLUMN_RESOLUTION)
        FIELDS[COLUMN_ASSIGNEE] = getIndex(COLUMN_ASSIGNEE)
        FIELDS[COLUMN_REPORTER] = getIndex(COLUMN_REPORTER)
        FIELDS[COLUMN_CREATER] = getIndex(COLUMN_CREATER)
        FIELDS[COLUMN_CREATED] = getIndex(COLUMN_CREATED)
        FIELDS[COLUMN_LASTVIEWED] = getIndex(COLUMN_LASTVIEWED)
        FIELDS[COLUMN_UPDATED] = getIndex(COLUMN_UPDATED)
        FIELDS[COLUMN_RESOLVED] = getIndex(COLUMN_RESOLVED)
        FIELDS[COLUMN_AFFECTSVERSION] = getIndex(COLUMN_AFFECTSVERSION)
        FIELDS[COLUMN_FIXVERSION] = getIndex(COLUMN_FIXVERSION)
        FIELDS[COLUMN_COMPONENTS] = getIndex(COLUMN_COMPONENTS)
        FIELDS[COLUMN_DUEDATE] = getIndex(COLUMN_DUEDATE)
        FIELDS[COLUMN_ORIGINALESTIMATE] = getIndex(COLUMN_ORIGINALESTIMATE)
        FIELDS[COLUMN_REMAININGESTIMATE] = getIndex(COLUMN_REMAININGESTIMATE)
        FIELDS[COLUMN_TIMESPENT] = getIndex(COLUMN_TIMESPENT)
        FIELDS[COLUMN_WORKRATIO] = getIndex(COLUMN_WORKRATIO)
        FIELDS[COLUMN_DESCRIPTION] = getIndex(COLUMN_DESCRIPTION)
        FIELDS[COLUMN_PROGRESS] = getIndex(COLUMN_PROGRESS)
        FIELDS[COLUMN_SUMPROGRESS] = getIndex(COLUMN_SUMPROGRESS)
        FIELDS[COLUMN_SUMTIMESPENT] = getIndex(COLUMN_SUMTIMESPENT)
        FIELDS[COLUMN_SUMREMAININGESTIMATE] = getIndex(COLUMN_SUMREMAININGESTIMATE)
        FIELDS[COLUMN_SUMORIGINALESTIMATE] = getIndex(COLUMN_SUMORIGINALESTIMATE)
        FIELDS[COLUMN_LABELS] = getIndex(COLUMN_LABELS)
        FIELDS[COLUMN_TEAMS] = getIndex(COLUMN_TEAMS)
        FIELDS[COLUMN_EPICNAME] = getIndex(COLUMN_EPICNAME)
        FIELDS[COLUMN_EPICSTATUS] = getIndex(COLUMN_EPICSTATUS)
        FIELDS[COLUMN_EPICCOLOR] = getIndex(COLUMN_EPICCOLOR)
        FIELDS[COLUMN_SPRINT] = getIndex(COLUMN_SPRINT)
        FIELDS[COLUMN_EPICLINK] = getIndex(COLUMN_EPICLINK)
        FIELDS[COLUMN_ORDERNUMBER] = getIndex(COLUMN_ORDERNUMBER)
        FIELDS[COLUMN_DELIVEREDVERSION] = getIndex(COLUMN_DELIVEREDVERSION)
        FIELDS[COLUMN_DRCNUMBER] = getIndex(COLUMN_DRCNUMBER)
        FIELDS[COLUMN_KEYWORD] = getIndex(COLUMN_KEYWORD)
        FIELDS[COLUMN_FIXPRIORITY] = getIndex(COLUMN_FIXPRIORITY)
        FIELDS[COLUMN_COMMENTSTART] = getIndex(COLUMN_COMMENTSTART)
        FIELDS[COLUMN_SUBTASKS] = getIndex(COLUMN_SUBTASKS)
        FIELDS[COLUMN_RELATIONTASKS] = getIndex(COLUMN_RELATIONTASKS)
        FIELDS[COLUMN_DUPLICATETASKS] = getIndex(COLUMN_DUPLICATETASKS)
        commentStart = getIndex("commentMode.commentStart")
    }


    /**
     * Convert row to Entities. Save all entities into DB.
     *
     * @param row
     * Row which should be converted.
     */
    override fun addTaskFromRow(row: Row) {
        var key = getStringCellValue(getCell(row, "keys"))
            ?: throw java.lang.IllegalArgumentException("Wrong key for sub-task")
        if (KEY_MODIFICATOR != null)
            key = fieldBuilder.buildTaskKey(
                key,
                KEY_MODIFICATOR
            )
        if (dataService.findTaskByKey(key) == null) {
            this.createNewTask(row)
        }
    }

    private fun createNewTask(row: Row) {
        val task = setUpTask(row)
        dataService.addTask(task)
        saveAllComments(row, task)
    }

    private fun setUpTask(row: Row): Task = Task(
        keys = fieldBuilder.buildTaskKey(
            getStringCellValue(
                getCell(row, "keys")
            ) ?: throw IllegalArgumentException("Task's key cannot be null"), KEY_MODIFICATOR ?: DEFAULT_KEY
        ),
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
            Employee? = if (employee == null) null else builder.buildEmployee(employee)

    private fun buildFeatureOrNull(title: String?, nature: String = NATURE_DEFAULT):
            Feature? = if (title == null) null else builder.buildFeature(title, nature)

    private fun buildFeatureSetOrNull(title: String?, nature: String = NATURE_DEFAULT):
            Set<Feature> = if (title == null) HashSet() else builder.buildFeatureSet(title, nature)

    override fun addTasksConnectFromRow(row: Row) {
        val stringKey = getStringCellValue(getCell(row, "keys"))
        val key: String
        if (stringKey == null) {
            //logger.error("Task key is null! Row: ${row.rowNum}; Column: ${FIELDS["keys"]}")
            throw java.lang.IllegalArgumentException("Task key is null")
        } else {
            key = fieldBuilder.buildTaskKey(stringKey, KEY_MODIFICATOR ?: DEFAULT_KEY)
        }
        val subTasks: Array<String> = fieldBuilder.rebuildString(getStringCellValue(getCell(row, "subTasks")), ANOTHER_TASKS_DIVIDER)
        val relationTasks: Array<String> = fieldBuilder.rebuildString(getStringCellValue(getCell(row, "relationTasks")), ANOTHER_TASKS_DIVIDER)
        val duplicateTasks: Array<String> = fieldBuilder.rebuildString(getStringCellValue(getCell(row, "duplicateTasks")), ANOTHER_TASKS_DIVIDER)

        dataService.addTaskRelation(
            subTasks = subTasks,
            duplicateTasks = duplicateTasks,
            relationTasks = relationTasks,
            taskKey = key
        )
    }

    override fun saveAllComments(row: Row, task: Task) {
        var currentIndex = FIELDS["commentStart"]
            ?: throw java.lang.IllegalArgumentException("commentStart cannot to be null!")
        var commentString: String?
        var curComment: Comment?

        commentString = getStringCellValue(row.getCell(currentIndex++))
        while (commentString != null) {
            curComment = builder.buildComments(commentString)
            if (curComment != null) {
                curComment.task = task
                dataService.addComment(curComment)
            }
            commentString = getStringCellValue(row.getCell(currentIndex++))
        }
    }


    override fun saveAllComments(row: Row) { //todo: rename or delete method
        var currentIndex: Int = commentStart
            ?: throw java.lang.IllegalArgumentException("commentMode.commentStart cannot to be null!")
        var commentString: String?
        var curComment: Comment?
        commentString = getStringCellValue(row.getCell(currentIndex++))
        while (commentString != null) {
            curComment = builder.buildComments(commentString)
            if (curComment != null) {
                dataService.addComment(curComment)
            }
            commentString = getStringCellValue(row.getCell(currentIndex++))
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