package ru.tsystems.divider.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ru.tsystems.divider.dao.api.OneParamDao;
import ru.tsystems.divider.entity.OneParamEntity;

@Repository
public class OneParamDaoImpl<Entity extends OneParamEntity> extends GeneralDaoImpl<Entity>
        implements OneParamDao<Entity> {
    private static final Logger logger = Logger.getLogger(OneParamDaoImpl.class);

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Get entity by param if it exist, return null if not.
     *
     * @param param String with param.
     * @param className Entity class name.
     * @return Entity if it exist either null.
     */
    @Override
    public Entity getByParam(String param, Class<? extends Entity> className) {
        logger.info("Get OneParamEntity (" + className.getSimpleName() + ") by param: " + param);
        try {
            return entityManager.createQuery("from " + className.getSimpleName() + " as ent where ent.param=:param"
                    , className)
                    .setParameter("param", param)
                    .getSingleResult();
        }
        catch (NoResultException noResExc){
            logger.warn("No result founded. Entity: " + className.getSimpleName() + ", param: " + param);
            return null;
        }
    }
}
