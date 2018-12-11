package ru.tsystems.divider.utils.api

import ru.tsystems.divider.exceptions.PropertiesException

interface MessageWorker {
    fun getSourceValue(sourceName: String): String?
    fun getSourceValue(sourceName: String, default: String): String
    fun getIntSourceValue(sourceName: String): Int?
    fun getIntSourceValue(sourceName: String, default: Int): Int
    fun getBoolSourceValue(sourceName: String): Boolean?
    fun getBoolSourceValue(sourceName: String, default: Boolean): Boolean
    @Throws(PropertiesException::class)
    fun getObligatorySourceValue(sourceName: String): String
}