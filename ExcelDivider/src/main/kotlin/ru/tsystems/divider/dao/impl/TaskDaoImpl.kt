package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.TaskDao
import ru.tsystems.divider.entity.Task
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

@Repository
class TaskDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Task>(), TaskDao {

    companion object {
        //private val logger = //logger.getLogger(TaskDaoImpl::class.java)
    }

    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    override fun find(id: Int): Task? = super.find(id, Task::class.java)

    override fun getReference(primaryKey: Int): Task? = super.getReference(Task::class.java, primaryKey)


    /**
     * Finding task by key
     *
     * @param key
     * Key
     * @return [Task]
     */
    override fun getBykey(key: String): Task? {
        //logger.info("Get Task Entity by key: $key")
        try {
            return entityManager.createQuery("from Task as task where task.keys=:curKey", Task::class.java)
                .setParameter("curKey", key).singleResult
        } catch (noResExc: NoResultException) {
            //logger.warn("No one Task Entity founded by the key: $key")
            return null
        }

    }

    override fun getMultipleReference(tasks: Set<Task>) {
        var taskId: Int
        for(task:Task in tasks){
            when {
                task.id != null -> taskId = task.id
                task.keys != null -> taskId = getBykey(task.keys).id
                else -> IllegalArgumentException("TaskKey is null! Cannot add depenence!")
            }
            getReference(taskId)
        }
    }
}