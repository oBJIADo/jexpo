package ru.tsystems.divider.service.api.functional

import ru.tsystems.divider.exceptions.NoShetException
import ru.tsystems.divider.service.api.excel.ExcelReader
import java.io.IOException


//todo: deprecated "OldExcel" methods, if it possible
interface JiraToDBConverter {

    /**
     * Read excel file which created by jira and write it to ad DB. For xlsx files only
     *
     * @param fileName
     * Excel file name.
     * @param sheetName
     * Excel sheet name with jira columns.
     * @param startRowIndex
     * First row index with needed data.
     */
    @Throws(IOException::class, NoShetException::class)
    fun transferAll(excelReader: ExcelReader, startRowIndex: Int)

    @Throws(IOException::class, NoShetException::class)
    fun transferAllComments(excelReader: ExcelReader, startRowIndex: Int)
}
