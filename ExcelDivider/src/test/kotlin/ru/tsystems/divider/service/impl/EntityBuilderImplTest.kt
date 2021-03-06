package ru.tsystems.divider.service.impl

import org.junit.Before
import org.junit.Test
import ru.tsystems.divider.context.TestContext
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Nature
import ru.tsystems.divider.exceptions.NoShetException
import ru.tsystems.divider.service.api.functional.EntityBuilder
import ru.tsystems.divider.utils.constants.NATURE_COMPONENT
import ru.tsystems.divider.utils.constants.NATURE_DEFAULT
import ru.tsystems.divider.utils.constants.NATURE_EPIC_COLOR
import ru.tsystems.divider.utils.constants.NATURE_ISSUE_TYPE
import ru.tsystems.divider.utils.constants.NATURE_KEYWORD
import ru.tsystems.divider.utils.constants.NATURE_LABEL
import ru.tsystems.divider.utils.constants.NATURE_PRIORITY
import ru.tsystems.divider.utils.constants.NATURE_RESOLUTION
import ru.tsystems.divider.utils.constants.NATURE_SPRINT
import ru.tsystems.divider.utils.constants.NATURE_STATUS
import ru.tsystems.divider.utils.constants.NATURE_VERSION
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EntityBuilderImplTest {
    private lateinit var builder: EntityBuilder
    private val dateTimeFormatt = DateTimeFormatter.ofPattern("d.M.yyyy H:m")

    @Before
    fun initStreams() {
        TestContext.reset()
        builder = TestContext.entityBuilder
    }

    @Test
    fun buildTask() {
        //todo
    }

    @Test
    @Throws(NoShetException::class)
    fun buildEmployee() {
        var actual = builder.buildEmployee("Bochkareva, Iuliia\n")
        val (firstname, secondname) = Employee("Bochkareva", "Iuliia")

        assertEquals(firstname, actual?.firstname)
        assertEquals(secondname, actual?.secondname)

        actual = builder.buildEmployee("Iuliia, Bochkareva\n")
        assertEquals(firstname, actual?.firstname)
        assertEquals(secondname, actual?.secondname)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildAssigne() {
        var actual = builder.buildEmployee("Domke, Jorg\n")
        val (firstname, secondname) = Employee("Domke", "Jorg")

        assertEquals(firstname, actual?.firstname)
        assertEquals(secondname, actual?.secondname)

        actual = builder.buildEmployee("Jorg, Domke\n")
        assertEquals(firstname, actual?.firstname)
        assertEquals(secondname, actual?.secondname)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildEpicColor() {
        val actual = builder.buildFeature("OHMYGOSH", NATURE_EPIC_COLOR)
        val expected = Feature("OHMYGOSH", Nature(NATURE_EPIC_COLOR))
        assertEquals(expected, actual)
    }

    @Test//todo: tests
    @Throws(NoShetException::class)
    fun buildIssueType() {
        val actual = builder.buildFeature("ISSuE", NATURE_ISSUE_TYPE)
        val expected = Feature("ISSuE", Nature(NATURE_ISSUE_TYPE))
        assertEquals(expected, actual)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildKeyword() {
        val actual = builder.buildFeature("Keyword", NATURE_KEYWORD)
        val expected = Feature("Keyword", Nature(NATURE_KEYWORD))
        assertEquals(expected, actual)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildPriority() {
        val actual = builder.buildFeature("Priority", NATURE_PRIORITY)
        val expected = Feature("Priority", Nature(NATURE_PRIORITY))
        assertEquals(expected, actual)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildResolution() {
        val actual = builder.buildFeature("resolution", NATURE_RESOLUTION)
        val expected = Feature("resolution", Nature(NATURE_RESOLUTION))
        assertEquals(expected, actual)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildSprint() {
        val actual = builder.buildFeature("sprint", NATURE_SPRINT)
        val expected = Feature("sprint", Nature(NATURE_SPRINT))
        assertEquals(expected, actual)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildStatus() {
        val actual = builder.buildFeature("status", NATURE_STATUS)
        val expected = Feature("status", Nature(NATURE_STATUS))
        assertEquals(expected, actual)
    }

    @Test
    fun buildTeam() {

    }

    @Test
    @Throws(NoShetException::class)
    fun buildComments() {
        val actual = builder.buildComments(
            "6.1.2017 11:07; Bochkareva, Iuliia;                 Hello Krystek, Stefanie,\n" +
                    "\n" +
                    "Could you please tell, this error code will be added in specification?  or there will be another decision?\n" +
                    "\n" +
                    "*I just need to create the test in accordance with specification.\n" +
                    "\n" +
                    "Thanks in advance,\n" +
                    "\n" +
                    "Iuliia\n" +
                    "        \n"
        )

        val expected = Comment(
            null,
            LocalDateTime.parse("6.1.2017 11:07", dateTimeFormatt),
            Employee("Bochkareva", "Iuliia"),
            "Hello Krystek, Stefanie,\n\n" +
                    "Could you please tell, this error code will be added in specification?  or there will be another decision?\n\n" +
                    "*I just need to create the test in accordance with specification.\n\n" +
                    "Thanks in advance,\n\n" +
                    "Iuliia"
        )


        assertEquals(expected, actual)
    }

    @Test
    fun parseTest(){
        val date: String = "3.9.2011 6:5"
        val dateTimeFormatt = DateTimeFormatter.ofPattern("d.M.yyyy H:m")
        val localDate = LocalDateTime.parse(date, dateTimeFormatt)
        assertEquals(3, localDate.dayOfMonth)
        assertEquals(9, localDate.monthValue)
        assertEquals(2011, localDate.year)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildComponents() {
        val actual = builder.buildFeatureSet("AD V5.4 Spezifikation; COM1; COM2; COM10", NATURE_COMPONENT)
        val expected = HashSet<Feature>()
        expected.add(Feature("COM1", Nature(NATURE_COMPONENT)))
        expected.add(Feature("COM2", Nature(NATURE_COMPONENT)))
        expected.add(Feature("COM10", Nature(NATURE_COMPONENT)))
        expected.add(Feature("AD V5.4 Spezifikation", Nature(NATURE_COMPONENT)))

        assertEquals(expected, actual)
    }

    @Test
    @Throws(NoShetException::class)
    fun buildLabels() {
        val actual = builder.buildFeatureSet("", NATURE_LABEL)
        assertTrue(actual.isEmpty())
    }

    @Test
    @Throws(NoShetException::class)
    fun buildVersions() {
        val actual = builder.buildFeatureSet("V05.400.01; V05.400.02", NATURE_VERSION)
        val expected = HashSet<Feature>()
        expected.add(Feature("V05.400.01", Nature(NATURE_VERSION)))
        expected.add(Feature("V05.400.02", Nature(NATURE_VERSION)))

        assertEquals(expected, actual)
    }

    @Test
    fun emptyEmployee() {
        val employee = builder.buildEmployee("")
        assertNull(employee)
    }

    @Test
    fun emptyComment() {
        val comment = builder.buildComments("")
        assertNull(comment)
    }

    @Test
    fun emptyFeature() {
        val feature = builder.buildFeature("", NATURE_DEFAULT)
        assertNull(feature)
    }

    @Test()
    fun emptyNature() {
        val feature = builder.buildFeature("Test", "")
        assertEquals(Feature("Test", Nature()), feature)
    }

    @Test
    fun emptyFeatureSet() {
        val featureSet = builder.buildFeatureSet("", NATURE_DEFAULT)
        assertEquals(0, featureSet.size.toLong())
    }
}