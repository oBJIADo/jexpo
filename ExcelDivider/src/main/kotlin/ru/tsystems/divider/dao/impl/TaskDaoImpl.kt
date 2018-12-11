package ru.tsystems.divider.dao.impl

import org.apache.log4j.Logger
import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.TaskDao
import ru.tsystems.divider.entity.Task
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

@Repository
class TaskDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Task>(), TaskDao {

    companion object {
        private val logger = Logger.getLogger(TaskDaoImpl::class.java)
    }

    /**
     * Finding task by key
     *
     * @param key
     * Key
     * @return [Task]
     */
    override fun getBykey(key: String): Task? {
        logger.info("Get Task Entity by key: $key")
        try {
            return entityManager.createQuery("from Task as task where task.keys=:curKey", Task::class.java)
                .setParameter("curKey", key).singleResult
        } catch (noResExc: NoResultException) {
            logger.warn("No one Task Entity founded by the key: $key")
            return null
        }

    }
}