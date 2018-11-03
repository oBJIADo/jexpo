package ru.tsystems.divider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.tsystems.divider.dao.api.CommentDao;
import ru.tsystems.divider.entity.Comment;
import ru.tsystems.divider.service.api.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    /**
     * Persist into db comment entity.
     * 
     * @param comment
     *            Comment entity.
     */
    @Override
    public void persist(Comment comment) {
        commentDao.persist(comment);
    }

}
