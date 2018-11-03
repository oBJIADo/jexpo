package ru.tsystems.jirexpo.components.api;

public interface MessageWorker {
    public String getSourceValue(String sourcePath, String sourceName);
    public String getSourceValue(String sourceName);
}
