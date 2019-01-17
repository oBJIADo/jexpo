package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.DatesDao
import ru.tsystems.divider.entity.Dates
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class DatesDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Dates>(), DatesDao {
    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    override fun find(id: Int): Dates? = super.find(id, Dates::class.java)

    override fun getReference(primaryKey: Int): Dates? = super.getReference(Dates::class.java, primaryKey)

}