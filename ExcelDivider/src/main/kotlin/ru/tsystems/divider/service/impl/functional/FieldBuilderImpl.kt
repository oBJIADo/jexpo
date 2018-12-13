package ru.tsystems.divider.service.impl.functional

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_FORMAT_READ_COMMENT
import javax.xml.bind.PropertyException

@Service
class FieldBuilderImpl() : FieldBuilder {

    companion object {
        private val logger = Logger.getLogger(FieldBuilderImpl::class.java)
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