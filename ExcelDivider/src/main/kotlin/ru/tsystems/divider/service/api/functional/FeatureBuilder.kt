package ru.tsystems.divider.service.api.functional

import ru.tsystems.divider.entity.Feature

interface FeatureBuilder{
    fun buildFeature(feature: String, nature: String): Feature
    fun buildFeatureSet(features: String, nature: String): Set<Feature>
}