package ru.tsystems.divider.service.impl

import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.tsystems.divider.service.api.excel.ExcelReader
import ru.tsystems.divider.service.impl.excel.XlsxFileReader
import java.util.*
import kotlin.test.assertEquals

class XlsxReaderImplTest {
    private val xlsxFilePath = "./src/test/resources/Book1.xlsx"
    private val firstSheetName = "First"
    private val secondSheetName = "Second"
    private lateinit var xlsxReader: ExcelReader

    @Before
    fun initAllParams() {
        xlsxReader = XlsxFileReader(xlsxFilePath, secondSheetName)
    }

    @After
    fun closeAllStreams() {
        xlsxReader.close()
    }

    @Test
    fun readRow() {
        val expected = ArrayList<String>()
        expected.add("zero")
        expected.add("one")
        expected.add("two")
        expected.add("three")
        expected.add("four")

        val row = this.xlsxReader.getRow(0)

        val actual = ArrayList<String>()
        if (row != null) {
            for (cell in row)
                actual.add(cell.stringCellValue)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun readCell() {
        val actual = xlsxReader.getCell(3, 0)?.stringCellValue
        assertEquals("three", actual)
    }

}