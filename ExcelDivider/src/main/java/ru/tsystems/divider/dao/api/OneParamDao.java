package ru.tsystems.divider.dao.api;

import ru.tsystems.divider.entity.OneParamEntity;

public interface OneParamDao<Entity extends OneParamEntity> extends GeneralDao<Entity>{

    /**
     * Get entity by param if it exist, return null if not.
     * @param param     String with param.
     * @param className Entity class name.
     * @return          Entity if it exist either null.
     */
    Entity getByParam(String param, Class<? extends Entity> className);
}
