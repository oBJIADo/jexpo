package ru.tsystems.divider.context

import ru.tsystems.divider.exceptions.PropertiesException
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_FORMAT_READ_COMMENT
import ru.tsystems.divider.utils.constants.PROPS_FORMAT_READ_DATE
import ru.tsystems.divider.utils.constants.PROPS_MODIFICATOR_KEYS_PRE
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_COMMENTS
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_EMPLOYEE

object MessageSimulator : MessageWorker, ContextSimmulator<String> {

    private lateinit var properties: MutableMap<String, String?>
    private lateinit var intProperties: MutableMap<String, Int?>
    private lateinit var boolProperties: MutableMap<String, Boolean?>

    override fun reset() {
        //Strings
        properties = HashMap()
        properties[PROPS_MODIFICATOR_KEYS_PRE] = "AD-"
        properties[PROPS_FORMAT_READ_DATE] = null
        properties[PROPS_SYMBOLS_SOURCE_EMPLOYEE] = ","
        properties[PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS] = ","
        properties[PROPS_SYMBOLS_SOURCE_COMMENTS] = ";"
        properties[PROPS_SYMBOLS_SOURCE_ANOTHER] = ";"
        properties[PROPS_FORMAT_READ_COMMENT] = "date,author"

        //Ints
        intProperties = HashMap()


        //Bools
        boolProperties = HashMap()
    }

    override fun setValue(key: String, value: String) {
        properties[key] = value
    }

    override fun getValue(key: String): String? {
        return properties[key]
    }

    init {
        reset()
    }

    override fun getObligatorySourceValue(sourceName: String): String {
        return properties[sourceName] ?: throw PropertiesException(sourceName)
    }

    override fun getSourceValue(sourceName: String, default: String): String {
        return properties[sourceName] ?: default
    }

    override fun getSourceValue(sourceName: String): String? {
        return properties[sourceName]
    }

    override fun getIntSourceValue(sourceName: String): Int? {
        return intProperties[sourceName]
    }

    override fun getIntSourceValue(sourceName: String, default: Int): Int {
        return intProperties[sourceName] ?: default
    }

    override fun getBoolSourceValue(sourceName: String): Boolean? {
        return boolProperties[sourceName]
    }

    override fun getBoolSourceValue(sourceName: String, default: Boolean): Boolean {
        return boolProperties[sourceName] ?: default
    }
}