package ru.tsystems.divider.dao.impl

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.NatureDao
import ru.tsystems.divider.entity.Nature
import javax.persistence.EntityManager
import javax.persistence.NoResultException

@Repository
class NatureDaoImpl(@Autowired override val entityManager: EntityManager) : GeneralDaoImpl<Nature>(), NatureDao {

    companion object {
        private val logger = Logger.getLogger(NatureDaoImpl::class.java)
    }

    override fun getByTitle(title: String): Nature? {
        logger.info("Get Nature by title: $title")
        try {
            return entityManager.createQuery("select nat from Nature as nat where nat.title=:title", Nature::class.java)
                .setParameter("title", title).singleResult
        } catch (noResExc: NoResultException) {
            logger.warn("Get Nature by title: $title")
        }
        return null
    }
}