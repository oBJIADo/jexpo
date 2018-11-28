package ru.tsystems.divider.context;


import org.jetbrains.annotations.NotNull;
import ru.tsystems.divider.exceptions.PropertiesException;
import ru.tsystems.divider.utils.api.MessageWorker;

import java.util.HashMap;
import java.util.Map;

import static ru.tsystems.divider.utils.constants.PropertiesConstantsKt.PROPS_FORMAT_READ_COMMENT;
import static ru.tsystems.divider.utils.constants.PropertiesConstantsKt.PROPS_FORMAT_READ_DATE;
import static ru.tsystems.divider.utils.constants.PropertiesConstantsKt.PROPS_MODIFICATOR_KEYS_PRE;
import static ru.tsystems.divider.utils.constants.PropertiesConstantsKt.PROPS_SYMBOLS_SOURCE_ANOTHER;
import static ru.tsystems.divider.utils.constants.PropertiesConstantsKt.PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS;
import static ru.tsystems.divider.utils.constants.PropertiesConstantsKt.PROPS_SYMBOLS_SOURCE_COMMENTS;
import static ru.tsystems.divider.utils.constants.PropertiesConstantsKt.PROPS_SYMBOLS_SOURCE_EMPLOYEE;

public class MessageSimulator implements MessageWorker, ContextSimmulator<String> {

    private Map<String, String> properties;

    public void reset() {
        properties = new HashMap<>();
        properties.put(PROPS_MODIFICATOR_KEYS_PRE, "AD-");
        properties.put(PROPS_FORMAT_READ_DATE, null);
        properties.put(PROPS_SYMBOLS_SOURCE_EMPLOYEE, ",");
        properties.put(PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS, ",");
        properties.put(PROPS_SYMBOLS_SOURCE_COMMENTS, ";");
        properties.put(PROPS_SYMBOLS_SOURCE_ANOTHER, ";");
        properties.put(PROPS_FORMAT_READ_COMMENT, "date,author");
    }

    public void setValue(String key, String value){
        properties.put(key, value);
    }

    public String getValue(String key){
        return properties.get(key);
    }

    protected MessageSimulator() {
        reset();
    }


    private static MessageSimulator messageSimulator;

    protected static MessageSimulator getMessageWorker() {
        if (messageSimulator == null) {
            messageSimulator = new MessageSimulator();
        }
        return messageSimulator;
    }

    @NotNull
    @Override
    public String getObligatorySourceValue(@NotNull String sourceName) throws PropertiesException{
        if(properties.get(sourceName) == null) {
            throw new PropertiesException(sourceName);
        }
        return properties.get(sourceName);
    }

    @Override
    public String getSourceValue(String sourcePath, String sourceName) {
        return properties.get(sourcePath + sourceName);
    }

    @Override
    public String getSourceValue(String sourceName) {
        return properties.get(sourceName);
    }

}
