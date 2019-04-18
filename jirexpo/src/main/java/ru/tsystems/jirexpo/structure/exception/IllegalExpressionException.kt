package ru.tsystems.jirexpo.structure.exception

class IllegalExpressionException(message: String) : Exception("Cannot build expression! $message") {
    constructor() : this("Unknown reason.")
}