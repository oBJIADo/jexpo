package ru.tsystems.divider.exceptions;

public class PropertiesException extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public PropertiesException(String propertyName, String comment) {
        super("Wrong property! Property name: " + propertyName + "; " + comment);
    }
}
