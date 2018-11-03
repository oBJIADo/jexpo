package ru.tsystems.divider.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ru.tsystems.divider.dao.api.GeneralDao;
import ru.tsystems.divider.entity.GeneralEntity;

@Repository
public abstract class GeneralDaoImpl<Entity extends GeneralEntity> implements GeneralDao<Entity> {
    private static final Logger logger = Logger.getLogger(GeneralDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Add new record into DB.
     *
     * @param entity
     *            Entity which should be added.
     */
    @Override
    public void persist(final Entity entity) {
        logger.info("New Entity(" + entity.getClass().getSimpleName() + ") added into db: " + entity.getId());
        this.entityManager.persist(entity);
    }

    /**
     * Get a record from DB.
     *
     * @param id
     *            record id.
     * @param className
     *            Entity class.
     * @return Entity.
     */
    @Override
    public Entity find(int id, Class<Entity> className) {
        return entityManager.find(className, id);
    }

    /**
     * Delete record from DB.
     *
     * @param entity
     *            Entity which should be deleted
     */
    @Override
    public void remove(Entity entity) {
        entityManager.remove(entity);
    }

    /**
     * Merge entity.
     *
     * @param entity
     *            Entity
     */
    @Override
    public void merge(Entity entity) {
        entityManager.merge(entity);
    }

    /**
     * Get all records from db.
     *
     * @param className
     *            Entity class.
     * @return Entities.
     */
    @Override
    public List<Entity> getAll(Class<Entity> className) {
        logger.info("Get all entities (" + className.getSimpleName() + ") from db");
        return this.entityManager.createQuery("from " + className.getSimpleName(), className).getResultList();
    }
}
