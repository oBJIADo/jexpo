package ru.tsystems.divider.service.api;

import ru.tsystems.divider.entity.OneParamEntity;

public interface OneParamService<Entity extends OneParamEntity> {

    /**
     * Find One param entity by this param.
     * 
     * @param param
     *            param for searching.
     * @param className
     *            Class of the current entity.
     * @return Entity if it exist, either null.
     */
    Entity findByParam(String param, Class<? extends Entity> className);

    /**
     * Persist into db OneParamEntity.
     *
     * @param entity
     *            One param Entity.
     */
    void persist(Entity entity);
}
