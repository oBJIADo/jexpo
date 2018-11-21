package ru.tsystems.divider.common;

import ru.tsystems.divider.components.api.MessageWorker;

public class MessageSimulator implements MessageWorker {

    private MessageSimulator() {
    }

    private static MessageSimulator messageSimulator;

    public static MessageSimulator getMessageWorker() {
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
                return null;
            case "format.read.date":
                return null;
            case "symbol.divideSymbol.employee":
                return null;
            case "symbol.divideSymbol.anotherTasks":
                return null;
            case "symbol.divideSymbol.comments":
                return null;
            case "symbol.divideSymbol.another":
                return null;
            case "format.read.comment":
                return null;
            default:
                return null;
        }
    }

}
