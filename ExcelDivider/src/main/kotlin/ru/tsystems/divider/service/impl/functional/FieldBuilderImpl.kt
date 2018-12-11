package ru.tsystems.divider.service.impl.functional

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_FORMAT_READ_COMMENT
import javax.xml.bind.PropertyException

@Service
class FieldBuilderImpl(@Autowired messageWorker: MessageWorker) : FieldBuilder {

    companion object {
        private val logger = Logger.getLogger(FieldBuilderImpl::class.java)
    }

    private val NAME_ID = "id"

    private val NAME_AUTHOR = "author"

    private val NAME_DATE = "date"

//    private val NAME_NOTHING = "not"

    private val INDEX_ID: Int

    private val INDEX_AUTHOR: Int

    private val INDEX_DATE: Int

    private val INDEX_COMMENT: Int

    init {
        var id = -1
        var author = -1
        var date = -1
        val comment: Int

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

    /**
     * Divide one string to substrings, delete all useless spaces.
     *
     * @param string
     * String for rebuilding.
     * @param symbol
     * Symbol for dividing the String.
     * @return List with rebuilded strings.
     */
    override fun rebuildString(string: String, symbol: String): Array<String> {
        val result: Array<String> = string.split(symbol.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in result.indices)
            result[i] = result[i].trim { it <= ' ' }
        return result
    }

    /**
     * Check a string for extra pairs (spaces) and remove it.
     *
     * @param target
     * The string which can contains extra spaces.
     * @return String without extra spaces.
     */
    private fun removeExtraSpaces(target: String): String {
        val result = StringBuilder(target.length)
        var curSymbol: Char
        var nextSymbol = target[0]

        for (i in 0 until target.length - 1) {
            curSymbol = nextSymbol
            nextSymbol = target[i + 1]
            if (!(curSymbol == ' ' && nextSymbol == ' ')) {
                result.append(curSymbol)
            }
        }

        result.append(nextSymbol)
        return result.toString()
    }

    /**
     * Rebuild comment. Result of rebuild is a three Strings Which contains: date, author, comment.
     *
     * @param comment
     * String with unrebuilded comment.
     * @return Rebuilded comment.
     */
    override fun rebuildComment(comment: String, divideSymbols: String): Array<String?> {
        var key: String?
        val date: String?
        val author: String?
        var commentText: String

        val indexId = INDEX_ID
        val indexDate = INDEX_DATE
        val indexAuthor = INDEX_AUTHOR

        var index = 0

        try {
            for (i in 0 until INDEX_COMMENT) {
                index = comment.indexOf(divideSymbols, index + 1)
            }

            commentText = comment.substring(index + 1).trim { it <= ' ' }

            val dividedComment =
                comment.substring(0, index).split(divideSymbols.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            key = if (INDEX_ID == -1) null else dividedComment[indexId].trim { it <= ' ' }
            date = if (INDEX_DATE == -1) null else dividedComment[indexDate].trim { it <= ' ' }
            author = if (INDEX_AUTHOR == -1) null else dividedComment[indexAuthor].trim { it <= ' ' }
            return arrayOf<String?>(key, date, author, commentText)
        } catch (indexExc: IndexOutOfBoundsException) {
            index = comment.indexOf(divideSymbols)
            key = if (INDEX_ID == -1) null else comment.substring(0, index)
            commentText = comment.substring(index + 1)
            logger.warn("Wrong comment, returned as text. Key: $key")
            return arrayOf<String?>(key, null, null, commentText)
        }
    }

    /**
     * Rebuild jira fields.
     *
     * @param jiraField
     * String with jira field.
     * @return Rebuilded field.
     */
    override fun rebuildJiraField(jiraField: String): Array<String> {
        return rebuildString(jiraField, ",")
    }

    /**
     * Rebuild jira fields.
     *
     * @param jiraField
     * String with jira field.
     * @return Rebuilded field.
     */
    override fun rebuildJiraField(jiraField: String, symbols: String): Array<String> {
        return rebuildString(jiraField, symbols)
    }

    override fun buildTaskKey(key: String, modificator: String): String {
        if (key.isEmpty()) {
            throw IllegalArgumentException("Key cannot be empty") //todo
        }
        val numericPart = getNumPart(key)

        return modificator + numericPart
    }

    private fun getNumPart(key: String): String {
        val firstIndex: Int
        val lastIndex: Int
        var i = 0

        while (i < key.length && !Character.isDigit(key[i])) {
            i++
        }

        if (i == key.length) {
            throw IllegalArgumentException("No numeric part in task's key") //todo
        } else {
            firstIndex = i
            i = key.length - 1
        }

        while (i >= 0 && !Character.isDigit(key[i])) {
            i--
        }

        lastIndex = i + 1

        return key.substring(firstIndex, lastIndex)
    }


}