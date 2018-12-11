package ru.tsystems.divider.utils.impl

import org.apache.poi.ss.usermodel.Cell
import ru.tsystems.divider.exceptions.NoShetException
import ru.tsystems.divider.service.impl.excel.XlsxFileReader
import ru.tsystems.divider.service.impl.excel.XlsxFileWriter
import java.io.IOException
import java.util.*

/*
    todo: Component should remove tags from source only. It shouldn't read file, parsinx xlsx, etc... When class will
    todo: changing, rename this class to new name. Mb should to add properties for that!
 */

class FileCleaner {

    private val REPLACES_TAGS: MutableMap<String, String>

    private val REPLACES_ONE_WORD_TAGS: MutableMap<String, String>

    private val REPLACES: MutableMap<String, String>

    init {
        REPLACES_TAGS = HashMap()

        REPLACES_TAGS["div"] = "\n"
        REPLACES_TAGS["br"] = "\n"

        REPLACES_TAGS["font"] = ""
        REPLACES_TAGS["strong"] = ""

        REPLACES_TAGS["a href"] = ""

        /*
     * This map needed because in comments I were found xml tags like <AH...>, And maybe comments contains xml tags,
     * which can to start with same letters. It's why i searching <a href...> and only <a>,</a>.
     */
        REPLACES_ONE_WORD_TAGS = HashMap()

        REPLACES_ONE_WORD_TAGS["</a>"] = ""
        REPLACES_ONE_WORD_TAGS["<a>"] = ""
        REPLACES_ONE_WORD_TAGS["<p> "] = "\n"
        REPLACES_ONE_WORD_TAGS["</p>"] = ""
        REPLACES_ONE_WORD_TAGS["<b>"] = ""
        REPLACES_ONE_WORD_TAGS["</b>"] = ""

        REPLACES = HashMap()

        REPLACES["&gt;"] = ">"
        REPLACES["&#228;"] = "ä"
        REPLACES["&#132;"] = "\""
        REPLACES["&#29;"] = "|"
        REPLACES["&nbsp;"] = " "
        REPLACES["&lt;"] = "<"
        REPLACES["&quot;"] = "\""
        REPLACES["&amp;"] = "&"
        REPLACES["\t"] = " "
    }

    @Throws(IOException::class, NoShetException::class)
    fun clearFile(readFileName: String, resultFileName: String, sheetName: String) {
        XlsxFileReader(readFileName, sheetName).use { reader ->
            XlsxFileWriter(resultFileName, sheetName).use { writer ->
                var cell: Cell? = null
                var cellIndex = 1
                var cellIndexToWrite = 1
                var value: String
                while ({ cell = reader.getCell(0, cellIndex); cell }() != null) {
                    value = cell!!.stringCellValue //todo
                    println("Read cell: $value")

                    value = formatLine(value)
                    println("Write cell: $value")
                    if (isFirstNum(value, ';')) {
                        writer.setTextCellValue(value, 0, cellIndexToWrite)
                    } else {
                        cellIndexToWrite--
                        writer.appendTextCellValue(value, 0, cellIndexToWrite)
                    }
                    cellIndex++
                    cellIndexToWrite++
                }
                println("Cell reading's file index: " + cellIndex + "\nCell writing's file index: "
                        + cellIndexToWrite)
            }
        }
    }

    private fun isFirstNum(str: String, divideSymbol: Char): Boolean {
        var index = str.indexOf(divideSymbol) - 1
        if (index < 0)
            return false
        while (index >= 0) {
            if (!Character.isDigit(str[index]))
                return false
            index--
        }
        return true
    }

    private fun formatLine(line: String): String {
        var result = line
        result = removeTags(result)
        for (del in REPLACES_ONE_WORD_TAGS.keys)
            result = result.replace(del, REPLACES_ONE_WORD_TAGS[del]!!) //todo
        for (del in REPLACES.keys)
            result = result.replace(del, REPLACES[del]!!)  //todo

        return result
    }

    fun removeTags(line: String): String {
        var result = line
        var indexStart: Int = -1
        var indexEnd: Int

        for (tag in REPLACES_TAGS.keys) {
            while ({ indexStart = searchTags(result, tag); indexStart }() != -1) { //todo
                indexEnd = result.indexOf(">", indexStart)
                if (indexEnd == result.length - 1)
                    result = result.substring(0, indexStart) + REPLACES_TAGS[tag]
                else
                    result = result.substring(0, indexStart) + REPLACES_TAGS[tag] + result.substring(indexEnd + 1)
            }
        }
        return result
    }

    private fun searchTags(str: String, tag: String): Int {
        var resultIndex: Int
        val tagOpenSymbol = "<"
        val tagCloseSymbol = "</"
        resultIndex = str.indexOf(tagOpenSymbol + tag)
        if (resultIndex == -1) {
            resultIndex = str.indexOf(tagCloseSymbol + tag)
        }

        return resultIndex
    }
}