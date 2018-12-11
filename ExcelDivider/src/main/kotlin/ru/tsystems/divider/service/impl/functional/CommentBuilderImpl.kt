package ru.tsystems.divider.service.impl.functional

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import ru.tsystems.divider.service.api.functional.CommentBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.COMMENT_PATTERN_AUTHOR
import ru.tsystems.divider.utils.constants.COMMENT_PATTERN_DATE
import ru.tsystems.divider.utils.constants.COMMENT_PATTERN_ID
import ru.tsystems.divider.utils.constants.FORMAT_READ_FILL
import ru.tsystems.divider.utils.constants.PROPS_FORMAT_READ_COMMENT
import javax.xml.bind.PropertyException

class CommentBuilderImpl(@Autowired messageWorker: MessageWorker) : CommentBuilder {

    private val logger = Logger.getLogger(CommentBuilderImpl::class.java)

    private val NAME_ID = "id"

    private val NAME_AUTHOR = "author"

    private val NAME_DATE = "date"

//    private val NAME_NOTHING = "not"

    private val INDEX_ID: Int

    private val INDEX_AUTHOR: Int

    private val INDEX_DATE: Int

    private val INDEX_COMMENT: Int

    private val FILL: Boolean

    private val authorPattern: String?

    private val idPattern: String?

    private val datePattern: String?

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
            logger.warn("No keywords, return all cell as comment text")
            comment = 0
        } else {
            val formats = format.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in formats.indices) {
                when (formats[i]) {
                    NAME_ID -> id = i
                    NAME_AUTHOR -> author = i
                    NAME_DATE -> date = i
//                    NAME_NOTHING -> { }
                    else -> throw PropertyException("format.read.comment", "Wrong keyword at index: $i")
                }
            }

            comment = formats.size
        }

        INDEX_ID = id
        INDEX_AUTHOR = author
        INDEX_DATE = date
        INDEX_COMMENT = comment


    }

    override fun buildComment(comment: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    private fun buildByPatterns(comment: String): Comment {
//
//    }
}