package ru.tsystems.divider.service.api

import ru.tsystems.divider.entity.Feature

interface FeatureService {
    /**
     * Find One param entity by this param.
     *
     * @param param
     * param for searching.
     * @param nature
     * @return Entity if it exist, either null.
     */
    fun findByParam(param: String, nature: String): Feature?

    /**
     * Persist into db OneParamEntity.
     *
     * @param entity
     * One param Entity.
     */
    fun persist(entity: Feature)

    fun createFeatur(title: String, nature: String): Feature
}