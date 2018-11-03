package ru.tsystems.divider.service.impl;

import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.tsystems.divider.components.api.MessageWorker;
import ru.tsystems.divider.service.api.FieldBuilder;

@Service
public class FieldBuilderImpl implements FieldBuilder {
    private static final Logger logger = Logger.getLogger(FieldBuilderImpl.class);

    private final int INDEX_ID;

    private final int INDEX_AUTHOR;

    private final int INDEX_DATE;

    private final int INDEX_COMMENT;

    private final static String NAME_ID = "id";

    private final static String NAME_AUTHOR = "author";

    private final static String NAME_DATE = "date";

    private final static String NAME_NOTHING = "not";

    public FieldBuilderImpl(@Autowired MessageWorker messageWorker) throws PropertyException {
        int id = -1;
        int author = -1;
        int date = -1;
        int comment;

        String format = messageWorker.getSourceValue("format.read.comment");
        if (format == null) {
            logger.error("No keywords, return all cell as comment text");
            comment = 0;
        } else {
            String[] formats = format.split(",");
            for (int i = 0; i < formats.length; i++) {
                switch (formats[i]) {
                case NAME_ID:
                    id = i;
                    break;
                case NAME_AUTHOR:
                    author = i;
                    break;
                case NAME_DATE:
                    date = i;
                    break;
                case NAME_NOTHING:
                    break;
                default:
                    throw new PropertyException("format.read.comment", "Wrong keyword at index: " + i);
                }
            }

            comment = formats.length;
        }

        INDEX_ID = id;
        INDEX_AUTHOR = author;
        INDEX_DATE = date;
        INDEX_COMMENT = comment;
    }

    /**
     * Divide one string to substrings, delete all useless spaces.
     *
     * @param string
     *            String for rebuilding.
     * @param symbol
     *            Symbol for dividing the String.
     * @return List with rebuilded strings.
     */
    @Override
    public String[] rebuildString(String string, String symbol) {
        if (string == null)
            return null;

        String[] result;
        result = string.split(symbol);
        for (int i = 0; i < result.length; i++)
            result[i] = result[i].trim();
        return result;
    }

    /**
     * Check a string for extra pairs (spaces) and remove it.
     * 
     * @param target
     *            The string which can contains extra spaces.
     * @return String without extra spaces.
     */
    private String removeExtraSpaces(String target) {
        StringBuilder result = new StringBuilder(target.length());
        char curSymbol;
        char nextSymbol = target.charAt(0);

        for (int i = 0; i < target.length() - 1; i++) {
            curSymbol = nextSymbol;
            nextSymbol = target.charAt(i + 1);
            if (!(curSymbol == ' ' && nextSymbol == ' ')) {
                result.append(curSymbol);
            }
        }

        result.append(nextSymbol);
        return result.toString();
    }

    /**
     * Remove Extra spaces and extra lines.
     * 
     * @param target
     *            The string which can contains extra symbols.
     * @return String without extra symbols.
     */
    private String removeExtraSpacesAndLines(String target) {
        if (target.isEmpty()) {
            logger.error("Empty target");
            return null;
        }

        target = removeExtraSpaces(target);
        String[] splitingResult = target.split("\n");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < splitingResult.length; i++) {
            if (!splitingResult[i].isEmpty()) {
                splitingResult[i] = splitingResult[i].trim();
                result.append(splitingResult[i]);
                result.append('\n');
            }
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    /**
     * Rebuild comment. Result of rebuild is a three Strings Which contains: date, author, comment.
     *
     * @param comment
     *            String with unrebuilded comment.
     * @return Rebuilded comment.
     */
    @Override
    public String[] rebuildComment(String comment, String divideSymbols) {
        if (comment == null || comment.isEmpty())
            return null;

        String key;
        String date;
        String author;
        String commentText;

        int indexId = INDEX_ID;
        int indexDate = INDEX_DATE;
        int indexAuthor = INDEX_AUTHOR;

        int index = 0;

        try {
            for (int i = 0; i < INDEX_COMMENT; i++) {
                index = comment.indexOf(divideSymbols, index + 1);
            }

            commentText = comment.substring(index + 1).trim();

            String[] dividedComment = comment.substring(0, index).split(divideSymbols);
            key = INDEX_ID == -1 ? null : dividedComment[indexId].trim();
            date = INDEX_DATE == -1 ? null : dividedComment[indexDate].trim();
            author = INDEX_AUTHOR == -1 ? null : dividedComment[indexAuthor].trim();
            return new String[] { key, date, author, commentText };
        } catch (IndexOutOfBoundsException indexExc) {
            index = comment.indexOf(divideSymbols);
            key = INDEX_ID == -1 ? null : comment.substring(0, index);
            commentText = comment.substring(index + 1);
            logger.error("Wrong comment, returned as comment. Key: " + key);
            return new String[] { key, null, null, commentText };
        }

    }

    /**
     * Rebuild jira fields.
     *
     * @param jiraField
     *            String with jira field.
     * @return Rebuilded field.
     */
    @Override
    public String[] rebuildJiraField(String jiraField) {
        return rebuildString(jiraField, ",");
    }

    /**
     * Rebuild jira fields.
     *
     * @param jiraField
     *            String with jira field.
     * @return Rebuilded field.
     */
    @Override
    public String[] rebuildJiraField(String jiraField, String symbols) {
        return rebuildString(jiraField, symbols);
    }

    @Override
    public String buildTaskKey(String key, String modificator) {
        String numericPart = getNumPart(key);

        if (numericPart == null)
            return null;
        else
            return modificator + numericPart;
    }

    private String getNumPart(String key) {
        int firstIndex;
        int lastIndex;
        int i = 0;

        while (i < key.length() && !Character.isDigit(key.charAt(i))) {
            i++;
        }

        if (i == key.length()) {
            return null;
        } else {
            firstIndex = i;
            i = key.length() - 1;
        }

        while (i >= 0 && !Character.isDigit(key.charAt(i))) {
            i--;
        }

        lastIndex = i + 1;

        return key.substring(firstIndex, lastIndex);
    }

}
