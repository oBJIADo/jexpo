package ru.tsystems.divider

import ru.tsystems.divider.exceptions.NoShetException
import ru.tsystems.divider.utils.impl.FileCleaner
import java.io.FileNotFoundException
import java.io.IOException

class FileClear {

    fun main(args: Array<String>) {
        try {
            val fileCleaner = FileCleaner()
            fileCleaner.clearFile(args[0], args[1], args[2])
        } catch (fnfExc: FileNotFoundException) {
            println("No file: " + fnfExc.message)
        } catch (ioExc: IOException) {
            println("File problem" + ioExc.message)
        } catch (nsExc: NoShetException) {
            println("Sheet exception: " + nsExc.message)
        }

    }
}