package ru.tsystems.divider.utils.impl

import org.junit.Test
import kotlin.test.assertEquals

class FileCleanerTest {

    @Test
    fun tagsTest() { //todo: link from href should be replaced with link between tags
        val str =
            "<a href=\"https://support.clicksoftware.com/csweb/downloads.nsf/cSearchView/A717CC68320D14724225746D00342E96?OpenDocument&amp;Highlight=PTV%2cDATA%2c17\">https://support.clicksoftware.com/csweb/downloads.nsf/cSearchView/A717CC68320D14724225746D00342E96?OpenDocument&amp;Highlight=PTV,DATA,17).</div>"
        val expected =
            "https://support.clicksoftware.com/csweb/downloads.nsf/cSearchView/A717CC68320D14724225746D00342E96?OpenDocument&amp;Highlight=PTV,DATA,17).\n"
        val result = FileCleaner().removeTags(str)
        assertEquals(expected, result)
    }

}