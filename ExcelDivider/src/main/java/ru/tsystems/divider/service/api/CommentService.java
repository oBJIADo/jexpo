package ru.tsystems.divider.service.api;

import ru.tsystems.divider.entity.Comment;

public interface CommentService {

    /**
     * Persist into db comment entity.
     *
     * @param comment
     *            Comment entity.
     */
    void persist(Comment comment);

}
