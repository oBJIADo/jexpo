package ru.tsystems.divider.service.impl.functional

import org.apache.log4j.Logger
import org.apache.poi.ss.usermodel.Row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.service.api.excel.ExcelReader
import ru.tsystems.divider.service.api.functional.JiraToDBConverter
import ru.tsystems.divider.service.api.functional.RowToEntityConverter

@Service
class JiraToDBConverterImpl(@Autowired private val rowToEntityConverter: RowToEntityConverter) : JiraToDBConverter {
    companion object {
        private val logger = Logger.getLogger(JiraToDBConverterImpl::class.java)
    }

    /**
     * Read excel file which created by jira and write it to ad DB.
     *
     * @param fileName
     * Excel file name.
     * @param sheetName
     * Excel sheet name with jira columns.
     * @param startRowIndex
     * First row index with needed data.
     */
    override fun transferAll(excelReader: ExcelReader, startRowIndex: Int) {
        addTasks(startRowIndex, excelReader)
        addSubTasks(startRowIndex, excelReader)
    }

    override fun transferAllComments(excelReader: ExcelReader, startRowIndex: Int) {
        addComments(startRowIndex, excelReader)
    }

    private fun addComments(startRowIndex: Int, reader: ExcelReader) {
        var curRow: Row? = null
        var curRowIndex = startRowIndex

        while ({ curRow = reader.getRow(curRowIndex); curRow }() != null) { //THIS CRUTCH SO BRILLIANT!!!!!!
            rowToEntityConverter.saveAllComments(curRow!!)
            curRowIndex++
        }
    }

    private fun addTasks(startRowIndex: Int, reader: ExcelReader) {
        var curRow: Row? = null
        var curRowIndex = startRowIndex

        while ({ curRow = reader.getRow(curRowIndex); curRow }() != null) {
            rowToEntityConverter.addTaskFromRow(curRow!!)
            curRowIndex++
        }
    }

    private fun addSubTasks(startRowIndex: Int, reader: ExcelReader) {
        var curRow: Row? = null
        var subtaskIndex = startRowIndex

        while ({ curRow = reader.getRow(subtaskIndex); curRow }() != null) {
            rowToEntityConverter.addTasksConnectFromRow(curRow!!)
            subtaskIndex++
        }
    }
}