package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.CommentDao
import ru.tsystems.divider.entity.Comment
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class CommentDaoImpl(@PersistenceContext override val entityManager: EntityManager)
    : GeneralDaoImpl<Comment>(), CommentDao