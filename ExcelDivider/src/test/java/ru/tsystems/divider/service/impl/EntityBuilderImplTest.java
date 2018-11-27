package ru.tsystems.divider.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tsystems.divider.context.InMemoryFeature;
import ru.tsystems.divider.context.InMemoryEmployee;
import ru.tsystems.divider.context.InMemoryNature;
import ru.tsystems.divider.context.InMemoryTask;
import ru.tsystems.divider.context.MessageSimulator;
import ru.tsystems.divider.components.api.MessageWorker;
import ru.tsystems.divider.context.TestContext;
import ru.tsystems.divider.entity.Comment;
import ru.tsystems.divider.entity.Employee;
import ru.tsystems.divider.entity.Feature;
import ru.tsystems.divider.entity.Nature;
import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.EntityBuilder;
import ru.tsystems.divider.service.api.ExcelFileReader;
import ru.tsystems.divider.service.api.FeatureService;
import ru.tsystems.divider.service.api.NatureService;
import ru.tsystems.divider.utils.constants.NatureConstants;

import javax.xml.bind.PropertyException;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class EntityBuilderImplTest {
    private int rowIndex = 2;
    private EntityBuilder builder;

    @Before
    public void initStreams() {
        builder = TestContext.getTestContext().getEntityBuilder();
    }

    @Test
    public void buildTask() {
        //todo
    }

    @Test
    public void buildEmployee() throws NoShetException {
        Employee actual = builder.buildEmployee("Bochkareva, Iuliia\n");
        Employee expected = new Employee("Bochkareva", "Iuliia");

        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getSecondname(), actual.getSecondname());

        actual = builder.buildEmployee("Iuliia, Bochkareva\n");
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getSecondname(), actual.getSecondname());
    }

    @Test
    public void buildAssigne() throws NoShetException {
        Employee actual = builder.buildEmployee("Domke, Jorg\n");
        Employee expected = new Employee("Domke", "Jorg");

        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getSecondname(), actual.getSecondname());

        actual = builder.buildEmployee("Jorg, Domke\n");
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getSecondname(), actual.getSecondname());
    }

    @Test
    public void buildEpicColor() throws NoShetException {
        Feature actual = builder.buildFeature("OHMYGOSH", NatureConstants.EPIC_COLOR);
        Feature expected = new Feature("OHMYGOSH", new Nature(NatureConstants.EPIC_COLOR));
        assertEquals(expected, actual);
    }

    @Test//todo: tests
    public void buildIssueType() throws NoShetException {
        Feature actual = builder.buildFeature("ISSuE", NatureConstants.ISSUE_TYPE);
        Feature expected = new Feature("ISSuE", new Nature(NatureConstants.ISSUE_TYPE));
        assertEquals(expected, actual);
    }

    @Test
    public void buildKeyword() throws NoShetException {
        Feature actual = builder.buildFeature("Keyword", NatureConstants.KEYWORD);
        Feature expected = new Feature("Keyword", new Nature(NatureConstants.KEYWORD));
        assertEquals(expected, actual);
    }

    @Test
    public void buildPriority() throws NoShetException {
        Feature actual = builder.buildFeature("Priority", NatureConstants.PRIORITY);
        Feature expected = new Feature("Priority", new Nature(NatureConstants.PRIORITY));
        assertEquals(expected, actual);
    }

    @Test
    public void buildResolution() throws NoShetException {
        Feature actual = builder.buildFeature("resolution", NatureConstants.RESOLUTION);
        Feature expected = new Feature("resolution", new Nature(NatureConstants.RESOLUTION));
        assertEquals(expected, actual);
    }

    @Test
    public void buildSprint() throws NoShetException {
        Feature actual = builder.buildFeature("sprint", NatureConstants.SPRINT);
        Feature expected = new Feature("sprint", new Nature(NatureConstants.SPRINT));
        assertEquals(expected, actual);
    }

    @Test
    public void buildStatus() throws NoShetException {
        Feature actual = builder.buildFeature("status", NatureConstants.STATUS);
        Feature expected = new Feature("status", new Nature(NatureConstants.STATUS));
        assertEquals(expected, actual);
    }

    @Test
    public void buildTeam() {

    }

    @Test
    public void buildComments() throws NoShetException {
        Comment actual = builder.buildComments("16.11.2017 11:07; Bochkareva, Iuliia;                 Hello Krystek, Stefanie,\n" +
                "\n" +
                "Could you please tell, this error code will be added in specification?  or there will be another decision?\n" +
                "\n" +
                "*I just need to create the test in accordance with specification.\n" +
                "\n" +
                "Thanks in advance,\n" +
                "\n" +
                "Iuliia\n" +
                "        \n");

        Comment expected = new Comment(
                null,
                new Date(117, 10, 16, 11, 7),
                new Employee("Bochkareva", "Iuliia"),
                new String("Hello Krystek, Stefanie,\n\n" +
                        "Could you please tell, this error code will be added in specification?  or there will be another decision?\n\n" +
                        "*I just need to create the test in accordance with specification.\n\n" +
                        "Thanks in advance,\n\n" +
                        "Iuliia")
        );


        assertEquals(expected, actual);
    }

    @Test
    public void buildComponents() throws NoShetException {
        Set<Feature> actual = builder.buildFeatureSet("AD V5.4 Spezifikation; COM1; COM2; COM10", NatureConstants.COMPONENT);
        Set<Feature> expected = new HashSet<>();
        expected.add(new Feature("COM1", new Nature(NatureConstants.COMPONENT)));
        expected.add(new Feature("COM2", new Nature(NatureConstants.COMPONENT)));
        expected.add(new Feature("COM10", new Nature(NatureConstants.COMPONENT)));
        expected.add(new Feature("AD V5.4 Spezifikation", new Nature(NatureConstants.COMPONENT)));

        assertEquals(expected, actual);
    }

    @Test
    public void buildLabels() throws NoShetException {
        Set<Feature> actual = builder.buildFeatureSet("", NatureConstants.LABEL);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void buildVersions() throws NoShetException {
        Set<Feature> actual = builder.buildFeatureSet("V05.400.01; V05.400.02", NatureConstants.VERSION);
        Set<Feature> expected = new HashSet<>();
        expected.add(new Feature("V05.400.01", new Nature(NatureConstants.VERSION)));
        expected.add(new Feature("V05.400.02", new Nature(NatureConstants.VERSION)));

        assertEquals(expected, actual);
    }
}