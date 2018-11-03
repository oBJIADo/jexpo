package ru.tsystems.divider.service.api;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import ru.tsystems.divider.exceptions.NoShetException;

public interface ExcelFileReader extends AutoCloseable {

    /**
     * Read row from xlsx file and return Row.
     *
     * @param rowIndex
     *            index of a row which should be readed.
     *
     * @return List with rows values.
     */
    Row getRow(int rowIndex) throws NoShetException;

    /**
     * Read cell from xlsx file and return it.
     *
     * @param columnIndex
     *            Horizontal index of the cell.
     *
     * @param rowIndex
     *            Vertical index of the cell.
     *
     * @return Cell.
     */
    Cell getCell(int columnIndex, int rowIndex) throws NoShetException;

    /**
     * Select current xlsx sheet.
     * 
     * @param sheetName
     *            Sheet name.
     */
    void setSheet(String sheetName) throws NoShetException;

    /**
     * Close all streams
     */
    void close() throws IOException;
}
