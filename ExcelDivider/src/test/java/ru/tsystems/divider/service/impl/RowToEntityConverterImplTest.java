package ru.tsystems.divider.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tsystems.divider.context.TestContext;
import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.excel.ExcelReader;
import ru.tsystems.divider.service.api.functional.RowToEntityConverter;
import ru.tsystems.divider.service.impl.excel.XlsxFileReader;

import java.io.IOException;

public class RowToEntityConverterImplTest {

    private RowToEntityConverter rowToEntityConverter;

    private int rowIndex = 2;
    private ExcelReader xlsxReader;

    @Before
    public void initStreams() throws IOException {
        String xlsxFilePath = "./src/test/resources/Book1.xlsx";
        String testSheetName = "buildTest";
        xlsxReader = new XlsxFileReader(xlsxFilePath, testSheetName);
        rowToEntityConverter = TestContext.getTestContext().getRteConv();
    }

    @After
    public void closeStreams() throws IOException {
        xlsxReader.close();
    }

    @Test
    public void covertRow() throws NoShetException {
//        rowToEntityConverter.addTaskFromRow(xlsxReader.getRow(rowIndex));
//        rowToEntityConverter.addTaskFromRow(xlsxReader.getRow(rowIndex+1));
//        rowToEntityConverter.addTaskFromRow(xlsxReader.getRow(rowIndex+2));
    }
}