package ru.tsystems.divider.components.impl;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import ru.tsystems.divider.components.api.MessageWorker;

@Component
public class MessageWorkerImpl implements MessageWorker {
    private static final Logger logger = Logger.getLogger(MessageWorker.class);

    @Autowired
    private MessageSource messageSource;

    public String getSourceValue(String sourcePath, String sourceName) {
        try {
            return messageSource.getMessage(sourcePath + sourceName, null, Locale.getDefault());
        } catch (NoSuchMessageException mesExc) {
            logger.warn("No message by source: " + sourceName);
            return null;
        }

    }

    public String getSourceValue(String sourceName) { //todo: this method should be removed and all "sourcePath"s should be in constants!
        try {
            String message = messageSource.getMessage(sourceName, null, Locale.getDefault());
            return message == null || message.isEmpty() ? null : message;
        } catch (NoSuchMessageException mesExc) {
            logger.warn("No message by source: " + sourceName);
            return null;
        }

    }

}
