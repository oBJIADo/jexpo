package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.ConsumablesDao
import ru.tsystems.divider.entity.Consumables
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class ConsumablesDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Consumables>(),
    ConsumablesDao {
    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    override fun find(id: Int): Consumables? = super.find(id, Consumables::class.java)

    override fun getReference(primaryKey: Int): Consumables? = super.getReference(Consumables::class.java, primaryKey)

}