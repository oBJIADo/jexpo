package ru.tsystems.divider.service.impl

import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.tsystems.divider.context.TestContext
import ru.tsystems.divider.exceptions.NoShetException
import ru.tsystems.divider.service.api.excel.ExcelReader
import ru.tsystems.divider.service.api.functional.RowToEntityConverter
import ru.tsystems.divider.service.impl.excel.XlsxFileReader
import java.io.IOException

class RowToEntityConverterImplTest {

    private val rowIndex = 2
    private lateinit var xlsxReader: ExcelReader
    private lateinit var rowToEntityConverter: RowToEntityConverter

    @Before
    fun initStreams() {
        val xlsxFilePath = "./src/test/resources/Book1.xlsx"
        val testSheetName = "buildTest"
        xlsxReader = XlsxFileReader(xlsxFilePath, testSheetName)
        rowToEntityConverter = TestContext.rteConv
    }

    @After
    fun closeStreams() {
        xlsxReader.close()
    }

    @Test
    fun covertRow() { //todo
        //        rowToEntityConverter.addTaskFromRow(xlsxReader.getRow(rowIndex));
        //        rowToEntityConverter.addTaskFromRow(xlsxReader.getRow(rowIndex+1));
        //        rowToEntityConverter.addTaskFromRow(xlsxReader.getRow(rowIndex+2));
    }
}