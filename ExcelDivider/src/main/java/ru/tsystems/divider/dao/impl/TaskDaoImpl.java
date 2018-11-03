package ru.tsystems.divider.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ru.tsystems.divider.dao.api.TaskDao;
import ru.tsystems.divider.entity.Task;

@Repository
public class TaskDaoImpl extends GeneralDaoImpl<Task> implements TaskDao {
    private static final Logger logger = Logger.getLogger(TaskDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Finding task by key
     *
     * @param key
     *            Key
     * @return {@link Task}
     */
    @Override
    public Task getBykey(String key) {
        logger.info("Get Task Entity by key: " + key);
        try {
            return entityManager.createQuery("from Task as task where task.keys=:curKey", Task.class)
                    .setParameter("curKey", key).getSingleResult();
        } catch (NoResultException noResExc) {
            logger.warn("No one Task Entity founded by the key: " + key);
            return null;
        }
    }
}
