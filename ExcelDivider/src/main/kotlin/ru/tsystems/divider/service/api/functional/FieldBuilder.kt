package ru.tsystems.divider.service.api.functional

interface FieldBuilder {

    /**
     * Divide one string to substrings, delete all useless spaces.
     *
     * @param string
     * String for rebuilding.
     *
     * @param symbol
     * Symbol for dividing the String.
     *
     * @return List with rebuilded strings.
     */
    fun rebuildString(string: String?, symbol: String): Array<String>


    fun buildTaskKey(key: String, modificator: String): String
}