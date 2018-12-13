package ru.tsystems.divider.service.impl.functional

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.FeatureDao
import ru.tsystems.divider.dao.api.NatureDao
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Nature
import ru.tsystems.divider.service.api.functional.FeatureBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER

@Service
class FeatureBuilderImpl(
    @Autowired messageWorker: MessageWorker,
    @Autowired val featureDao: FeatureDao,
    @Autowired val natureDao: NatureDao
) : FeatureBuilder {

    private val fieldBuilder: FieldBuilder
    private val DIVIDER: String

    init {
        fieldBuilder = FieldBuilderImpl()
        DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_ANOTHER)
    }

    override fun buildFeature(feature: String, nature: String): Feature {
        return featureDao.getByParam(feature, nature) ?: createFeature(feature, nature)
    }

    override fun buildFeatureSet(features: String, nature: String): Set<Feature> {
        val components = HashSet<Feature>()
        val params = fieldBuilder.rebuildJiraField(features, DIVIDER)

        var feature: Feature
        for (param in params) {
            if (!param.isEmpty()) {
                feature = buildFeature(param, nature)
                components.add(feature)
            } else {
                logger.warn("Empty feature when try to build feature Set. nature: $nature, feature: $features")
            }
        }
        return components
    }


    fun createFeature(title: String, nature: String): Feature {
        return Feature(title, getOrAddNatureByTitle(nature))
    }

    fun getOrAddNatureByTitle(title: String): Nature {
        if (title.isEmpty()) {
            throw IllegalArgumentException("Nature cannot be null or empty!")
        }

        var nature: Nature? = natureDao.getByTitle(title)

        if (nature == null) {
            nature = Nature(title)
            natureDao.persist(nature)
        }

        return nature
    }

    companion object {
        private val logger = Logger.getLogger(FeatureBuilderImpl::class.java)
    }
}