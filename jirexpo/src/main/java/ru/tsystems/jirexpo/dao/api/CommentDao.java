package ru.tsystems.jirexpo.dao.api;

import java.util.List;

import ru.tsystems.jirexpo.entity.Comment;

public interface CommentDao extends GeneralDao<Comment> {
    /**
     * Get by task's key all comments.
     * @param taskKey  Task's key.
     * @return  List with comments.
     */
    List<Comment> getByTaskKey(String taskKey);
}
