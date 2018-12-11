package ru.tsystems.divider.dao.impl

import org.apache.log4j.Logger
import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.GeneralDao
import ru.tsystems.divider.entity.GeneralEntity
import javax.persistence.EntityManager

@Repository
abstract class GeneralDaoImpl<Entity : GeneralEntity> : GeneralDao<Entity> {

    companion object {
        private val logger = Logger.getLogger(GeneralDaoImpl::class.java)
    }

    abstract val entityManager: EntityManager

    /**
     * Add new record into DB.
     *
     * @param entity
     * Entity which should be added.
     */
    override fun persist(entity: Entity) {
        logger.info("New Entity(" + entity.javaClass.simpleName + ") added into db: " + entity.id)
        this.entityManager.persist(entity)
    }

    /**
     * Get a record from DB.
     *
     * @param id
     * record id.
     * @param className
     * Entity class.
     * @return Entity.
     */
    override fun find(id: Int, className: Class<Entity>): Entity? {
        return entityManager.find(className, id)
    }

    /**
     * Delete record from DB.
     *
     * @param entity
     * Entity which should be deleted
     */
    override fun remove(entity: Entity) {
        entityManager.remove(entity)
    }

    /**
     * Merge entity.
     *
     * @param entity
     * Entity
     */
    override fun merge(entity: Entity) {
        entityManager.merge(entity)
    }

    /**
     * Get all records from db.
     *
     * @param className
     * Entity class.
     * @return Entities.
     */
    override fun getAll(className: Class<Entity>): List<Entity> {
        logger.info("Get all entities (" + className.simpleName + ") from db")
        return this.entityManager.createQuery("from " + className.simpleName, className).resultList
    }

}