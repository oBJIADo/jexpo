package ru.tsystems.divider.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.RowToEntityConverter;
import ru.tsystems.divider.service.api.ExcelFileReader;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class RowToEntityConverterImplTest {

    @Autowired
    private RowToEntityConverter rowToEntityConverter;

    private int rowIndex = 2;
    private ExcelFileReader xlsxReader;

    @Before
    public void initStreams() throws IOException {
        String xlsxFilePath = "./src/test/resources/Book1.xlsx";
        String testSheetName = "buildTest";
        xlsxReader = new XlsxReaderImpl(xlsxFilePath, testSheetName);
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