package ru.tsystems.divider.service.impl;

import org.junit.Before;
import org.junit.Test;
import ru.tsystems.divider.context.TestContext;
import ru.tsystems.divider.entity.Comment;
import ru.tsystems.divider.entity.Employee;
import ru.tsystems.divider.entity.Feature;
import ru.tsystems.divider.entity.Nature;
import ru.tsystems.divider.exceptions.NoShetException;
import ru.tsystems.divider.service.api.functional.EntityBuilder;
import ru.tsystems.divider.utils.constants.NatureConstants;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;


public class EntityBuilderImplTest {
    private EntityBuilder builder;

    @Before
    public void initStreams() {
        TestContext testContext = TestContext.getTestContext();
        testContext.reset();
        builder = testContext.getEntityBuilder();
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
        Feature actual = builder.buildFeature("OHMYGOSH", NatureConstants.NATURE_EPIC_COLOR);
        Feature expected = new Feature("OHMYGOSH", new Nature(NatureConstants.NATURE_EPIC_COLOR));
        assertEquals(expected, actual);
    }

    @Test//todo: tests
    public void buildIssueType() throws NoShetException {
        Feature actual = builder.buildFeature("ISSuE", NatureConstants.NATURE_ISSUE_TYPE);
        Feature expected = new Feature("ISSuE", new Nature(NatureConstants.NATURE_ISSUE_TYPE));
        assertEquals(expected, actual);
    }

    @Test
    public void buildKeyword() throws NoShetException {
        Feature actual = builder.buildFeature("Keyword", NatureConstants.NATURE_KEYWORD);
        Feature expected = new Feature("Keyword", new Nature(NatureConstants.NATURE_KEYWORD));
        assertEquals(expected, actual);
    }

    @Test
    public void buildPriority() throws NoShetException {
        Feature actual = builder.buildFeature("Priority", NatureConstants.NATURE_PRIORITY);
        Feature expected = new Feature("Priority", new Nature(NatureConstants.NATURE_PRIORITY));
        assertEquals(expected, actual);
    }

    @Test
    public void buildResolution() throws NoShetException {
        Feature actual = builder.buildFeature("resolution", NatureConstants.NATURE_RESOLUTION);
        Feature expected = new Feature("resolution", new Nature(NatureConstants.NATURE_RESOLUTION));
        assertEquals(expected, actual);
    }

    @Test
    public void buildSprint() throws NoShetException {
        Feature actual = builder.buildFeature("sprint", NatureConstants.NATURE_SPRINT);
        Feature expected = new Feature("sprint", new Nature(NatureConstants.NATURE_SPRINT));
        assertEquals(expected, actual);
    }

    @Test
    public void buildStatus() throws NoShetException {
        Feature actual = builder.buildFeature("status", NatureConstants.NATURE_STATUS);
        Feature expected = new Feature("status", new Nature(NatureConstants.NATURE_STATUS));
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
        Set<Feature> actual = builder.buildFeatureSet("AD V5.4 Spezifikation; COM1; COM2; COM10", NatureConstants.NATURE_COMPONENT);
        Set<Feature> expected = new HashSet<>();
        expected.add(new Feature("COM1", new Nature(NatureConstants.NATURE_COMPONENT)));
        expected.add(new Feature("COM2", new Nature(NatureConstants.NATURE_COMPONENT)));
        expected.add(new Feature("COM10", new Nature(NatureConstants.NATURE_COMPONENT)));
        expected.add(new Feature("AD V5.4 Spezifikation", new Nature(NatureConstants.NATURE_COMPONENT)));

        assertEquals(expected, actual);
    }

    @Test
    public void buildLabels() throws NoShetException {
        Set<Feature> actual = builder.buildFeatureSet("", NatureConstants.NATURE_LABEL);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void buildVersions() throws NoShetException {
        Set<Feature> actual = builder.buildFeatureSet("V05.400.01; V05.400.02", NatureConstants.NATURE_VERSION);
        Set<Feature> expected = new HashSet<>();
        expected.add(new Feature("V05.400.01", new Nature(NatureConstants.NATURE_VERSION)));
        expected.add(new Feature("V05.400.02", new Nature(NatureConstants.NATURE_VERSION)));

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullEmployee(){
        builder.buildEmployee(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComment(){
        builder.buildComments(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullFeature(){
        builder.buildFeature(null, NatureConstants.NATURE_DEFAULT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNature(){
        builder.buildFeature("Test", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullFeatureSet(){
        builder.buildFeatureSet(null, NatureConstants.NATURE_DEFAULT);
    }

    @Test()
    public void emptyEmployee(){
        Employee employee = builder.buildEmployee("");
        assertNull(employee);
    }

    @Test()
    public void emptyComment(){
        Comment comment = builder.buildComments("");
        assertNull(comment);
    }

    @Test()
    public void emptyFeature(){
        Feature feature = builder.buildFeature("", NatureConstants.NATURE_DEFAULT);
        assertNull(feature);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNature(){
        builder.buildFeature("Test", "");
    }

    @Test()
    public void emptyFeatureSet(){
        Set<Feature> featureSet = builder.buildFeatureSet("", NatureConstants.NATURE_DEFAULT);
        assertEquals(0, featureSet.size());
    }
}