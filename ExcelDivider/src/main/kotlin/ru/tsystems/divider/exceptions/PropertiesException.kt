package ru.tsystems.divider.exceptions

class PropertiesException : Exception {

    /**
     * Constructs a new exception with `null` as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to [.initCause].
     */
    constructor(
        propertyName: String,
        comment: String
    ) : super("Wrong property! Property name: $propertyName; $comment") {
    }

    constructor(property: String) : super("No obligatory property: $property")
}