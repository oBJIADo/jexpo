package ru.tsystems.divider.service.impl.functional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.service.api.functional.DataService
import ru.tsystems.divider.service.api.functional.FeatureBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER

@Service
class FeatureBuilderImpl(
    @Autowired messageWorker: MessageWorker,
    @Autowired val dataService: DataService
) : FeatureBuilder {

    private val fieldBuilder: FieldBuilder
    private val DIVIDER: String

    init {
        fieldBuilder = FieldBuilderImpl()
        DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_ANOTHER)
    }

    override fun buildFeature(feature: String, nature: String): Feature {
        return dataService.findOrCreateFeature(feature, nature)
    }

    override fun buildFeatureSet(features: String, nature: String): Set<Feature> {
        val components = HashSet<Feature>()
        val params = fieldBuilder.rebuildString(features, DIVIDER)

        var feature: Feature
        for (param in params) {
            if (!param.isEmpty()) {
                feature = buildFeature(param, nature)
                components.add(feature)
            } else {
                //logger.warn("Empty feature when try to build feature Set. nature: $nature, feature: $features")
            }
        }
        return components
    }

    companion object {
        //private val logger = //logger.getLogger(FeatureBuilderImpl::class.java)
    }
}