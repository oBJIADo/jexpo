package ru.tsystems.divider.exceptions;

public class ExcelFormatException extends Exception {

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     *
     */
    public ExcelFormatException(String fileFormat, String fileName) {
        super("This application cant work with current format. File format: " + fileFormat + "; file name: "
              + fileName);
    }
}
