package ru.tsystems.divider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsystems.divider.dao.api.OneParamDao;
import ru.tsystems.divider.entity.OneParamEntity;
import ru.tsystems.divider.service.api.OneParamService;

@Service
public class OneParamServiceImpl<Entity extends OneParamEntity> implements OneParamService<Entity> {

    @Autowired
    OneParamDao oneParamDao;

    /**
     * Find One param entity by this param.
     *
     * @param param
     *            param for searching.
     * @param className
     *            Class of the current entity.
     * @return Entity if it exist, either null.
     */
    @Override
    @Transactional
    public Entity findByParam(String param, Class<? extends Entity> className) {
        return (Entity) oneParamDao.getByParam(param, className);
    }

    /**
     * Persist into db OneParamEntity.
     *
     * @param entity
     *            One param Entity.
     */
    @Override
    @Transactional
    public void persist(Entity entity) {
        oneParamDao.persist(entity);
    }
}
