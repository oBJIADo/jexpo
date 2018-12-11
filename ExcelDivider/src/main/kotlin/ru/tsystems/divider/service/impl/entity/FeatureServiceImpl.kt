package ru.tsystems.divider.service.impl.entity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.tsystems.divider.dao.api.FeatureDao
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.service.api.entity.FeatureService
import ru.tsystems.divider.service.api.entity.NatureService

@Service
class FeatureServiceImpl(@param:Autowired private val featureDao: FeatureDao,
                         @param:Autowired private val natureService: NatureService) : FeatureService {

    /**
     * Find One param entity by this param.
     *
     * @param param
     * param for searching.
     * @param nature
     * @return Entity if it exist, either null.
     */
    @Transactional
    override fun findByParam(param: String, nature: String): Feature? {
        return featureDao.getByParam(param, nature)
    }

    /**
     * Persist into db OneParamEntity.
     *
     * @param entity
     * One param Entity.
     */
    @Transactional //todo: rename or delete.
    override fun persist(entity: Feature) {
        featureDao.persist(entity)
    }

    /**
     * todo
     * @param title
     * @param nature
     */
    @Transactional
    override fun createFeature(title: String, nature: String): Feature {
        return Feature(title, natureService.getOrAddNatureByTitle(nature))
    }
}
