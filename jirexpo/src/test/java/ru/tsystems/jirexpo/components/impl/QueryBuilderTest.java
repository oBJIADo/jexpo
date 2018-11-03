package ru.tsystems.jirexpo.components.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueryBuilderTest {

    @Test
    public void makeQueryConditionText() {
        int[] testParams = { 1, 2 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Varchar fields\n" + "lower(tsk.summary) like :param or\n"
                          + "lower(tsk.description) like :param \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionNum() {
        int[] testParams = { 9, 11 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Num fields\n" + "tsk.last_viewed like :param or\n" + "tsk.resolved like :param \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionSimple() {
        int[] testParams = { 24, 28, 30 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Id's of another one-field tables\n"
                          + "tsk.priority in (select id from priority as tbl where lower(tbl.PARAM) like :param) or\n"
                          + "tsk.epicColor in (select id from epicColor as tbl where lower(tbl.PARAM) like :param) or\n"
                          + "tsk.fix_priority in (select id from PRIORITY as tbl where lower(tbl.PARAM) like :param) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionEmployees() {
        int[] testParams = { 31, 32, 33 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Employees\n"
                          + "tsk.assignee in (select id from employee as empl where lower(empl.FIRSTNAME) like :param or\n"
                          + "lower(empl.SECONDNAME) like :param) or\n"
                          + "tsk.reporter in (select id from employee as empl where lower(empl.FIRSTNAME) like :param or\n"
                          + "lower(empl.SECONDNAME) like :param) or\n"
                          + "tsk.creater in (select id from employee as empl where lower(empl.FIRSTNAME) like :param or\n"
                          + "lower(empl.SECONDNAME) like :param) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionMTM() {
        int[] testParams = { 37, 38, 42 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Many-to-many links\n" + "tsk.id in (select distinct(mtm.TASK_ID)\n"
                          + "from sub_affects_version as mtm\n"
                          + "where mtm.VERSION_ID in (select id from VERSION as tm where lower(tm.PARAM) like :param)) or\n"
                          + "tsk.id in (select distinct(mtm.TASK_ID)\n" + "from task_fix_version as mtm\n"
                          + "where mtm.VERSION_ID in (select id from VERSION as tm where lower(tm.PARAM) like :param)) \n"
                          + "-- Many-to-many tasks links\n" + "or\n" + "tsk.id in (select distinct(mtm.TASK_ID)\n"
                          + "from SUB_TASK as mtm\n"
                          + "where mtm.SUBTASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like :param)) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionComment() {
        int[] testParams = { 34, 36 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Comments\n" + "tsk.id in (select distinct(com.TASK_ID)\n" + "from COMMENT as com\n"
                          + "where\n" + "com.COMMENT_DATE like :param or\n"
                          + "com.AUTHOR in (select id from employee as emplc where lower(emplc.FIRSTNAME) like :param or\n" +
                "lower(emplc.SECONDNAME) like :param)) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionComment2() {
        int[] testParams = { 34 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Comments\n" + "tsk.id in (select distinct(com.TASK_ID)\n" + "from COMMENT as com\n"
                          + "where\n" + "com.COMMENT_DATE like :param ) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionComment4() {
        int[] testParams = { 35 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Comments\n" + "tsk.id in (select distinct(com.TASK_ID)\n" + "from COMMENT as com\n"
                          + "where\n" + "lower(com.COMMENT) like :param ) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionComment3() {
        int[] testParams = { 36 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Comments\n" + "tsk.id in (select distinct(com.TASK_ID)\n" + "from COMMENT as com\n"
                          + "where\n"
                          + "com.AUTHOR in (select id from employee as emplc where lower(emplc.FIRSTNAME) like :param or\n" +
                "lower(emplc.SECONDNAME) like :param)) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void makeQueryConditionCommentAll() {
        int[] testParams = { 34, 35, 36 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Comments\n" +
                "tsk.id in (select distinct(com.TASK_ID)\n" +
                "from COMMENT as com\n"
                          + "where\n"
                + "com.COMMENT_DATE like :param or\n" +
                "lower(com.COMMENT) like :param or\n"
                          + "com.AUTHOR in (select id from employee as emplc where lower(emplc.FIRSTNAME) like :param or\n" +
                "lower(emplc.SECONDNAME) like :param)) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void bigTest() {
        int[] testParams = { 3, 5, 10, 21, 24, 26, 31, 33, 34, 35, 36, 40, 42 };
        String actual = QueryBuilder.makeQueryCondition(testParams, true);
        String expected = "-- Varchar fields\n" + "lower(tsk.epic_name) like :param or\n"
                          + "lower(tsk.order_number) like :param \n" + "-- Num fields\n" + "or\n"
                          + "tsk.updated like :param or\n" + "tsk.sum_original_estimate like :param \n"
                          + "-- Id's of another one-field tables\n" + "or\n"
                          + "tsk.priority in (select id from priority as tbl where lower(tbl.PARAM) like :param) or\n"
                          + "tsk.keyword in (select id from keyword as tbl where lower(tbl.PARAM) like :param) \n"
                          + "-- Employees\n" + "or\n"
                          + "tsk.assignee in (select id from employee as empl where lower(empl.FIRSTNAME) like :param or\n"
                          + "lower(empl.SECONDNAME) like :param) or\n"
                          + "tsk.creater in (select id from employee as empl where lower(empl.FIRSTNAME) like :param or\n"
                          + "lower(empl.SECONDNAME) like :param) \n" + "-- Comments\n" + "or\n"
                          + "tsk.id in (select distinct(com.TASK_ID)\n" + "from COMMENT as com\n" + "where\n"
                          + "com.COMMENT_DATE like :param or\n" + "lower(com.COMMENT) like :param or\n"
                          + "com.AUTHOR in (select id from employee as emplc where lower(emplc.FIRSTNAME) like :param or\n" +
                "lower(emplc.SECONDNAME) like :param)) \n"
                          + "-- Many-to-many links\n" + "or\n" + "tsk.id in (select distinct(mtm.TASK_ID)\n"
                          + "from task_label as mtm\n"
                          + "where mtm.LABEL_ID in (select id from LABEL as tm where lower(tm.PARAM) like :param)) \n"
                          + "-- Many-to-many tasks links\n" + "or\n" + "tsk.id in (select distinct(mtm.TASK_ID)\n"
                          + "from SUB_TASK as mtm\n"
                          + "where mtm.SUBTASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like :param)) \n";
        assertEquals(expected, actual);
    }

    @Test
    public void nullQuery() {
        int[] array = new int[0];
        String actual = QueryBuilder.makeQueryCondition(array, true);
        assertEquals("", actual);
    }

    @Test
    public void makeFinalNativeQuery() {
        int[] testParams = { 3, 5, 10, 21, 24, 26, 31, 33, 34, 35, 36, 40, 42 };
        String actual = QueryBuilder.makeNativeQuery(testParams, true);
        String expected = "select distinct *\n" + "from task as tsk\n" + "where\n" + "-- Varchar fields\n"
                          + "lower(tsk.epic_name) like :param or\n" + "lower(tsk.order_number) like :param \n"
                          + "-- Num fields\n" + "or\n" + "tsk.updated like :param or\n"
                          + "tsk.sum_original_estimate like :param \n" + "-- Id's of another one-field tables\n"
                          + "or\n"
                          + "tsk.priority in (select id from priority as tbl where lower(tbl.PARAM) like :param) or\n"
                          + "tsk.keyword in (select id from keyword as tbl where lower(tbl.PARAM) like :param) \n"
                          + "-- Employees\n" + "or\n"
                          + "tsk.assignee in (select id from employee as empl where lower(empl.FIRSTNAME) like :param or\n"
                          + "lower(empl.SECONDNAME) like :param) or\n"
                          + "tsk.creater in (select id from employee as empl where lower(empl.FIRSTNAME) like :param or\n"
                          + "lower(empl.SECONDNAME) like :param) \n" + "-- Comments\n" + "or\n"
                          + "tsk.id in (select distinct(com.TASK_ID)\n" + "from COMMENT as com\n" + "where\n"
                          + "com.COMMENT_DATE like :param or\n" + "lower(com.COMMENT) like :param or\n"
                          + "com.AUTHOR in (select id from employee as emplc where lower(emplc.FIRSTNAME) like :param or\n"
                          + "lower(emplc.SECONDNAME) like :param)) \n" + "-- Many-to-many links\n" + "or\n"
                          + "tsk.id in (select distinct(mtm.TASK_ID)\n" + "from task_label as mtm\n"
                          + "where mtm.LABEL_ID in (select id from LABEL as tm where lower(tm.PARAM) like :param)) \n"
                          + "-- Many-to-many tasks links\n" + "or\n" + "tsk.id in (select distinct(mtm.TASK_ID)\n"
                          + "from SUB_TASK as mtm\n"
                          + "where mtm.SUBTASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like :param)) \n"
                          + "order by tsk.ID";
        assertEquals(expected, actual);
    }

    @Test
    public void makeFinalNativeNullQuery() {
        int[] array = new int[0];
        String actual = QueryBuilder.makeNativeQuery(array, true);
        String expected = "select distinct *\n" + "from task as tsk\n" + "order by tsk.ID";
        assertEquals(expected, actual);
    }
}