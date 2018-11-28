package ru.tsystems.divider.exceptions

class ExcelFormatException
/**
 * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
 * be initialized by a call to [.initCause].
 *
 */
(fileFormat: String, fileName: String) : Exception("This application cant work with current format. File format: " + fileFormat + "; file name: "
        + fileName)