package ru.tsystems.jirexpo.components.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import ru.tsystems.jirexpo.components.api.MessageWorker;

import java.util.Locale;

@Component
public class MessageWorkerImpl implements MessageWorker {
    private static final Logger logger = Logger.getLogger(MessageWorkerImpl.class);

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

    public String getSourceValue(String sourceName) {
        try {
            String message = messageSource.getMessage(sourceName, null, Locale.getDefault());
            return message == null || message.isEmpty() ? null : message;
        } catch (NoSuchMessageException mesExc) {
            logger.warn("No message by source: " + sourceName);
            return null;
        }

    }

}
