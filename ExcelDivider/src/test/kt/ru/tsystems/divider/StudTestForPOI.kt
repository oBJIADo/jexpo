package ru.tsystems.divider

import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.FileInputStream
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StudTestForPOI {
    private val secondSheetName = "Second"
    private val firstSheetName = "First"
    private val xlsxFilePath = "./src/test/resources/Book1.xlsx"
    private lateinit var fileInputStream: FileInputStream
    private lateinit var excelBook: XSSFWorkbook  //what is late init?

    @Before
    fun openFileStream() {
        fileInputStream = FileInputStream(xlsxFilePath)
        excelBook = XSSFWorkbook(fileInputStream)
    }

    @After
    fun closeStreams() {
        excelBook.getPackage().close()
        fileInputStream.close()
    }

    private fun getCell(workbook: XSSFWorkbook, sheetName: String, rowNum: Int, columnNum: Int): Cell {
        val sheet = workbook.getSheet(sheetName)
        val row = sheet.getRow(rowNum)
        val cell = row.getCell(columnNum)
        if (cell == null) {
            throw NullPointerException()
        } else {
            return cell
        }
    }

    @Test
    fun readFromXlsxViaFileStream() {
        val cell = getCell(excelBook, secondSheetName, 1, 2)
        val actual = cell.stringCellValue
        assertEquals("seven", actual)
    }

    @Test(expected = IllegalStateException::class)
    fun readFromXlsxNumAsString() {
        val cell = getCell(excelBook, firstSheetName, 1, 2)
        cell.stringCellValue
    }

    //read null value
    @Test(expected = NullPointerException::class)
    fun readFromXlsxNullValue() {
        val cell = getCell(excelBook, secondSheetName, 4, 4)
        cell.stringCellValue
    }

    @Test(expected = NullPointerException::class)
    fun readFromXlsxNullValueIsNull() {
        val cell = getCell(excelBook, secondSheetName, 1, 5)
        assertNull(cell)
    }

    @Test
    fun readNumFromXlsxViaFileStream() {
        val cell = getCell(excelBook, firstSheetName, 2, 4)
        val actual = cell.numericCellValue
        assertEquals(6.0, actual)
    }

    /*
    * Too long method, but it use more memory...
    */
    @Test
    fun readFromXlsxViaPackage() {
        OPCPackage.open(xlsxFilePath).use { opcPackage ->
            val excelBook = XSSFWorkbook(opcPackage)
            val cell = getCell(excelBook, secondSheetName, 1, 2)
            val actual = cell.stringCellValue
            assertEquals("seven", actual)
        }
    }

    @Test
    fun stringToDate() {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val actual = formatter.parse("26.10.2017 12:11")
        val cell = getCell(excelBook, secondSheetName, 2, 0)
        val expected = cell.dateCellValue
        assertEquals(expected, actual)
    }

    @Test
    fun isSpace() {
        assertEquals(' ', '\u0020')
    }
}