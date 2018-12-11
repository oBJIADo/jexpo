package ru.tsystems.divider.context

interface ContextSimmulator<T> {

    fun reset()
    fun setValue(key: String, value: T)
    fun getValue(key: String): T?

}