package ru.tsystems.divider.context;

public interface ContextSimmulator<T> {

    void reset();
    void setValue(String key, T value);
    T getValue(String key);

}
