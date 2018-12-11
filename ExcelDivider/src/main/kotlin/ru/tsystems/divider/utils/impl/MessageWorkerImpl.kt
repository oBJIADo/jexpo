package ru.tsystems.divider.utils.impl

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.stereotype.Component
import ru.tsystems.divider.exceptions.PropertiesException
import ru.tsystems.divider.utils.api.MessageWorker
import java.util.*

@Component
class MessageWorkerImpl(@param:Autowired private val messageSource: MessageSource) : MessageWorker {

    companion object {
        private val logger = Logger.getLogger(MessageWorker::class.java)
    }

    override fun getSourceValue(sourceName: String, default: String): String {
        try {
            val message = getMessage(sourceName)
            return if (message.isEmpty()) default else message
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
        }
        return default
    }

    override fun getSourceValue(sourceName: String): String? {
        try {
            val message = getMessage(sourceName)
            return if (message.isEmpty()) null else message
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
        }
        return null
    }

    override fun getIntSourceValue(sourceName: String): Int? {
        try {
            return getIntMessage(sourceName)
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
        }
        return null
    }

    override fun getIntSourceValue(sourceName: String, default: Int): Int {
        try {
            return getIntMessage(sourceName) ?: default
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
        }
        return default
    }

    override fun getBoolSourceValue(sourceName: String): Boolean? {
        try {
            return getBoolMessage(sourceName)
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
        }
        return null
    }

    override fun getBoolSourceValue(sourceName: String, default: Boolean): Boolean {
        try {
            return getBoolMessage(sourceName) ?: default
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
        }
        return default
    }

    override fun getObligatorySourceValue(sourceName: String): String {
        try {
            return getMessage(sourceName)
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
            throw PropertiesException(sourceName)
        }
    }

    private fun getMessage(sourceName: String):
            String = messageSource.getMessage(sourceName, null, Locale.getDefault())

    private fun getIntMessage(sourceName: String): Int? =
        try {
            Integer.valueOf(messageSource.getMessage(sourceName, null, Locale.getDefault()))
        } catch (nfexc: NumberFormatException) {
            logger.warn("Wrong number: $sourceName; Returned -1")
            -1
        }

    private fun getBoolMessage(sourceName: String): Boolean? {
        val message = messageSource.getMessage(sourceName, null, Locale.getDefault())
        return if ("false".equals(message.toLowerCase())) {
            false
        } else if ("true".equals(message.toLowerCase())) {
            true
        } else {
            val logMessage = "Value not boolean: $sourceName"
            logger.warn(logMessage)
            throw IllegalArgumentException(logMessage)
        }
    }
}