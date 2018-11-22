package ru.tsystems.divider.context;

import ru.tsystems.divider.components.api.MessageWorker;

public class MessageSimulator implements MessageWorker {

    private MessageSimulator() {//todo: switch to map
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
        return simulateMessageWorker(sourcePath + sourceName);
    }

    @Override
    public String getSourceValue(String sourceName) {
        return simulateMessageWorker(sourceName);
    }

    private String simulateMessageWorker(String str) {
        switch (str) {
            case "modificator.keys.pre":
                return "AD-";
            case "format.read.date":
                return null;
            case "symbol.divideSymbol.employee":
                return ",";
            case "symbol.divideSymbol.anotherTasks":
                return ",";
            case "symbol.divideSymbol.comments":
                return ";";
            case "symbol.divideSymbol.another":
                return ",";
            case "format.read.comment":
                return "date,author";
            default:
                return null;
        }
    }

}
