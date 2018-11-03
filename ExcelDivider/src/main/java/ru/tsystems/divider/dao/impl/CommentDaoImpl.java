package ru.tsystems.divider.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ru.tsystems.divider.dao.api.CommentDao;
import ru.tsystems.divider.entity.Comment;

@Repository
public class CommentDaoImpl extends GeneralDaoImpl<Comment> implements CommentDao {

    @PersistenceContext
    private EntityManager entityManager;


}
