package ru.tsystems.divider.dao.impl

import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.CommentDao
import ru.tsystems.divider.entity.Comment
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class CommentDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Comment>(),
    CommentDao {
    /**
     * Get a record from DB.
     * @param id            record id.
     * @param className     Entity class.
     * @return              Entity.
     */
    override fun find(id: Int): Comment? = super.find(id, Comment::class.java)

    override fun getReference(primaryKey: Int): Comment? = super.getReference(Comment::class.java, primaryKey)
}