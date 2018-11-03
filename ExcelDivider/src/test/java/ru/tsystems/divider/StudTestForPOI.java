package ru.tsystems.divider;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class StudTestForPOI {

    private String xlsxFilePath = "C:\\Users\\Vtroynik\\Idea\\jExpo\\ExcelDivider\\src\\test\\resources\\Book1.xlsx";
    private String firstSheetName = "First";
    private String secondSheetName = "Second";
    private FileInputStream fileInputStream;
    XSSFWorkbook excelBook;

    @Before
    public void openFileStream()throws IOException{
        fileInputStream = new FileInputStream(xlsxFilePath);
        excelBook = new XSSFWorkbook(fileInputStream);
    }

    @After
    public void closeStreams() throws IOException{
        excelBook.getPackage().close();
        fileInputStream.close();
    }

    private Cell getCell(XSSFWorkbook workbook,String sheetName, int rowNum, int columnNum){
        XSSFSheet sheet = workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNum);
        return row.getCell(columnNum);
    }

    @Test
    public void readFromXlsxViaFileStream() {
        Cell cell = getCell(excelBook, secondSheetName, 1, 2);
        String actual = cell.getStringCellValue();
        assertEquals("seven", actual);
    }

    @Test(expected = IllegalStateException.class)
    public void readFromXlsxNumAsString() {
        Cell cell = getCell(excelBook, firstSheetName, 1, 2);
        cell.getStringCellValue();
    }

    //read null value
    @Test(expected = NullPointerException.class)
    public void readFromXlsxNullValue() {
        Cell cell = getCell(excelBook, secondSheetName, 4, 4);
        cell.getStringCellValue();
    }
    @Test
    public void readFromXlsxNullValueIsNull() {
        Cell cell = getCell(excelBook, secondSheetName, 1, 5);
        assertNull(cell);
    }

    @Test
    public void readNumFromXlsxViaFileStream() {
        Cell cell = getCell(excelBook, firstSheetName, 2, 4);
        double actual = cell.getNumericCellValue();
        assertEquals(6.0, actual, 0.01);
    }

    /*
    * Too long method, but it use more memory...
    */
    @Test
    public void readFromXlsxViaPackage() throws InvalidFormatException, IOException {
        try(OPCPackage opcPackage = OPCPackage.open(xlsxFilePath)) {
            XSSFWorkbook excelBook = new XSSFWorkbook(opcPackage);
            Cell cell = getCell(excelBook, secondSheetName, 1, 2);
            String actual = cell.getStringCellValue();
            assertEquals("seven", actual);
        }
    }

    @Test
    public void stringToDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date actual = formatter.parse("26.10.2017 12:11");
        Cell cell = getCell(excelBook, secondSheetName, 2,0);
        Date expected = cell.getDateCellValue();
        assertEquals(expected, actual);
    }

    @Test
    public void isSpace() {
        assertEquals(' ', '\u0020');
    }
}
