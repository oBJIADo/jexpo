package ru.tsystems.divider.service.impl.functional

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.EmployeeDao
import ru.tsystems.divider.dao.api.TaskDao
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Task
import ru.tsystems.divider.service.api.entity.FeatureService
import ru.tsystems.divider.service.api.functional.EntityBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_FORMAT_READ_DATE
import ru.tsystems.divider.utils.constants.PROPS_MODIFICATOR_KEYS_PRE
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_COMMENTS
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_EMPLOYEE
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashSet

@Service
class EntityBuilderImpl(@Autowired messageWorker: MessageWorker,
                        @Autowired val rebuilder: FieldBuilder,
                        @Autowired val featureService: FeatureService,
                        @Autowired val employeeDao: EmployeeDao,
                        @Autowired val taskDao: TaskDao) : EntityBuilder {

    private val logger = Logger.getLogger(EntityBuilderImpl::class.java)


    private val EMPLOYEE_DIVIDER: String
    private val ANOTHER_TASKS_DIVIDER: String
    private val COMMENTS_DIVIDER: String
    private val KEY_MODIFICATOR: String
    private val ANOTHER_DIVIDER: String
    private val FORMAT_DATE: String

    init {
        KEY_MODIFICATOR = messageWorker.getObligatorySourceValue(PROPS_MODIFICATOR_KEYS_PRE)

        FORMAT_DATE = messageWorker.getSourceValue(PROPS_FORMAT_READ_DATE) ?: "dd.MM.yyyy HH:mm"

        EMPLOYEE_DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_EMPLOYEE)
        ANOTHER_TASKS_DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS)
        COMMENTS_DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_COMMENTS)
        ANOTHER_DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_ANOTHER)
    }

    /**
     * Make a Employee entity and return it
     *
     * @param employee String with employee.
     * @return New Employee entity.
     */
    override fun buildEmployee(employee: String): Employee? {
        return if (employee.isEmpty()) null else getOrCreateEmployee(rebuilder.rebuildJiraField(employee, EMPLOYEE_DIVIDER))
    }

    /**
     * Make a team entity and return it
     *
     * @param team team.
     * @return New team entity.
     */
    override fun buildFeatureSet(title: String, nature: String): Set<Feature> {
        if (title.isEmpty())
            return HashSet<Feature>()

        val components = HashSet<Feature>()
        val params = rebuilder.rebuildJiraField(title, ANOTHER_DIVIDER)
        for (param in params)
            components.add(buildFeature(param, nature) ?: throw IllegalArgumentException("Not real"))//todo

        return components
    }

    /**
     * Make a Comment entity and return it
     *
     * @param comment Comment.
     * @return New comment.
     */
    override fun buildComments(comment: String): Comment? {
        if (comment.isEmpty()) //todo
            return null

        val commentDividingResult = rebuilder.rebuildComment(comment, COMMENTS_DIVIDER)
        val date = commentDividingResult[1]
        val author = commentDividingResult[2]
        val commentText = commentDividingResult[3]

        val commentBirthDay: LocalDateTime?
        val comentator: Employee?

        comentator = if (author == null) null else this.buildEmployee(author)
        commentBirthDay = if (date == null) null else this.dateFromString(date)

        return Comment(null, commentBirthDay, comentator, commentText)
    }

    override fun buildCommentsWithTask(comment: String): Comment? {
        if (comment.isEmpty()) //todo
            return null

        val commentDividingResult = rebuilder.rebuildComment(comment, COMMENTS_DIVIDER)
        val rebuilderKey = commentDividingResult[0]
        val key = if (rebuilderKey == null) {
            throw java.lang.IllegalArgumentException("Key cannot to be null")
        } else {
            rebuilder.buildTaskKey(rebuilderKey, KEY_MODIFICATOR)
        }//todo

        val date = commentDividingResult[1]
        val author = commentDividingResult[2]
        val commentText = commentDividingResult[3]

        val commentBirthDay: LocalDateTime?
        val comentator: Employee?

        comentator = if (author == null) null else this.buildEmployee(author)
        commentBirthDay = if (date == null) null else this.dateFromString(date)
        val task = taskDao.getBykey(key)

        return Comment(task, commentBirthDay, comentator, commentText)
    }

    /**
     * Convert String with special format (dd.MM.yyyy HH:mm) into Date.
     *
     * @param date String with date.
     * @return Date
     */
    private fun dateFromString(date: String): LocalDateTime? {
        try {
            val dateTimeFormatt = DateTimeFormatter.ofPattern(FORMAT_DATE)
            return LocalDateTime.parse(date, dateTimeFormatt)
        } catch (dateExc: ParseException) {
            logger.error("Can't to convert String to Date: $date")
            return null
        } catch (dateExc: NullPointerException) {
            logger.error("Can't to convert String to Date: $date")
            return null
        }

    }

    /**
     * Try to find one param entity. If it doesn't exist, persist new.
     *
     * @param title  Title
     * @param nature Nature constant.
     * @return OneParamEntity
     */
    override fun buildFeature(title: String, nature: String): Feature? { //todo: no nulls
        if (title.isEmpty())
            return null

        val feature = featureService.findByParam(title, nature)
        return feature ?: featureService.createFeatur(title, nature)
    }

    /**
     * Get employee or create new if it not exist.
     *
     * @param names String massive with firstname and secondname.
     * @return Employee.
     */
    private fun getOrCreateEmployee(names: Array<String>?): Employee? {
        return if (names == null || names.size <= 0) {
            getUnassignedEmployee()
        } else if (names.size == 1) {
            findOrPersistEmployee(names[0], names[0])
        } else {
            findOrPersistEmployee(names[0], names[1])
        }
    }

    /**
     * Try to find employee into db. If it not exit, create new employee and persist it into.
     *
     * @param firstName  Employee's firstname.
     * @param secondName Employee's secondname.
     * @return Employee.
     */
    private fun findOrPersistEmployee(firstName: String, secondName: String): Employee {
        var employee: Employee? = employeeDao.getByNames(firstName, secondName)
        if (employee != null) {
            return employee
        } else {
            employee = employeeDao.getByNames(secondName, firstName)
            if (employee != null) {
                return employee
            } else {
                employee = Employee(firstName, secondName)
                employeeDao.persist(employee)
                return employee
            }
        }
    }

    /**
     * Logging and get null user. Calling if names has null values.
     *
     * @return null
     */
    private fun getUnassignedEmployee(): Employee? {
        logger.info("Unassigned user")
        return null
    }

    /**
     * Make a subtask set and return it
     *
     * @param subTasks version.
     * @return Set with version entities.
     */
    override fun buildConnectionToAnotherTasks(subTasks: String): Set<Task> {
        if (subTasks.isEmpty())
            return HashSet()
        val tasks = HashSet<Task>()

        val keys = rebuilder.rebuildJiraField(subTasks, ANOTHER_TASKS_DIVIDER)
        for (key in keys) {
            try {
                tasks.add(buildSubTask(rebuilder.buildTaskKey(key, KEY_MODIFICATOR))) //todo: if->try not beauty
            } catch (ilStExc: IllegalStateException) {
                logger.error(ilStExc.message + "; This task not added to dependencies!!!")
            }
        }

        return tasks

    }

    private fun buildSubTask(key: String): Task {
        return taskDao.getBykey(key) ?: throw IllegalStateException("Illegal task connection with key: $key")
    }
}