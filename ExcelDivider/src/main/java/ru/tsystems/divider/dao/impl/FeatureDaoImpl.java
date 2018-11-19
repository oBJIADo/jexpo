package ru.tsystems.divider.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.tsystems.divider.dao.api.FeatureDao;
import ru.tsystems.divider.entity.Feature;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class FeatureDaoImpl extends GeneralDaoImpl<Feature> implements FeatureDao {
    private static final Logger logger = Logger.getLogger(FeatureDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get entity by param if it exist, return null if not.
     *
     * @param param String with param.
     * @return Entity if it exist either null.
     */
    @Override
    public Feature getByParam(String param, String nature) {
        logger.info("Get Feature by param, nature: " + param + ", " + nature);
        try {
            return entityManager.createQuery("select ent from Feature as ent where ent.title=:param AND ent.nature.title = :title"
                    , Feature.class)
                    .setParameter("param", param)
                    .getSingleResult();
        } catch (NoResultException noResExc) {
            logger.warn("No result founded. Entity: Feature, param, nature: " + param + ", " + nature);
            return null;
        }
    }
}
