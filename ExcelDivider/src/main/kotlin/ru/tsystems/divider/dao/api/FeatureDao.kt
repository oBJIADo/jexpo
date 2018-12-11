package ru.tsystems.divider.dao.api

import ru.tsystems.divider.entity.Feature

interface FeatureDao : GeneralDao<Feature> {

    /**
     * Get entity by param if it exist, return null if not.
     * @param param     String with param.
     * @return          Entity if it exist either null.
     */
    fun getByParam(param: String, nature: String): Feature?

}