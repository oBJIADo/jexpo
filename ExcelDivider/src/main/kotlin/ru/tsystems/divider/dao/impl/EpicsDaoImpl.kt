package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.EpicsDao
import ru.tsystems.divider.entity.Epics
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class EpicsDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Epics>(), EpicsDao {
    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    override fun find(id: Int): Epics? = super.find(id, Epics::class.java)

    override fun getReference(primaryKey: Int): Epics? = super.getReference(Epics::class.java, primaryKey)

}