package ru.tsystems.divider.dao.impl

import org.apache.log4j.Logger
import org.springframework.stereotype.Repository
import ru.tsystems.divider.dao.api.FeatureDao
import ru.tsystems.divider.entity.Feature
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

@Repository
class FeatureDaoImpl(@PersistenceContext override val entityManager: EntityManager) : GeneralDaoImpl<Feature>(), FeatureDao {

    /**
     * Get entity by param if it exist, return null if not.
     *
     * @param param String with param.
     * @return Entity if it exist either null.
     */
    override fun getByParam(param: String, nature: String): Feature? {
        logger.info("Get Feature by param, nature: $param, $nature")
        try {
            return entityManager.createQuery("select ent from Feature as ent where ent.title=:param AND ent.nature.title = :title", Feature::class.java)
                    .setParameter("param", param)
                    .singleResult
        } catch (noResExc: NoResultException) {
            logger.warn("No result founded. Entity: Feature, param, nature: $param, $nature")
            return null
        }

    }

    companion object {
        private val logger = Logger.getLogger(FeatureDaoImpl::class.java)
    }
}