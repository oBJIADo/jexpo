package ru.tsystems.divider.dao.api;

import ru.tsystems.divider.entity.Feature;

public interface FeatureDao extends GeneralDao<Feature>{

    /**
     * Get entity by param if it exist, return null if not.
     * @param param     String with param.
     * @return          Entity if it exist either null.
     */
    Feature getByParam(String param, String nature);

}
