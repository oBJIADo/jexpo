package ru.tsystems.jirexpo.dao.api;

import java.util.List;

import ru.tsystems.jirexpo.entity.GeneralEntity;

public interface GeneralDao<Entity extends GeneralEntity> {

    /**
     * Add new record into DB.
     * @param entity    Entity which should be added.
     */
    public void persist(final Entity entity);

    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    public Entity find(int id, Class<Entity> className);

    /**
     * Delete record from DB.
     * @param entity Entity which should be deleted
     */
    public void remove(final Entity entity);

    /**
     * Merge entity.
     * @param entity    Entity
     */
    public void merge(Entity entity);

    /**
     * Get all records from db.
     * @param className Entity class.
     * @return          Entities.
     */
    public List<Entity> getAll(Class<Entity> className);

}
