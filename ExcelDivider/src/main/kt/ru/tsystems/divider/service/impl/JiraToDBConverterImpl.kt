package ru.tsystems.divider.service.impl

import org.apache.log4j.Logger
import org.apache.poi.ss.usermodel.Row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.exceptions.NoShetException
import ru.tsystems.divider.service.api.ExcelFileReader
import ru.tsystems.divider.service.api.JiraToDBConverter
import ru.tsystems.divider.service.api.RowToEntityConverter
import java.io.IOException

@Service
class JiraToDBConverterImpl : JiraToDBConverter {
    private val logger = Logger.getLogger(JiraToDBConverterImpl::class.java)

    @Autowired
    private val rowToEntityConverter: RowToEntityConverter? = null

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
    @Throws(IOException::class, NoShetException::class)
    override fun transferAll(excelReader: ExcelFileReader, startRowIndex: Int) {
                addTasks(startRowIndex, excelReader)
                addSubTasks(startRowIndex, excelReader)
    }

    @Throws(IOException::class, NoShetException::class)
    override fun transferAllComments(excelReader: ExcelFileReader, startRowIndex: Int) {
            addComments(startRowIndex, excelReader)
    }

    @Throws(NoShetException::class)
    private fun addComments(startRowIndex: Int, reader: ExcelFileReader) {//todo: just todo; Remove all !! and do smthng with dat shitty code
        var curRow: Row? = null
        var curRowIndex = startRowIndex

        while ({curRow = reader.getRow(curRowIndex); curRow}() != null) { //THIS CRUTCH SO BRILLIANT!!!!!!
            rowToEntityConverter!!.saveAllComments(curRow!!)
            curRowIndex++
        }
    }

    @Throws(NoShetException::class) //todo: just todo; Remove all !! and do smthng with dat shitty code
    private fun addTasks(startRowIndex: Int, reader: ExcelFileReader) {
        var curRow: Row? = null
        var curRowIndex = startRowIndex

        while ({curRow = reader.getRow(curRowIndex); curRow}() != null) {
            rowToEntityConverter!!.addTaskFromRow(curRow!!)
            curRowIndex++
        }
    }

    @Throws(NoShetException::class)
    private fun addSubTasks(startRowIndex: Int, reader: ExcelFileReader) {//todo: just todo; Remove all !! and do smthng with dat shitty code
        var curRow: Row? = null
        var subtaskIndex = startRowIndex

        while ({curRow = reader.getRow(subtaskIndex); curRow}() != null) {
            rowToEntityConverter!!.addTasksConnectFromRow(curRow!!)
            subtaskIndex++
        }
    }
}