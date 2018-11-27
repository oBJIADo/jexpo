package ru.tsystems.divider.context;

import ru.tsystems.divider.components.api.MessageWorker;

import java.util.HashMap;
import java.util.Map;

public class MessageSimulator implements MessageWorker, ContextSimmulator<String> {

    private Map<String, String> properties;

    public void reset() {
        properties = new HashMap<>();
        properties.put("modificator.keys.pre", "AD-");
        properties.put("format.read.date", null);
        properties.put("symbol.divideSymbol.employee", ",");
        properties.put("symbol.divideSymbol.anotherTasks", ",");
        properties.put("symbol.divideSymbol.comments", ";");
        properties.put("symbol.divideSymbol.another", ";");
        properties.put("format.read.comment", "date,author");
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

    @Override
    public String getSourceValue(String sourcePath, String sourceName) {
        return properties.get(sourcePath + sourceName);
    }

    @Override
    public String getSourceValue(String sourceName) {
        return properties.get(sourceName);
    }

}
