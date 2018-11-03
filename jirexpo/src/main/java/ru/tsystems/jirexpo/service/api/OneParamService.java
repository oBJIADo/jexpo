package ru.tsystems.jirexpo.service.api;

import ru.tsystems.jirexpo.entity.OneParamEntity;

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
    public Entity findByParam(String param, Class<? extends Entity> className);

    /**
     * Persist into db OneParamEntity.
     *
     * @param entity
     *            One param Entity.
     */
    public void persist(Entity entity);
}
