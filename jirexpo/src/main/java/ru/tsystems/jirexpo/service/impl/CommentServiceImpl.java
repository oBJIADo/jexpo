package ru.tsystems.jirexpo.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsystems.jirexpo.components.impl.Converter;
import ru.tsystems.jirexpo.dao.api.CommentDao;
import ru.tsystems.jirexpo.dto.CommentDto;
import ru.tsystems.jirexpo.entity.Comment;
import ru.tsystems.jirexpo.service.api.CommentService;

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

    /**
     * Find all task's comments
     *
     * @param taskKey
     *            task's key
     * @return List with commentsDto
     */
    @Override
    @Transactional
    public List<CommentDto> findCommentByTask(String taskKey) {
        List<CommentDto> comments = commentDao.getByTaskKey(taskKey).stream().map(Converter::convertToDto)
                .collect(Collectors.toList());
        Collections.reverse(comments);
        return comments;
    }
}
