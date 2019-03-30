package ru.tsystems.jirexpo.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.tsystems.jirexpo.dao.api.TaskDao;
import ru.tsystems.jirexpo.entity.Task;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

@Repository
public class TaskDaoImpl extends GeneralDaoImpl<Task> implements TaskDao {
    private static final Logger logger = Logger.getLogger(TaskDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Finding task by key
     *
     * @param key Key
     * @return {@link Task}
     */
    @Override
    public Task getBykey(String key) {
        logger.info("Get Task Entity by key: " + key);
        try {
            return entityManager.createQuery("select task from Task as task where task.keys=:curKey", Task.class)
                    .setParameter("curKey", key).getSingleResult();
        } catch (NoResultException noResExc) {
            logger.error("No one Task Entity founded by the key: " + key);
            return null;
        }
    }

    /**
     * Get count of the tasks
     *
     * @return Count of the tasks
     */
    @Override
    public int getItemsCount() {
        logger.info("Get tasks count");
        return entityManager.createQuery("select count(*) from Task", Long.class).getSingleResult().intValue();
    }

    /**
     * Get some Tasks from starting count.
     *
     * @param from  Starting count
     * @param count Count of tasks
     * @return List with {@link Task}
     */
    @Override
    public List<Task> getTasks(int from, int count) {
        logger.info("Get tasks from: " + from + ", count: " + count);
        return entityManager.createQuery("FROM Task as task", Task.class).setFirstResult(from).setMaxResults(count)
                .getResultList();
    }

    /**
     * Get some Tasks from starting count.
     *
     * @param from  Starting count
     * @param count Count of tasks
     * @param param Param for searching
     * @param query Native query
     * @return List with {@link Task}
     */
    @Override
    public List<Task> getTasks(int from, int count, String param, String query) {
        logger.info("Native query with param: " + param + ", from: " + from + ", count: " + count);
        return entityManager.createNativeQuery(query, Task.class).setParameter("param", param)
                .setFirstResult(from).setMaxResults(count).getResultList();
    }

    /**
     * Get count of the tasks
     *
     * @param query Query for giving task's count
     * @param param Param for searching
     * @return Count of the tasks
     */
    @Override
    public int getItemsCount(String query, String param) {
        logger.info("Native query for counts with param: " + param);
        BigInteger bInt = (BigInteger) entityManager.createNativeQuery(query).setParameter("param", param)
                .getSingleResult();

        return bInt.intValue();
    }
}
