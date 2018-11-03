package ru.tsystems.jirexpo.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ru.tsystems.jirexpo.dao.api.CommentDao;
import ru.tsystems.jirexpo.entity.Comment;

@Repository
public class CommentDaoImpl extends GeneralDaoImpl<Comment> implements CommentDao {

    private static final Logger logger = Logger.getLogger(CommentDaoImpl.class);


    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get by task's key all comments.
     *
     * @param taskKey
     *            Task's key.
     * @return List with comments.
     */
    @Override
    public List<Comment> getByTaskKey(String taskKey) {
        logger.info("Get comment with task key: " + taskKey);
        return entityManager.createQuery("from Comment as comment where comment.task.keys=:taskKey order by comment.id desc", Comment.class)
                .setParameter("taskKey", taskKey).getResultList();
    }
}
