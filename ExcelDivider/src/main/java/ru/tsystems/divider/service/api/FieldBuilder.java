package ru.tsystems.divider.service.api;

//todo: to kotlin
public interface FieldBuilder {

    /**
     * Divide one string to substrings, delete all useless spaces.
     *
     * @param string
     *            String for rebuilding.
     *
     * @param symbol
     *            Symbol for dividing the String.
     *
     * @return List with rebuilded strings.
     */
    String[] rebuildString(String string, String symbol);

    /**
     * Rebuild comment. Result of rebuild is a three Strings Which contains: date, author, comment.
     *
     * @param comment
     *            String with unrebuilded comment.
     *
     * @return Rebuilded comment.
     */
    String[] rebuildComment(String comment, String divideSymbols);

    /**
     * Rebuild jira fields.
     *
     * @param jiraField
     *            String with jira field.
     *
     * @return Rebuilded field.
     */
    String[] rebuildJiraField(String jiraField);

    /**
     * Rebuild jira fields.
     *
     * @param jiraField
     *            String with jira field.
     *
     * @return Rebuilded field.
     */
    String[] rebuildJiraField(String jiraField, String symbols);


    String buildTaskKey(String key, String modificator);
}
