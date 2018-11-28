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
    private val logger = Logger.getLogger(MessageWorker::class.java)

    override fun getSourceValue(sourcePath: String, sourceName: String): String? {
        try {
            return messageSource.getMessage(sourcePath + sourceName, null, Locale.getDefault())
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
            return null
        }

    }

    override fun getSourceValue(sourceName: String): String? { //todo: this method should be removed and all "sourcePath"s should be in constants!
        try {
            val message = messageSource.getMessage(sourceName, null, Locale.getDefault())
            return if (message == null || message.isEmpty()) null else message
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
            return null
        }
    }

    override fun getObligatorySourceValue(sourceName: String): String {
        try{
            val message = messageSource.getMessage(sourceName, null, Locale.getDefault())
            if(message == null) { //todo
                logger.warn("No message by source: $sourceName")
                throw PropertiesException(sourceName)
            }
            return message
        } catch (mesExc: NoSuchMessageException) {
            logger.warn("No message by source: $sourceName")
            throw PropertiesException(sourceName);
        }
    }
}