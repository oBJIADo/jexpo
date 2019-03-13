package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.GeneralDao
import ru.tsystems.divider.entity.GeneralEntity
import java.util.*

abstract class InMemoryDaoGeneral<Entity : GeneralEntity> internal constructor() : GeneralDao<Entity>,
    ContextSimmulator<Entity> {
    protected var database: MutableList<Entity>

    init {
        database = ArrayList()
    }

    /**
     * Add new record into DB.
     *
     * @param entity Entity which should be added.
     */
    override fun persist(entity: Entity) {
        if (entity.id == null) {
            entity.id = database.size
        }

        for (cur in database) {
            if (cur.id == entity.id) {
                throw IllegalArgumentException("Entity with cloned id")
            }
        }

        database.add(entity)
    }

    /**
     * Get a record from DB.
     *
     * @param id        record id.
     * @return Entity.
     */
    override fun find(id: Int): Entity? {
        for (entity in database) {
            if (entity.id == id) {
                return entity
            }
        }

        return null
    }

    /**
     * Delete record from DB.
     *
     * @param entity Entity which should be deleted
     */
    override fun remove(entity: Entity) {
        val id = entity.id

        if (id == -1) {
            throw IllegalArgumentException("Delete entity with index -1")
        }

        for (i in database.indices) {
            if (id == database[i].id) {
                database.removeAt(i)
            }
        }

    }

    /**
     * Merge entity.
     *
     * @param entity Entity
     */
    override fun merge(entity: Entity) {
        for (i in database.indices) {
            if (database[i].id == entity.id) {
                database[i] = entity
            }
        }
    }

    /**
     * Get all records from db.
     *
     * @param className Entity class.
     * @return Entities.
     */
    override fun getAll(className: Class<Entity>): List<Entity> {
        return database
    }

    override fun reset() {
        database = ArrayList()
    }

    override fun setValue(key: String, value: Entity) {
        var needed = -1
        value.id = Integer.valueOf(key)
        for (i in database.indices) {
            if (database[i].id == value.id) {
                needed = i
            }
        }

        if (needed == -1) {
            database.add(value)
        } else {
            database[needed] = value
        }
    }

    override fun getValue(key: String): Entity? {
        for (entity in database) {
            if (entity.id == Integer.valueOf(key)) {
                return entity
            }
        }
        return null
    }

    override fun getReference(primaryKey: Int): Entity? {
        return find(primaryKey)
    }

    override fun refresh(entity: Entity?) {
    }
}