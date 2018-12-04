package ru.tsystems.divider.service.api.excel

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import ru.tsystems.divider.exceptions.NoShetException
import java.io.IOException

interface ExcelReader : AutoCloseable {

    /**
     * Read row from xlsx file and return Row.
     *
     * @param rowIndex
     * index of a row which should be readed.
     *
     * @return List with rows values.
     */
    fun getRow(rowIndex: Int): Row?

    /**
     * Read cell from xlsx file and return it.
     *
     * @param columnIndex
     * Horizontal index of the cell.
     *
     * @param rowIndex
     * Vertical index of the cell.
     *
     * @return Cell.
     */
    fun getCell(columnIndex: Int, rowIndex: Int): Cell?

    /**
     * Select current xlsx sheet.
     *
     * @param sheetName
     * Sheet name.
     */
    fun setSheet(sheetName: String)

    /**
     * Close all streams
     */
    override fun close()
}
