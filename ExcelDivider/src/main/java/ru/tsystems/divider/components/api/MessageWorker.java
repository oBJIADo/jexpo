package ru.tsystems.divider.components.api;

public interface MessageWorker {
    String getSourceValue(String sourcePath, String sourceName);
    String getSourceValue(String sourceName); //todo: watch to implementation
}
