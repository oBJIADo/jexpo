package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.CommentDao
import ru.tsystems.divider.entity.Comment

object InMemoryComment : InMemoryDaoGeneral<Comment>(), CommentDao, ContextSimmulator<Comment> {

    init {
        reset()
    }

}