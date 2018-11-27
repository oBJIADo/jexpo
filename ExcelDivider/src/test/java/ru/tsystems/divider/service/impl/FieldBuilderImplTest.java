package ru.tsystems.divider.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tsystems.divider.context.TestContext;
import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.excel.ExcelReader;
import ru.tsystems.divider.service.api.functional.FieldBuilder;
import ru.tsystems.divider.service.impl.excel.XlsxFileReader;

import javax.xml.bind.PropertyException;
import java.io.IOException;

import static org.junit.Assert.*;

public class FieldBuilderImplTest {
    private String xlsxFilePath = "./src/test/resources/Book1.xlsx";

    private String testSheetName = "RebuildTest";

    private FieldBuilder fieldBuilder;

    private ExcelReader reader;

    @Before
    public void init() throws IOException, PropertyException {
        reader = new XlsxFileReader(xlsxFilePath, testSheetName);
        fieldBuilder = TestContext.getTestContext().getFieldBuilder();
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