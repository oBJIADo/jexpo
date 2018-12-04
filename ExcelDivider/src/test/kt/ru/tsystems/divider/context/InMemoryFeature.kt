package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.FeatureDao
import ru.tsystems.divider.entity.Feature

object InMemoryFeature : InMemoryDaoGeneral<Feature>(), FeatureDao, ContextSimmulator<Feature> {

    init {
        reset()
    }

    /**
     * Get entity by param if it exist, return null if not.
     *
     * @param param  String with param.
     * @param nature
     * @return Entity if it exist either null.
     */
    override fun getByParam(param: String, nature: String): Feature? {
        for (feature in database) {
            if (param == feature.title && nature == feature.nature?.title) {
                return feature
            }
        }
        return null
    }
}