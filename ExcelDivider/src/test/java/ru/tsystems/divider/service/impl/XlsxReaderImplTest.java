package ru.tsystems.divider.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tsystems.divider.exceptions.NoShetException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class XlsxReaderImplTest {
    private String xlsxFilePath = "C:\\Users\\Vtroynik\\Idea\\jExpo\\ExcelDivider\\src\\test\\resources\\Book1.xlsx";
    private String firstSheetName = "First";
    private String secondSheetName = "Second";
    private XlsxReaderImpl xlsxReader;

    @Before
    public void initAllParams() throws IOException {
        xlsxReader = new XlsxReaderImpl(xlsxFilePath, secondSheetName);
    }

    @After
    public void closeAllStreams() throws IOException{
        xlsxReader.close();
    }

    @Test
    public void readRow() throws NoShetException {
        List<String> expected = new ArrayList<>();
        expected.add("zero");
        expected.add("one");
        expected.add("two");
        expected.add("three");
        expected.add("four");

        Row row = this.xlsxReader.getRow(0);

        List<String> actual = new ArrayList<>();
        for(Cell cell: row)
            actual.add(cell.getStringCellValue());
        assertEquals(expected, actual);
    }

    @Test
    public void readCell() throws NoShetException {
        String actual = xlsxReader.getCell(3,0).getStringCellValue();
        assertEquals("three", actual );
    }

}