package ru.tsystems.divider.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.ExcelFileReader;
import ru.tsystems.divider.service.api.JiraToDBConverter;
import ru.tsystems.divider.service.api.RowToEntityConverter;

@Service
public class JiraToDBConverterImpl implements JiraToDBConverter {
    private static final Logger logger = Logger.getLogger(JiraToDBConverterImpl.class);

    @Autowired
    private RowToEntityConverter rowToEntityConverter;

    @Override
    /**
     * Read excel file which created by jira and write it to ad DB.
     *
     * @param fileName
     *            Excel file name.
     * @param sheetName
     *            Excel sheet name with jira columns.
     * @param startRowIndex
     *            First row index with needed data.
     */
    public void transferAll(String fileName, String sheetName, int startRowIndex) throws IOException, NoShetException {

        try (ExcelFileReader xlsxReader = new XlsxReaderImpl(fileName, sheetName)) {
            addTasks(startRowIndex, xlsxReader);
            addSubTasks(startRowIndex, xlsxReader);
        } catch (IOException exc) {
            logger.error("IOexception." + exc.getMessage());
            throw exc;
        } catch (NoShetException nsexc) {
            logger.error("NoSheetException: " + nsexc.getMessage());
            throw nsexc;
        }

    }

    @Override
    /**
     * Read excel file which created by jira and write it to ad DB.
     *
     * @param fileName
     *            Excel file name.
     * @param sheetName
     *            Excel sheet name with jira columns.
     * @param startRowIndex
     *            First row index with needed data.
     */
    public void transferAllOldExcell(String fileName, String sheetName, int startRowIndex)
            throws IOException, NoShetException {

        try (ExcelFileReader xlsReader = new XlsReaderImpl(fileName, sheetName)) {
            addTasks(startRowIndex, xlsReader);
            addSubTasks(startRowIndex, xlsReader);
        } catch (IOException exc) {
            logger.error("IOexception." + exc.getMessage());
            throw exc;
        } catch (NoShetException nsexc) {
            logger.error("NoSheetException: " + nsexc.getMessage());
            throw nsexc;
        }

    }

    @Override
    public void transferAllCommentsOldExcell(String fileName, String sheetName, int startRowIndex)
            throws IOException, NoShetException {
        try (ExcelFileReader xlsReader = new XlsReaderImpl(fileName, sheetName)) {
            addComments(startRowIndex, xlsReader);
        } catch (IOException exc) {
            logger.error("IOexception." + exc.getMessage());
            throw exc;
        } catch (NoShetException nsexc) {
            logger.error("NoSheetException: " + nsexc.getMessage());
            throw nsexc;
        }
    }

    @Override
    public void transferAllComments(String fileName, String sheetName, int startRowIndex)
            throws IOException, NoShetException {
        try (ExcelFileReader xlsxReader = new XlsxReaderImpl(fileName, sheetName)) {
            addComments(startRowIndex, xlsxReader);
        } catch (IOException exc) {
            logger.error("IOexception." + exc.getMessage());
            throw exc;
        } catch (NoShetException nsexc) {
            logger.error("NoSheetException: " + nsexc.getMessage());
            throw nsexc;
        }
    }

    private void addComments(int startRowIndex, ExcelFileReader reader) throws NoShetException {
        Row curRow;
        int curRowIndex = startRowIndex;

        while ((curRow = reader.getRow(curRowIndex)) != null) {
            rowToEntityConverter.saveAllComments(curRow);
            curRowIndex++;
        }
    }

    private void addTasks(int startRowIndex, ExcelFileReader reader) throws NoShetException {
        Row curRow;
        int curRowIndex = startRowIndex;

        while ((curRow = reader.getRow(curRowIndex)) != null) {
            rowToEntityConverter.addTaskFromRow(curRow);
            curRowIndex++;
        }
    }

    private void addSubTasks(int startRowIndex, ExcelFileReader reader) throws NoShetException {
        Row curRow;
        int subtaskIndex = startRowIndex;

        while ((curRow = reader.getRow(subtaskIndex)) != null) {
            rowToEntityConverter.addTasksConnectFromRow(curRow);
            subtaskIndex++;
        }
    }

}
