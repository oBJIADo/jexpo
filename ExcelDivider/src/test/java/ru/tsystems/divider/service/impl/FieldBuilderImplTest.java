package ru.tsystems.divider.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.ExcelFileReader;
import ru.tsystems.divider.service.api.FieldBuilder;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class FieldBuilderImplTest {
    private String xlsxFilePath = "C:\\Users\\Vtroynik\\Idea\\jExpo\\ExcelDivider\\src\\test\\resources\\Book1.xlsx";

    private String testSheetName = "RebuildTest";

    @Autowired
    private FieldBuilder fieldBuilder;

    private ExcelFileReader reader;

    @Before
    public void init() throws IOException {
        reader = new XlsxReaderImpl(xlsxFilePath, testSheetName);
    }

    @After
    public void close() throws Exception {
        reader.close();
    }

    private String[] expectedComment = {
            null,
            "22.11.2017 08:57", "Scholle, Julia",
            "Hi, \n\nthere was a wrong Translation in CS, we Change that, so the new value is \"Adresse\".\n\n"
                                                  + "The issue is fixed with the next update of the user profiles.\n\n"
                                                  + "Regards,\n\n" + "Julia" };

    @Test
    public void rebuildComment() {
        String comment = "22.11.2017 08:57; Scholle, Julia; Hi, \n\n"
                         + "there was a wrong Translation in CS, we Change that, so the new value is \"Adresse\".\n\n"
                         + "The issue is fixed with the next update of the user profiles.\n\n" + "Regards,\n\n"
                         + "Julia\n\n\n\n\n\n\n\n";

        String[] actuals;
        actuals = fieldBuilder.rebuildComment(comment, ";");

        assertArrayEquals(expectedComment, actuals);
    }

    @Test
    public void rebuildCommentFromXlsx() throws NoShetException {
        String cellValue;
        String[] actuals;

        cellValue = reader.getCell(0, 0).getStringCellValue();
        actuals = fieldBuilder.rebuildComment(cellValue, ";");

        assertArrayEquals(expectedComment, actuals);
    }

    private String[] expectedFields = { "AD-10042", "AD-10044", "AD-10148", "AD-10254", "AD-10255", "AD-10256",
            "AD-10257", "AD-10258", "AD-10259", "AD-10260", "AD-10261", "AD-10262", "AD-10292", };

    @Test
    public void rebuildJiraField() {
        String subTasks = "AD-10042, AD-10044, " + "AD-10148, AD-10254, " + "AD-10255, AD-10256, "
                          + "AD-10257, AD-10258, " + "AD-10259, AD-10260, " + "AD-10261, AD-10262, " + "AD-10292";

        String[] actuals;

        actuals = fieldBuilder.rebuildJiraField(subTasks);
        assertArrayEquals(expectedFields, actuals);
    }

    @Test
    public void rebuildJiraFieldXlsx() throws NoShetException {
        String cellValue;
        String[] actuals;

        cellValue = reader.getCell(1, 0).getStringCellValue();
        actuals = fieldBuilder.rebuildJiraField(cellValue);

        assertArrayEquals(expectedFields, actuals);
    }

}