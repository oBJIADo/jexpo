package ru.tsystems.divider.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.tsystems.divider.dao.api.NatureDao;
import ru.tsystems.divider.entity.Nature;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class NatureDaoImpl extends GeneralDaoImpl<Nature> implements NatureDao {
    private static final Logger logger = Logger.getLogger(NatureDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Override
    public Nature getByTitle(String title) {
        logger.info("Get Nature by title: " + title);
        try {
            return entityManager.createQuery("select nat from Nature as nat where nat.title=:title", Nature.class)
                    .setParameter("title", title).getSingleResult();
        } catch (NoResultException noResExc) {
            logger.warn("Get Nature by title: " + title);
            return null; //todo: not null!
        }
    }
}
