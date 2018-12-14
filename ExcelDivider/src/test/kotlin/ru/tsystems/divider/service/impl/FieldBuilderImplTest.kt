package ru.tsystems.divider.service.impl

import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import ru.tsystems.divider.context.TestContext
import ru.tsystems.divider.service.api.excel.ExcelReader
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.service.impl.excel.XlsxFileReader

class FieldBuilderImplTest {
    private val xlsxFilePath = "./src/test/resources/Book1.xlsx"

    private val testSheetName = "RebuildTest"

    private lateinit var fieldBuilder: FieldBuilder

    private lateinit var reader: ExcelReader

    private val expectedComment = arrayOf<String?>(
        null,
        "22.11.2017 08:57",
        "Scholle, Julia",
        "Hi, \n\nthere was a wrong Translation in CS, we Change that, so the new value is \"Adresse\".\n\n"
                + "The issue is fixed with the next update of the user profiles.\n\n"
                + "Regards,\n\n" + "Julia"
    )

    private val expectedFields = arrayOf(
        "AD-10042",
        "AD-10044",
        "AD-10148",
        "AD-10254",
        "AD-10255",
        "AD-10256",
        "AD-10257",
        "AD-10258",
        "AD-10259",
        "AD-10260",
        "AD-10261",
        "AD-10262",
        "AD-10292"
    )

    @Before
    fun init() {
        reader = XlsxFileReader(xlsxFilePath, testSheetName)
        fieldBuilder = TestContext.fieldBuilder
    }

    @After
    @Throws(Exception::class)
    fun close() {
        reader.close()
    }

    @Test
    fun rebuildComment() {//todo
//        val comment = ("22.11.2017 08:57; Scho    lle, Julia; Hi, \n\n"
//                + "there was a wrong Translation in CS, we Change that, so the new value is \"Adresse\".\n\n"
//                + "The issue is fixed with the next update of the user profiles.\n\n" + "Regards,\n\n"
//                + "Julia\n\n\n\n\n\n\n\n")
//
//        val actuals: Array<String?>
//        actuals = fieldBuilder.rebuildComment(comment, ";")
//
//        assertArrayEquals(expectedComment, actuals)
    }

    @Test
    fun rebuildCommentFromXlsx() {//todo
//        val cellValue: String
//        val actuals: Array<String?>
//
//        cellValue = reader.getCell(0, 0)?.stringCellValue ?: throw NullPointerException()
//        actuals = fieldBuilder.rebuildComment(cellValue, ";")
//
//        assertArrayEquals(expectedComment, actuals)
    }

    @Test
    fun rebuildJiraField() {
        val subTasks = ("AD-10042, AD-10044, " + "AD-10148, AD-10254, " + "AD-10255, AD-10256, "
                + "AD-10257, AD-10258, " + "AD-10259, AD-10260, " + "AD-10261, AD-10262, " + "AD-10292")

        val actuals: Array<String>

        actuals = fieldBuilder.rebuildString(subTasks, ",")
        assertArrayEquals(expectedFields, actuals)
    }

    @Test
    fun rebuildJiraFieldXlsx() {
        val cellValue: String
        val actuals: Array<String>

        cellValue = reader.getCell(1, 0)?.stringCellValue ?: throw NullPointerException()
        actuals = fieldBuilder.rebuildString(cellValue, ",")

        assertArrayEquals(expectedFields, actuals)
    }

}