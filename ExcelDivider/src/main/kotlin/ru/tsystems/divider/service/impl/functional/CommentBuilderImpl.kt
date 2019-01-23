package ru.tsystems.divider.service.impl.functional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.service.api.functional.CommentBuilder
import ru.tsystems.divider.service.api.functional.DataService
import ru.tsystems.divider.service.api.functional.EmployeeBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.COMMENT_PATTERN_AUTHOR
import ru.tsystems.divider.utils.constants.COMMENT_PATTERN_DATE
import ru.tsystems.divider.utils.constants.COMMENT_PATTERN_ID
import ru.tsystems.divider.utils.constants.FORMAT_READ_FILL
import ru.tsystems.divider.utils.constants.PROPS_FORMAT_READ_COMMENT
import ru.tsystems.divider.utils.constants.PROPS_FORMAT_READ_DATE
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_COMMENTS
import java.text.ParseException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.xml.bind.PropertyException

@Service
class CommentBuilderImpl(
    @Autowired messageWorker: MessageWorker,
    @Autowired val employeeBuilder: EmployeeBuilder,
    @Autowired val dataService: DataService
) : CommentBuilder {

    companion object {
        //private val logger = //logger.getLogger(CommentBuilderImpl::class.java)
    }

    private val NAME_ID = "id"

    private val NAME_AUTHOR = "author"

    private val NAME_DATE = "date"

    private val INDEX_ID: Int

    private val INDEX_AUTHOR: Int

    private val INDEX_DATE: Int

    private val INDEX_COMMENT: Int

    private val FILL: Boolean

    private val authorPattern: String?

    private val idPattern: String?

    private val datePattern: String?
    private val DIVIDER: String
    private val FORMAT_DATE: String

    init {
        var id = -1
        var author = -1
        var date = -1
        val comment: Int

        authorPattern = messageWorker.getSourceValue(COMMENT_PATTERN_ID)
        idPattern = messageWorker.getSourceValue(COMMENT_PATTERN_AUTHOR)
        datePattern = messageWorker.getSourceValue(COMMENT_PATTERN_DATE)

        FILL = messageWorker.getBoolSourceValue(FORMAT_READ_FILL, false)

        val format = messageWorker.getSourceValue(PROPS_FORMAT_READ_COMMENT)
        if (format == null) {
            //logger.warn("No keywords, return all cell as comment text")
            comment = 0
        } else {
            val formats = format.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in formats.indices) {
                when (formats[i]) {
                    NAME_ID -> id = i
                    NAME_AUTHOR -> author = i
                    NAME_DATE -> date = i
                    else -> throw PropertyException("format.read.comment", "Wrong keyword at index: $i")
                }
            }

            comment = formats.size
        }
        DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_COMMENTS)
        FORMAT_DATE = messageWorker.getSourceValue(PROPS_FORMAT_READ_DATE) ?: "d.M.yyyy H:m"

        INDEX_ID = id
        INDEX_AUTHOR = author
        INDEX_DATE = date
        INDEX_COMMENT = comment


    }

    override fun buildComment(comment: String): Comment {
        var keyStr: String? = null
        var dateStr: String? = null
        var authorStr: String? = null

        var index = 0
        for (i in 0 until INDEX_COMMENT) { //todo
            index = comment.indexOf(DIVIDER, index + 1)
        }
        val commentText = comment.substring(index + 1).trim { it <= ' ' }

        try {
            val dividedComment =
                comment.substring(0, index).split(DIVIDER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            keyStr = findPart(dividedComment, INDEX_ID)
            dateStr = findPart(dividedComment, INDEX_DATE)
            authorStr = findPart(dividedComment, INDEX_AUTHOR)
        } catch (indexExc: IndexOutOfBoundsException) {
            //logger.warn("Wrong comment, returned as text.")
        }

        val task = if (keyStr != null) dataService.findTaskByKey(keyStr) else null
        val date = if (dateStr != null) dateFromString(dateStr) else null
        val author = if (authorStr != null) employeeBuilder.buildEmployee(authorStr) else null

        return Comment(task, date, author, commentText)
    }

    fun findPart(str: Array<String>, partIndex: Int): String? {
        return when {
            partIndex == -1 -> null
            partIndex >= str.size -> null
            else -> str[partIndex].trim { it <= ' ' }
        }
    }

    /**
     * Convert String with special format (dd.MM.yyyy HH:mm) into Date.
     *
     * @param date String with date.
     * @return Date
     */
    private fun dateFromString(date: String): LocalDateTime? {
        try {
            val dateTimeFormatt = DateTimeFormatter.ofPattern(FORMAT_DATE)
            return LocalDateTime.parse(date, dateTimeFormatt)
        } catch (dateExc: DateTimeParseException) {
            //logger.warn("Can't to convert String to Date: $date")
            return null
        } catch (dateExc: NullPointerException) {
            //logger.warn("Can't to convert String to Date: $date")
            return null
        }

    }


}