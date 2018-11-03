package ru.tsystems.divider.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.tsystems.divider.entity.*;
import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.EntityBuilder;
import ru.tsystems.divider.service.api.ExcelFileReader;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class EntityBuilderImplTest {
    private ExcelFileReader xlsxReader;
    private int rowIndex = 2;

    @Autowired
    private EntityBuilder builder;

    @Before
    public void initStreams() throws IOException {
        String xlsxFilePath = "C:\\Users\\Vtroynik\\Idea\\jExpo\\ExcelDivider\\src\\test\\resources\\Book1.xlsx";
        String testSheetName = "buildTest";
        xlsxReader = new XlsxReaderImpl(xlsxFilePath, testSheetName);
    }

    @After
    public void closeStreams() throws IOException {
        xlsxReader.close();
    }

    @Test
    public void buildTask() {
    }

    @Test
    public void buildEmployee() throws NoShetException {
        String cellText = getStringCellValue(xlsxReader.getCell(8,rowIndex));
        Employee actual = builder.buildEmployee(cellText);
        Employee expected = new Employee("Bochkareva", "Iuliia");

        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getSecondname(), actual.getSecondname());
    }

    @Test
    public void buildAssigne() throws NoShetException {
        String cellText = getStringCellValue(xlsxReader.getCell(7,rowIndex));
        Employee actual = builder.buildEmployee(cellText);
        Employee expected = new Employee("Domke", "Jorg");

        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getSecondname(), actual.getSecondname());
    }

    @Test
    public void buildEpicColor() throws NoShetException{
        String cellText = getStringCellValue(xlsxReader.getCell(33,rowIndex));
        EpicColor actual = builder.buildEpicColor(cellText);
        assertNull(actual);
    }

    @Test
    public void buildIssueType() throws NoShetException{
        String issueType = getStringCellValue(xlsxReader.getCell(3, rowIndex));
        IssueType actual = builder.buildIssueType(issueType);
        assertEquals("Problem Request", actual.getParam());
    }

    @Test
    public void buildKeyword() throws NoShetException{
        String keyword = getStringCellValue(xlsxReader.getCell(39, rowIndex));
        Keyword actual = builder.buildKeyword(keyword);
        assertEquals("Fachkonzept", actual.getParam());
    }

    @Test
    public void buildPriority() throws NoShetException{
        String priority = getStringCellValue(xlsxReader.getCell(5, rowIndex));
        Priority actual = builder.buildPriority(priority);
        assertEquals("Minor", actual.getParam());
    }

    @Test
    public void buildResolution() throws NoShetException{
        String resolution = getStringCellValue(xlsxReader.getCell(6, rowIndex));
        Resolution actual = builder.buildResolution(resolution);
        assertEquals("Unresolved", actual.getParam());
    }

    @Test
    public void buildSprint() throws NoShetException{
        String sprint = getStringCellValue(xlsxReader.getCell(34, rowIndex));
        Sprint actual = builder.buildSprint(sprint);
        assertNull(actual);
    }

    @Test
    public void buildStatus() throws NoShetException{
        String status = getStringCellValue(xlsxReader.getCell(32, rowIndex));
        Status actual = builder.buildStatus(status);
        assertNull(actual);
    }

    @Test
    public void buildTeam() {

    }

    @Test
    public void buildComments() throws NoShetException{
        String comment = getStringCellValue(xlsxReader.getCell(41, rowIndex));
        Comment actual = builder.buildComments(comment);

        Comment expected = new Comment(
                new Date(117, 10,16,11,7),
                new Employee("Bochkareva", "Iuliia"),
                new String("Hello Krystek, Stefanie,\n\n" +
                        "Could you please tell, this error code will be added in specification?  or there will be another decision?\n\n" +
                        "*I just need to create the test in accordance with specification.\n\n" +
                        "Thanks in advance,\n\n" +
                        "Iuliia")
        );

//        assertEquals(expected, actual);

        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getComment(), actual.getComment());
        assertEquals(expected.getCommentDate(), actual.getCommentDate());
    }

    @Test
    public void buildComponents() throws NoShetException{
        String component = getStringCellValue(xlsxReader.getCell(16, rowIndex));
        Set<Component> actual = builder.buildComponents(component);
        Set<Component> expected = new HashSet<>();
        expected.add(new Component("AD V5.4 Spezifikation"));

        assertEquals(expected, actual);
    }

    @Test
    public void buildLabels() throws NoShetException{
        String label = getStringCellValue(xlsxReader.getCell(29, rowIndex));
        Set<Label> actual = builder.buildLabels(label);

        assertNull(actual);
    }

    @Test
    public void buildVersions() throws NoShetException{
        String version = getStringCellValue(xlsxReader.getCell(14, rowIndex));
        Set<Version> actual = builder.buildVersions(version);
        Set<Version> expected = new HashSet<>();
        expected.add(new Version("V05.400.01"));

        assertEquals(expected, actual);
    }

    private String getStringCellValue(Cell cell){
        if(cell == null)
            return null;
        return cell.getStringCellValue();
    }

    private Date getDateCellValue(Cell cell){
        if(cell == null)
            return null;
        return cell.getDateCellValue();
    }

    private Double getNumericCellValue(Cell cell){
        if(cell == null)
            return null;
        return cell.getNumericCellValue();
    }



}