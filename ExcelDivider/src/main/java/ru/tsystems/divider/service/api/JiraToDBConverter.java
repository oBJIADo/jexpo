package ru.tsystems.divider.service.api;

import java.io.IOException;

import ru.tsystems.divider.exceptions.NoShetException;

public interface JiraToDBConverter {

    /**
     * Read excel file which created by jira and write it to ad DB. For xlsx files only
     *
     * @param fileName
     *            Excel file name.
     * @param sheetName
     *            Excel sheet name with jira columns.
     * @param startRowIndex
     *            First row index with needed data.
     */
    void transferAll(String fileName, String sheetName, int startRowIndex)throws IOException, NoShetException;

    /**
     * Read excel file which created by jira and write it to ad DB. For xls files only
     *
     * @param fileName
     *            Excel file name.
     * @param sheetName
     *            Excel sheet name with jira columns.
     * @param startRowIndex
     *            First row index with needed data.
     */
    void transferAllOldExcell(String fileName, String sheetName, int startRowIndex)throws IOException, NoShetException;
    void transferAllCommentsOldExcell(String fileName, String sheetName, int startRowIndex)throws IOException, NoShetException;
    void transferAllComments(String fileName, String sheetName, int startRowIndex)throws IOException, NoShetException;
}
