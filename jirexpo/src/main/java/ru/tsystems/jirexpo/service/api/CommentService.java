package ru.tsystems.jirexpo.service.api;

import java.util.List;

import ru.tsystems.jirexpo.dto.CommentDto;
import ru.tsystems.jirexpo.entity.Comment;

public interface CommentService {

    /**
     * Persist into db comment entity.
     *
     * @param comment
     *            Comment entity.
     */
    public void persist(Comment comment);

    /**
     * Find all task's comments
     * @param taskKey   task's key
     * @return List with commentsDto
     */
    public List<CommentDto> findCommentByTask(String taskKey);
}
