package ru.tsystems.divider.utils.api

import ru.tsystems.divider.exceptions.PropertiesException

interface MessageWorker {
    fun getSourceValue(sourcePath: String, sourceName: String): String?
    fun getSourceValue(sourceName: String): String?  //todo: watch to implementation
    @Throws(PropertiesException::class)
    fun getObligatorySourceValue(sourceName: String): String
}