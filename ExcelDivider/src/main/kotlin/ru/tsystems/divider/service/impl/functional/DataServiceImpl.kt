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
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Nature
import ru.tsystems.divider.entity.Task
import ru.tsystems.divider.service.api.functional.DataService

@Service
class DataServiceImpl (
    @Autowired val taskDao: TaskDao,
    @Autowired val employeeDao: EmployeeDao,
    @Autowired val featureDao: FeatureDao,
    @Autowired val natureDao: NatureDao,
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Transactional
    override fun findFeature(featureTitle: String, natureTitle: String): Feature {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Transactional
    override fun findOrCreateFeature(featureTitle: String, natureTitle: String): Feature {
        val nature: Nature = natureDao.getByTitle(natureTitle) ?: natureDao.getDefaultNature()
        var feature = featureDao.getByParam(featureTitle, nature.title)
        return if(feature == null){
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
        throw NotImplementedError()
    }

    @Transactional
    override fun addComment(taskKey: String, comment: Comment) {
        val task: Task = taskDao.getBykey(taskKey) ?: throw IllegalArgumentException("Task with key: $taskKey not exist!")
        comment.task = task
        commentDao.persist(comment)
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
}