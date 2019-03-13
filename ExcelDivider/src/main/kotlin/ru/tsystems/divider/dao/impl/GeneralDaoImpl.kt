package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.GeneralDao
import ru.tsystems.divider.entity.GeneralEntity
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException

@Repository
abstract class GeneralDaoImpl<Entity : GeneralEntity> : GeneralDao<Entity> {

    companion object {
        //private val logger = //logger.getLogger(GeneralDaoImpl::class.java)
    }

    abstract val entityManager: EntityManager

    protected fun getReference(className: Class<Entity>, primaryKey: Int): Entity? =
        try {
            entityManager.getReference(className, primaryKey)
        } catch (exc: EntityNotFoundException) {
            null
        }

    /**
     * Add new record into DB.
     *
     * @param entity
     * Entity which should be added.
     */
    override fun persist(entity: Entity) {
        //logger.info("New Entity(" + entity.javaClass.simpleName + ") added into db: " + entity.id)
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
    protected fun find(id: Int, className: Class<Entity>): Entity? = entityManager.find(className, id)

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
    override fun getAll(className: Class<Entity>): List<Entity> =
    //logger.info("Get all entities (" + className.simpleName + ") from db")
        entityManager.createQuery("from " + className.simpleName, className).resultList


    override fun refresh(entity: Entity?) {
        if (entity != null) {
            entityManager.refresh(entity)
        }
    }
}