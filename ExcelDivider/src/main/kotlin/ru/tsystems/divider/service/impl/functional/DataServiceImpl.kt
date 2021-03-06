package ru.tsystems.divider.service.impl.functional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.tsystems.divider.dao.api.CommentDao
import ru.tsystems.divider.dao.api.EmployeeDao
import ru.tsystems.divider.dao.api.FeatureDao
import ru.tsystems.divider.dao.api.NatureDao
import ru.tsystems.divider.dao.api.TaskDao
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Consumables
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Epics
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Nature
import ru.tsystems.divider.entity.Task
import ru.tsystems.divider.entity.Workers
import ru.tsystems.divider.service.api.functional.DataService

@Service
class DataServiceImpl(
    @Autowired private val taskDao: TaskDao,
    @Autowired private val employeeDao: EmployeeDao,
    @Autowired private val featureDao: FeatureDao,
    @Autowired private val natureDao: NatureDao,
    @Autowired private val commentDao: CommentDao
) : DataService {

    @Transactional
    override fun findTaskByKey(key: String): Task? {
        return taskDao.getBykey(key)
    }

    @Transactional
    override fun updateTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Transactional
    override fun addTask(task: Task) {

        val issueTypeId = task.issueType?.id
        if (issueTypeId != null) {
            task.issueType = featureDao.getReference(issueTypeId)
        }

        val consumables: Consumables? = task.consumables
        if (consumables != null) {
            addConsumables(consumables)
        }

        taskDao.merge(task)
    }

    private fun addConsumables(consumables: Consumables) {
        val statusId = consumables.status?.id
        if (statusId != null) {
            consumables.status = featureDao.getReference(statusId)
        }

        val priorityId = consumables.priority?.id
        if (priorityId != null) {
            consumables.priority = featureDao.getReference(priorityId)
        }

        val resolutionId = consumables.resolution?.id
        if (resolutionId != null) {
            consumables.resolution = featureDao.getReference(resolutionId)
        }

        val sprintId = consumables.sprint?.id
        if (sprintId != null) {
            consumables.sprint = featureDao.getReference(sprintId)
        }

        val deliveredVersionId = consumables.deliveredVersion?.id
        if (deliveredVersionId != null) {
            consumables.deliveredVersion = featureDao.getReference(deliveredVersionId)
        }

        val fixPriorityId = consumables.fixPriority?.id
        if (fixPriorityId != null) {
            consumables.fixPriority = featureDao.getReference(fixPriorityId)
        }

        val epics: Epics? = consumables.epics
        if (epics != null) {
            addEpics(epics)
        }

        val workers: Workers? = consumables.workers
        if (workers != null) {
            addWorkers(workers)
        }

    }

    private fun addEpics(epics: Epics) {
        val epicColorId = epics.epicColor?.id
        if (epicColorId != null) {
            epics.epicColor = featureDao.getReference(epicColorId)
        }

        val epicStatusId = epics.epicStatus?.id
        if (epicStatusId != null) {
            epics.epicStatus = featureDao.getReference(epicStatusId)
        }
//        epicsDao.merge(epics)
    }

    @Transactional
    fun addWorkers(workers: Workers) {
        val assigneeId = workers.assignee?.id
        if (assigneeId != null) {
            workers.assignee = employeeDao.getReference(assigneeId)
        }

        val createrId = workers.creater?.id
        if (createrId != null) {
            workers.creater = employeeDao.getReference(createrId)
        }

        val reproterId = workers.reporter?.id
        if (reproterId != null) {
            workers.reporter = employeeDao.getReference(reproterId)
        }

//        workersDao.merge(workers)
    }

    @Transactional
    override fun addTaskRelation(
        subTasks: Array<String>,
        relationTasks: Array<String>,
        duplicateTasks: Array<String>,
        taskKey: String
    ) {
        var isShouldUpdate = false
        val task = taskDao.getBykey(taskKey) ?: throw IllegalArgumentException("Task with key: $taskKey not exist!")

        if (!subTasks.isEmpty()) {
            task.subTasks = getTasks(subTasks)
            isShouldUpdate = true
        }
        if (!relationTasks.isEmpty()) {
            task.relationTasks = getTasks(relationTasks)
            isShouldUpdate = true
        }
        if (!duplicateTasks.isEmpty()) {
            task.duplicateTasks = getTasks(duplicateTasks)
            isShouldUpdate = true
        }

        if (isShouldUpdate) {
            taskDao.merge(task)
        }
    }

    @Transactional
    override fun findFeature(featureTitle: String, natureTitle: String): Feature {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Transactional
    override fun findOrCreateFeature(featureTitle: String, natureTitle: String): Feature {
        val nature: Nature = natureDao.getByTitle(natureTitle) ?: natureDao.getDefaultNature()
        var feature = featureDao.getByParam(featureTitle, nature.title)
        return if (feature == null) {
            feature = Feature(featureTitle, nature)
            featureDao.persist(feature)
            feature
        } else {
            feature
        }
    }

    @Transactional
    override fun addFeature(feature: Feature) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Transactional
    override fun addComment(comment: Comment) {
        val task: Task = taskDao.getBykey(comment.task?.keys ?: throw IllegalArgumentException("Comment without task!"))
            ?: throw IllegalArgumentException("Task ot added to db yet!")

        comment.task = task
        commentDao.merge(comment)
    }

    @Transactional
    override fun addComment(taskKey: String, comment: Comment) {
        val task: Task =
            taskDao.getBykey(taskKey) ?: throw IllegalArgumentException("Task with key: $taskKey not exist!")
        comment.task = task
        commentDao.merge(comment)
    }

    /**
     * Try to find employee into db. If it not exit, create new employee and persist it into.
     *
     * @param firstName  Employee's firstname.
     * @param secondName Employee's secondname.
     * @return Employee.
     */
    @Transactional
    override fun findOrCreateEmployee(firstName: String, secondName: String): Employee {
        var employee: Employee? = employeeDao.getByNamesIgnoreCase(firstName, secondName)
        return if (employee != null) {
            employee
        } else {
            employee = employeeDao.getByNamesIgnoreCase(secondName, firstName)
            if (employee != null) {
                employee
            } else {
                employee = Employee(firstName, secondName)
                employeeDao.persist(employee)
                employee
            }
        }
    }

    @Transactional
    override fun findOrCreateEmployee(name: String): Employee {
        var employee: Employee? = employeeDao.getByNamesIgnoreCase(name, name)
        return if (employee != null) {
            return employee
        } else {
            employee = Employee(name, name)
            employeeDao.persist(employee)
            employee
        }
    }

    @Transactional
    override fun addEmployee(employee: Employee) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getTasks(keys: Array<String>): Set<Task> {
        val tasks: HashSet<Task> = HashSet()
        var task: Task?
        for (key: String in keys) {
            task = taskDao.getBykey(key)
            if(task?.id != null){
                tasks.add(task)
            }
        }
        return tasks
    }
}