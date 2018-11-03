package ru.tsystems.divider.dao.api;

import java.util.List;

import ru.tsystems.divider.entity.GeneralEntity;

public interface GeneralDao<Entity extends GeneralEntity> {

    /**
     * Add new record into DB.
     * @param entity    Entity which should be added.
     */
    void persist(final Entity entity);

    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    Entity find(int id, Class<Entity> className);

    /**
     * Delete record from DB.
     * @param entity Entity which should be deleted
     */
    void remove(final Entity entity);

    /**
     * Merge entity.
     * @param entity    Entity
     */
    void merge(Entity entity);

    /**
     * Get all records from db.
     * @param className Entity class.
     * @return          Entities.
     */
    List<Entity> getAll(Class<Entity> className);

}
