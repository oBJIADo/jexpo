package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.StatisticsDao
import ru.tsystems.divider.entity.Statistics
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class StatisticsDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Statistics>(),
    StatisticsDao {

    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    override fun find(id: Int): Statistics? = super.find(id, Statistics::class.java)

    override fun getReference(primaryKey: Int): Statistics? = super.getReference(Statistics::class.java, primaryKey)

}