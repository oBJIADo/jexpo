package ru.tsystems.divider.components.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileCleanerTest {

    @Test
    public void tagsTest(){
        String str = "<a href=\"https://support.clicksoftware.com/csweb/downloads.nsf/cSearchView/A717CC68320D14724225746D00342E96?OpenDocument&amp;Highlight=PTV%2cDATA%2c17\">https://support.clicksoftware.com/csweb/downloads.nsf/cSearchView/A717CC68320D14724225746D00342E96?OpenDocument&amp;Highlight=PTV,DATA,17).</div>";
        FileCleaner.removeTags(str);
    }

}