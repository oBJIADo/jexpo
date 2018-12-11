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
    fun rebuildString(string: String, symbol: String): Array<String>

    /**
     * Rebuild comment. Result of rebuild is a three Strings Which contains: date, author, comment.
     *
     * @param comment
     * String with unrebuilded comment.
     *
     * @return Rebuilded comment.
     */
    fun rebuildComment(comment: String, divideSymbols: String): Array<String?>

    /**
     * Rebuild jira fields.
     *
     * @param jiraField
     * String with jira field.
     *
     * @return Rebuilded field.
     */
    fun rebuildJiraField(jiraField: String): Array<String>

    /**
     * Rebuild jira fields.
     *
     * @param jiraField
     * String with jira field.
     *
     * @return Rebuilded field.
     */
    fun rebuildJiraField(jiraField: String, symbols: String): Array<String>


    fun buildTaskKey(key: String, modificator: String): String
}