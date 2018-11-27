package ru.tsystems.divider.context;

import ru.tsystems.divider.components.api.MessageWorker;

import java.util.HashMap;
import java.util.Map;

public class MessageSimulator implements MessageWorker {

    private Map<String, String> properties;

    public void resetProperties() {
        properties = new HashMap<>();
        properties.put("modificator.keys.pre", "AD-");
        properties.put("format.read.date", null);
        properties.put("symbol.divideSymbol.employee", ",");
        properties.put("symbol.divideSymbol.anotherTasks", ",");
        properties.put("symbol.divideSymbol.comments", ";");
        properties.put("symbol.divideSymbol.another", ";");
        properties.put("format.read.comment", "date,author");
    }

    public void setProp(String key, String value){
        properties.put(key, value);
    }

    public String getProperty(String key, String value){
        return properties.get(key);
    }

    private MessageSimulator() {
        resetProperties();
    }


    private static MessageSimulator messageSimulator;

    protected static MessageSimulator getMessageWorker() {
        if (messageSimulator == null) {
            messageSimulator = new MessageSimulator();
        }
        return messageSimulator;
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
