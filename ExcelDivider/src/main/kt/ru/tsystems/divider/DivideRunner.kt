package ru.tsystems.divider

import org.apache.log4j.Logger
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource
import ru.tsystems.divider.exceptions.ExcelFormatException
import ru.tsystems.divider.exceptions.NoShetException
import ru.tsystems.divider.service.api.functional.JiraToDBConverter
import ru.tsystems.divider.service.impl.excel.XlsFileReader
import ru.tsystems.divider.service.impl.excel.XlsxFileReader
import java.io.FileNotFoundException
import java.io.IOException

@SpringBootApplication
@ImportResource("classpath:spring.xml")
class DivideRunner {
    companion object {
        private val logger = Logger.getLogger(DivideRunner::class.java)
    }

    private val EXCEL_OLD_FORMAT = "xls"
    private val EXCEL_FORMAT = "xlsx"

    fun main(args: Array<String>) {
        val fileName: String
        val sheetName: String
        val startRowIndex: Int
        val convertingParam: Int

        try {
            convertingParam = Integer.valueOf(args[0])
            fileName = args[1]
            sheetName = args[2]
            startRowIndex = Integer.valueOf(args[3])

            // Start spring boot app and get Beam from context
            val configurableApplicationContext = SpringApplication.run(DivideRunner::class.java)
            val transfer = configurableApplicationContext.getBean(JiraToDBConverter::class.java)

            if (convertingParam == 1)
                startTasksReader(fileName, sheetName, startRowIndex, transfer)
            else if (convertingParam == 2)
                startCommentReader(fileName, sheetName, startRowIndex, transfer)
            else
                throw IllegalArgumentException("Converting param is wrong: $convertingParam")

        } catch (indexexc: IndexOutOfBoundsException) {
            println(indexexc.message)
            println("Need 4 arguments:\n  1) Read mode.\n  2) File name with path.\n  3) Sheet name in excel file.\n  4) Index of the first readable row.")
            logger.fatal("Arguments error: " + indexexc.message)
        } catch (nsexc: NoShetException) {
            println(nsexc.message)
            println("Wrong sheet name.")
            logger.fatal("Wrong sheet name.")
        } catch (excelExc: ExcelFormatException) {
            println(excelExc.message)
            logger.fatal(excelExc.message)
        } catch (fnfexc: FileNotFoundException) {
            println(fnfexc.message)
            println("Wrong file name")
            logger.fatal("Wrong file name")
        } catch (ioexc: IOException) {
            println(ioexc.message)
            println("Can't to read file.")
            logger.fatal("Can't to read file.")
        }

    }

    @Throws(ExcelFormatException::class, IOException::class, NoShetException::class)
    private fun startTasksReader(fileName: String, sheetName: String, startRowIndex: Int,
                                 converter: JiraToDBConverter) { //todo: Simple this shit
        val fileFormat = getFormat(fileName)
        if (EXCEL_FORMAT.equals(fileFormat, ignoreCase = true)) {
            try {
                XlsxFileReader(fileName, sheetName).use { excelReader -> converter.transferAll(excelReader, startRowIndex) }
            } catch (exc: IOException) {
                logger.error("IOexception." + exc.message)
                throw exc
            } catch (nsexc: NoShetException) {
                logger.error("NoSheetException: " + nsexc.message)
                throw nsexc
            }

        } else if (EXCEL_OLD_FORMAT.equals(fileFormat, ignoreCase = true)) {
            try { //todo
                XlsFileReader(fileName, sheetName).use { excelReader -> converter.transferAll(excelReader, startRowIndex) }
            } catch (exc: IOException) {
                logger.error("IOexception." + exc.message)
                throw exc
            } catch (nsexc: NoShetException) {
                logger.error("NoSheetException: " + nsexc.message)
                throw nsexc
            }

        } else {
            throw ExcelFormatException(fileFormat, fileName)
        }
    }


    @Throws(ExcelFormatException::class, IOException::class, NoShetException::class)
    private fun startCommentReader(fileName: String, sheetName: String, startRowIndex: Int,
                                   converter: JiraToDBConverter) {
        val fileFormat = getFormat(fileName)
        if (EXCEL_FORMAT.equals(fileFormat, ignoreCase = true)) {
            try {
                XlsxFileReader(fileName, sheetName).use { excelReader -> converter.transferAllComments(excelReader, startRowIndex) }
            } catch (exc: IOException) {
                logger.error("IOexception." + exc.message)
                throw exc
            } catch (nsexc: NoShetException) {
                logger.error("NoSheetException: " + nsexc.message)
                throw nsexc
            }

        } else if (EXCEL_OLD_FORMAT.equals(fileFormat, ignoreCase = true)) {
            try {
                XlsFileReader(fileName, sheetName).use { excelReader -> converter.transferAllComments(excelReader, startRowIndex) }
            } catch (exc: IOException) {
                logger.error("IOexception." + exc.message)
                throw exc
            } catch (nsexc: NoShetException) {
                logger.error("NoSheetException: " + nsexc.message)
                throw nsexc
            }

        } else {
            throw ExcelFormatException(fileFormat, fileName)
        }
    }

    private fun getFormat(fileName: String): String {
        val dotIndex = fileName.lastIndexOf('.')
        return fileName.substring(dotIndex + 1)
    }
}