package ru.tsystems.divider.dao.api

import ru.tsystems.divider.entity.GeneralEntity

interface GeneralDao<Entity : GeneralEntity> {

    /**
     * Add new record into DB.
     * @param entity    Entity which should be added.
     */
    fun persist(entity: Entity)

    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    fun find(id: Int, className: Class<Entity>): Entity?

    /**
     * Delete record from DB.
     * @param entity Entity which should be deleted
     */
    fun remove(entity: Entity)

    /**
     * Merge entity.
     * @param entity    Entity
     */
    fun merge(entity: Entity)

    /**
     * Get all records from db.
     * @param className Entity class.
     * @return          Entities.
     */
    fun getAll(className: Class<Entity>): List<Entity>

}