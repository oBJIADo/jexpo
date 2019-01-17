package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.WorkersDao
import ru.tsystems.divider.entity.Workers
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class WorkersDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Workers>(),
    WorkersDao {

    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    override fun find(id: Int): Workers? = super.find(id, Workers::class.java)

    override fun getReference(primaryKey: Int): Workers? = super.getReference(Workers::class.java, primaryKey)

}