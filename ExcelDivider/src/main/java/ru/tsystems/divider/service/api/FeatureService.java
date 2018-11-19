package ru.tsystems.divider.service.api;

import ru.tsystems.divider.entity.Feature;

public interface FeatureService {
    /**
     * Find One param entity by this param.
     *
     * @param param
     *            param for searching.
     * @param nature
     * @return Entity if it exist, either null.
     */
    public Feature findByParam(String param, String nature);

    /**
     * Persist into db OneParamEntity.
     *
     * @param entity
     *            One param Entity.
     */
    public void persist(Feature entity);

    public Feature createFeatur(String title, String nature);
}
