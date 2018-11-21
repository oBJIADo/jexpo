package ru.tsystems.divider.common;

import ru.tsystems.divider.dao.api.CommentDao;
import ru.tsystems.divider.dao.api.EmployeeDao;
import ru.tsystems.divider.dao.api.FeatureDao;
import ru.tsystems.divider.dao.api.GeneralDao;
import ru.tsystems.divider.dao.api.NatureDao;
import ru.tsystems.divider.dao.api.TaskDao;
import ru.tsystems.divider.entity.Comment;
import ru.tsystems.divider.entity.Employee;
import ru.tsystems.divider.entity.Feature;
import ru.tsystems.divider.entity.GeneralEntity;
import ru.tsystems.divider.entity.Nature;
import ru.tsystems.divider.entity.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryDaoGeneral implements GeneralDao<GeneralEntity> {


    /**
     * Add new record into DB.
     *
     * @param entity Entity which should be added.
     */
    @Override
    public void persist(GeneralEntity entity) {

    }

    /**
     * Get a record from DB.
     *
     * @param id        record id.
     * @param className Entity class.
     * @return Entity.
     */
    @Override
    public GeneralEntity find(int id, Class<GeneralEntity> className) {
        return null;
    }

    /**
     * Delete record from DB.
     *
     * @param entity Entity which should be deleted
     */
    @Override
    public void remove(GeneralEntity entity) {

    }

    /**
     * Merge entity.
     *
     * @param entity Entity
     */
    @Override
    public void merge(GeneralEntity entity) {

    }

    /**
     * Get all records from db.
     *
     * @param className Entity class.
     * @return Entities.
     */
    @Override
    public List<GeneralEntity> getAll(Class<GeneralEntity> className) {
        return null;
    }
}
