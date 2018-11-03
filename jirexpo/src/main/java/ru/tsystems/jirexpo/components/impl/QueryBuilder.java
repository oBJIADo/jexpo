package ru.tsystems.jirexpo.components.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * This class convert indexes, which maped on fields, to build a query.
 */
public class QueryBuilder {
    private static final Logger logger = Logger.getLogger(QueryBuilder.class);

    /*@formatter:off */
    private static final String DEFAULT_QUERY = "from task as tsk\n" +
            "where\n" +
            "-- Varchar fields\n" +
            "lower(tsk.keys) like :param or\n" +
            "lower(tsk.summary) like :param or\n" +
            "lower(tsk.description) like :param \n" +
            "-- Num fields\n" +
            "or\n" +
            "tsk.created like :param or\n" +
            "tsk.last_viewed like :param or\n" +
            "tsk.updated like :param \n" +
            "-- Id's of another one-field tables\n" +
            "or\n" +
            "tsk.issue_type in (select id from issueType as tbl where tbl.PARAM like :param) \n" +
            "-- Employees\n" +
            "or\n" +
            "tsk.assignee in (select id from employee as empl where empl.FIRSTNAME like :param or\n" +
            "empl.SECONDNAME like :param) or\n" +
            "tsk.reporter in (select id from employee as empl where empl.FIRSTNAME like :param or\n" +
            "empl.SECONDNAME like :param) or\n" +
            "tsk.creater in (select id from employee as empl where empl.FIRSTNAME like :param or\n" +
            "empl.SECONDNAME like :param) \n" +
            "-- Comments\n" +
            "or\n" +
            "tsk.id in (select distinct(com.TASK_ID)\n" +
            "from COMMENT as com\n" +
            "where\n" +
            "com.COMMENT_DATE like :param or\n" +
            "lower(com.COMMENT) like :param or\n" +
            "com.AUTHOR in (select id from employee as emplc where emplc.FIRSTNAME like :param)) \n" +
            "-- Many-to-many links\n" +
            "or\n" +
            "tsk.id in (select distinct(mtm.TASK_ID)\n" +
            "from sub_affects_version as mtm\n" +
            "where mtm.VERSION_ID in (select id from VERSION as tm where lower(tm.PARAM) like :param)) or\n" +
            "tsk.id in (select distinct(mtm.TASK_ID)\n" +
            "from task_fix_version as mtm\n" +
            "where mtm.VERSION_ID in (select id from VERSION as tm where lower(tm.PARAM) like :param)) or\n" +
            "tsk.id in (select distinct(mtm.TASK_ID)\n" +
            "from SUB_TASK as mtm\n" +
            "where mtm.SUBTASK_ID in (select id from TASK as tbl where lower(tbl.KEYS) like :param)) \n";
    /*@formatter:on */

    /**
     * Contains indexes of the fields for default search.
     */
    public static int[] DEFAULT_INDEXES = { 0, 1, 2, 8, 9, 10, 22, 31, 32, 33, 34, 35, 36, 37, 38, 42, 43, 44 };

    /**
     * Map which contains links between indexes and field's names
     */
    private static final Map<Integer, String> CONVERT_FIELDS = new HashMap<Integer, String>();

    /**
     * Map which contains links between indexes and table's names
     */
    private static final Map<Integer, String> CONVERT_TABLES = new HashMap<Integer, String>();

    /**
     * Num which show last index for a text fields in map
     */
    private static final int TEXT_LAST_INDEX = 7;

    /**
     * Num which show last index for a numeric fields in map
     */
    private static final int NUM_LAST_INDEX = 21;

    /**
     * Num which show last index for a simple fields (fields which linking to a table with one param) in map
     */
    private static final int SIMPLE_LAST_INDEX = 30;

    /**
     * Num which show last index for a employees fields (linking to a empl table) in map
     */
    private static final int EMPLOYEES_LAST_INDEX = 33;

    /**
     * Num which show last index for a comment fields int comment table in map
     */
    private static final int COMMENT_LAST_INDEX = 36;

    /**
     * Another tasks links
     */
    private static final int MTM_SIMPLE_TABLES_LAST_INDEX = 41;

    /**
     * Num which show last index for a many-to-many fields in map
     */
    private static final int MTM_TASKS_LAST_INDEX = 44;

    /**
     * Default values for a Maps
     */
    static {
        // text
        CONVERT_FIELDS.put(0, "keys");
        CONVERT_FIELDS.put(1, "summary");
        CONVERT_FIELDS.put(2, "description");
        CONVERT_FIELDS.put(3, "epic_name");
        CONVERT_FIELDS.put(4, "epic_link");
        CONVERT_FIELDS.put(5, "order_number");
        CONVERT_FIELDS.put(6, "delivered_version");
        CONVERT_FIELDS.put(7, "drc_number");

        // num or dates
        CONVERT_FIELDS.put(8, "created");
        CONVERT_FIELDS.put(9, "last_viewed");
        CONVERT_FIELDS.put(10, "updated");
        CONVERT_FIELDS.put(11, "resolved");
        CONVERT_FIELDS.put(12, "due_date");
        CONVERT_FIELDS.put(13, "original_estimate");
        CONVERT_FIELDS.put(14, "remaining_estimate");
        CONVERT_FIELDS.put(15, "time_spent");
        CONVERT_FIELDS.put(16, "work_ration");
        CONVERT_FIELDS.put(17, "progress");
        CONVERT_FIELDS.put(18, "sum_progress");
        CONVERT_FIELDS.put(19, "sum_time_spant");
        CONVERT_FIELDS.put(20, "sum_remaining_estimate");
        CONVERT_FIELDS.put(21, "sum_original_estimate");

        // One-field (simple) tables
        CONVERT_FIELDS.put(22, "issue_type");
        CONVERT_FIELDS.put(23, "status");
        CONVERT_FIELDS.put(24, "priority");
        CONVERT_FIELDS.put(25, "resolution");
        CONVERT_FIELDS.put(26, "keyword");
        CONVERT_FIELDS.put(27, "epic_status");
        CONVERT_FIELDS.put(28, "epicColor");
        CONVERT_FIELDS.put(29, "sprint");
        CONVERT_FIELDS.put(30, "fix_priority");

        // employees
        CONVERT_FIELDS.put(31, "assignee");
        CONVERT_FIELDS.put(32, "reporter");
        CONVERT_FIELDS.put(33, "creater");

        // Comment
        CONVERT_FIELDS.put(34, "author");
        CONVERT_FIELDS.put(35, "comment_date");
        CONVERT_FIELDS.put(36, "comment");

        // ManyToMany
        CONVERT_FIELDS.put(37, "VERSION");
        CONVERT_FIELDS.put(38, "VERSION");
        CONVERT_FIELDS.put(39, "COMPONENT");
        CONVERT_FIELDS.put(40, "LABEL");
        CONVERT_FIELDS.put(41, "TEAM");

        // MTM_TASKS
        CONVERT_FIELDS.put(42, "SUBTASK");
        CONVERT_FIELDS.put(43, "RELATION_TASK");
        CONVERT_FIELDS.put(44, "DUPLICATE_TASK");

        /* --Tables-- */
        // Simple
        CONVERT_TABLES.put(22, "issueType");
        CONVERT_TABLES.put(23, "status");
        CONVERT_TABLES.put(24, "priority");
        CONVERT_TABLES.put(25, "resolution");
        CONVERT_TABLES.put(26, "keyword");
        CONVERT_TABLES.put(27, "STATUS");
        CONVERT_TABLES.put(28, "epicColor");
        CONVERT_TABLES.put(29, "sprint");
        CONVERT_TABLES.put(30, "PRIORITY");

        CONVERT_TABLES.put(37, "sub_affects_version");
        CONVERT_TABLES.put(38, "task_fix_version");
        CONVERT_TABLES.put(39, "task_component");
        CONVERT_TABLES.put(40, "task_label");
        CONVERT_TABLES.put(41, "task_team");

        // MTM_TASKS
        CONVERT_TABLES.put(42, "SUB_TASK");
        CONVERT_TABLES.put(43, "RELATION_TASK");
        CONVERT_TABLES.put(44, "DUPLICATE_TASK");
    }

    /**
     * Read array with indexes and add to query string conditions with text fields.
     * 
     * @param array
     *            Indexes which keys for a map.
     * @param index
     *            Array index for start reading.
     * @param builder
     *            {@link StringBuilder} for a building query
     * @return Index of element which after last index for reading.
     */
    private static int makeTextParamsPart(int[] array, int index, StringBuilder builder, boolean caseIgnore) {
        if (!(index < array.length) || !(array[index] <= TEXT_LAST_INDEX))
            return index;
        builder.append("-- Varchar fields\n");
        while (index < array.length && array[index] <= TEXT_LAST_INDEX) {
            if (index != 0)
                builder.append("or\n");

            if (caseIgnore)
                builder.append("lower(");

            builder.append("tsk.");
            builder.append(CONVERT_FIELDS.get(array[index]));

            if (caseIgnore)
                builder.append(")");

            builder.append(" like :param ");
            index++;
        }
        builder.append("\n");
        return index;
    }

    /**
     * Read array with indexes and add to query string conditions with num fields.
     *
     * @param array
     *            Indexes which keys for a map.
     * @param index
     *            Array index for start reading.
     * @param builder
     *            {@link StringBuilder} for a building query
     * @return Index of element which after last index for reading.
     */
    private static int makeNumParamsPart(int[] array, int index, StringBuilder builder) {
        if (!(index < array.length) || !(array[index] <= NUM_LAST_INDEX))
            return index;
        builder.append("-- Num fields\n");
        while (index < array.length && array[index] <= NUM_LAST_INDEX) {
            if (index != 0)
                builder.append("or\n");
            builder.append("tsk.");
            builder.append(CONVERT_FIELDS.get(array[index]));
            builder.append(" like :param ");
            index++;
        }
        builder.append("\n");
        return index;
    }

    /**
     * Read array with indexes and add to query string conditions with simple-fields.
     *
     * @param array
     *            Indexes which keys for a map.
     * @param index
     *            Array index for start reading.
     * @param builder
     *            {@link StringBuilder} for a building query
     * @return Index of element which after last index for reading.
     */
    private static int makeSimpleParamsPart(int[] array, int index, StringBuilder builder, boolean caseIgnore) {
        if (!(index < array.length) || !(array[index] <= SIMPLE_LAST_INDEX))
            return index;
        builder.append("-- Id's of another one-field tables\n");
        while (index < array.length && array[index] <= SIMPLE_LAST_INDEX) {
            if (index != 0)
                builder.append("or\n");
            builder.append("tsk.");
            builder.append(CONVERT_FIELDS.get(array[index]));
            builder.append(" in (select id from ");
            builder.append(CONVERT_TABLES.get(array[index]));
            builder.append(" as tbl where ");
            builder.append(caseIgnore ? "lower(tbl.PARAM)" : "tbl.PARAM");
            builder.append(" like :param) ");
            index++;
        }
        builder.append("\n");
        return index;
    }

    /**
     * Read array with indexes and add to query string conditions with employees fields.
     *
     * @param array
     *            Indexes which keys for a map.
     * @param index
     *            Array index for start reading.
     * @param builder
     *            {@link StringBuilder} for a building query
     * @return Index of element which after last index for reading.
     */
    private static int makeEmployeesParamsPart(int[] array, int index, StringBuilder builder, boolean caseIgnore) {
        if (!(index < array.length) || !(array[index] <= EMPLOYEES_LAST_INDEX))
            return index;
        builder.append("-- Employees\n");
        while (index < array.length && array[index] <= EMPLOYEES_LAST_INDEX) {
            if (index != 0)
                builder.append("or\n");
            builder.append("tsk.");
            builder.append(CONVERT_FIELDS.get(array[index]));
            builder.append(" in (select id from employee as empl where ");
            builder.append(caseIgnore ? "lower(empl.FIRSTNAME)" : "empl.FIRSTNAME");
            builder.append(" like :param or\n");
            builder.append(caseIgnore ? "lower(empl.SECONDNAME)" : "empl.SECONDNAME");
            builder.append(" like :param) ");
            index++;
        }
        builder.append("\n");
        return index;
    }

    /**
     * Read array with indexes and add to query string conditions with comment fields.
     *
     * @param array
     *            Indexes which keys for a map.
     * @param index
     *            Array index for start reading.
     * @param builder
     *            {@link StringBuilder} for a building query
     * @return Index of element which after last index for reading.
     */
    private static int makeCommentParamsPart(int[] array, int index, StringBuilder builder, boolean caseIgnore) {
        if (!(index < array.length) || !(array[index] <= COMMENT_LAST_INDEX))
            return index;

        int commentInnerIndex = 0;
        builder.append("-- Comments\n");
        if (index != 0)
            builder.append("or\n");
        builder.append("tsk.id in (select distinct(com.TASK_ID)\n" + "from COMMENT as com\n" + "where\n");
        while (index < array.length && array[index] <= COMMENT_LAST_INDEX) {
            if (commentInnerIndex != 0)
                builder.append("or\n");
            if (array[index] == 34) {
                builder.append("com.COMMENT_DATE like :param ");
            }
            if (array[index] == 35) {
                builder.append(caseIgnore ? "lower(com.COMMENT)" : "com.COMMENT");
                builder.append(" like :param ");
            }
            if (array[index] == 36) {
                builder.append("com.AUTHOR in (select id from employee as emplc where ");
                builder.append(caseIgnore ? "lower(emplc.FIRSTNAME)" : "emplc.FIRSTNAME");
                builder.append(" like :param or\n");
                builder.append(caseIgnore ? "lower(emplc.SECONDNAME)" : "emplc.SECONDNAME");
                builder.append(" like :param)");
            }
            index++;
            commentInnerIndex++;
        }
        builder.append(") ");
        builder.append("\n");
        return index;
    }

    /**
     * Read array with indexes and add to query string conditions with mtm fields.
     *
     * @param array
     *            Indexes which keys for a map.
     * @param index
     *            Array index for start reading.
     * @param builder
     *            {@link StringBuilder} for a building query
     * @return Index of element which after last index for reading.
     */
    private static int makeMTMSimpleParamsPart(int[] array, int index, StringBuilder builder, boolean caseIgnore) {
        if (!(index < array.length) || !(array[index] <= MTM_TASKS_LAST_INDEX))
            return index;
        builder.append("-- Many-to-many links\n");
        while (index < array.length && array[index] <= MTM_SIMPLE_TABLES_LAST_INDEX) {
            if (index != 0)
                builder.append("or\n");
            builder.append("tsk.id in (select distinct(mtm.TASK_ID)\n" + "from ");
            builder.append(CONVERT_TABLES.get(array[index]));
            builder.append(" as mtm\n" + "where mtm.");
            builder.append(CONVERT_FIELDS.get(array[index]));
            builder.append("_ID in (select id from ");
            builder.append(CONVERT_FIELDS.get(array[index]));
            builder.append(" as tm where ");
            builder.append(caseIgnore ? "lower(tm.PARAM)" : "tm.PARAM");
            builder.append(" like :param)) ");
            index++;
        }
        builder.append("\n");
        return index;
    }

    private static int makeMTMTasksParamsPart(int[] array, int index, StringBuilder builder, boolean caseIgnore) {
        if (!(index < array.length) || !(array[index] <= MTM_TASKS_LAST_INDEX))
            return index;
        builder.append("-- Many-to-many tasks links\n");
        while (index < array.length && array[index] <= MTM_TASKS_LAST_INDEX) {
            if (index != 0)
                builder.append("or\n");
            builder.append("tsk.id in (select distinct(mtm.TASK_ID)\n" + "from " + CONVERT_TABLES.get(array[index])
                           + " as mtm\n" + "where mtm." + CONVERT_FIELDS.get(array[index])
                           + "_ID in (select id from TASK as tbl where ");
            builder.append(caseIgnore ? "lower(tbl.KEYS)" : "tbl.KEYS");
            builder.append(" like :param)) ");
            index++;
        }
        builder.append("\n");
        return index;
    }

    /**
     * Build condition of query.
     *
     * @param paramsIndexes
     *            Sorted key for a map.
     * @return String with condition.
     */
    public static String makeQueryCondition(int[] paramsIndexes, boolean caseIgnore) {
        int index = 0;
        StringBuilder builder = new StringBuilder();
        index = makeTextParamsPart(paramsIndexes, index, builder, caseIgnore);
        index = makeNumParamsPart(paramsIndexes, index, builder);
        index = makeSimpleParamsPart(paramsIndexes, index, builder, caseIgnore);
        index = makeEmployeesParamsPart(paramsIndexes, index, builder, caseIgnore);
        index = makeCommentParamsPart(paramsIndexes, index, builder, caseIgnore);
        index = makeMTMSimpleParamsPart(paramsIndexes, index, builder, caseIgnore);
        makeMTMTasksParamsPart(paramsIndexes, index, builder, caseIgnore);
        return builder.toString();
    }

    /**
     * Build main part of a query (without 'order by' and 'select #')
     *
     * @param builder
     *            {@link StringBuilder} with a first part of query.
     * @param params
     *            Sorted key for a map.
     * @return {@link StringBuilder} with a query.
     */
    private static StringBuilder makeQuery(StringBuilder builder, int[] params, boolean caseIgnore) {
        logger.info("Make query with indexes: " + getMassiveForLogs(params));
        String condition;
        builder.append("from task as tsk\n");
        if (!(condition = makeQueryCondition(params, caseIgnore)).isEmpty()) {
            builder.append("where\n");
            builder.append(condition);
        }
        return builder;
    }

    private static String getMassiveForLogs(int[] array) {
        StringBuilder builder = new StringBuilder();
        for (int elem : array) {
            builder.append(elem);
            builder.append("; ");
        }
        return builder.toString();
    }

    /**
     * Make sql query for a searching.
     *
     * @param indexes
     *            Sorted key for a map.
     * @return String with a query
     */
    public static String makeNativeQuery(int[] indexes, boolean caseIgnore) {
        logger.info("Make native query");
        StringBuilder builder = new StringBuilder();
        builder.append("select distinct *\n");
        makeQuery(builder, indexes, caseIgnore);
        builder.append("order by tsk.ID");
        return builder.toString();
    }

    /**
     * Make sql query for a searching which get count of elements.
     *
     * @param indexes
     *            Sorted key for a map.
     * @return String with a query
     */
    public static String makeCountsNativeQuery(int[] indexes, boolean caseIgnore) {
        logger.info("Make native query for searching counts of elements");
        StringBuilder builder = new StringBuilder();
        builder.append("select count(distinct tsk.id)\n");
        makeQuery(builder, indexes, caseIgnore);
        return builder.toString();
    }

    /**
     * Make sql query for default searching.
     *
     * @return String with a query
     */
    public static String makeNativeQuery() {
        logger.info("Make DEFAULT native query");
        return "select distinct *\n" + DEFAULT_QUERY + "order by tsk.ID";
    }

    /**
     * Make sql query for default searching which get count of elements.
     *
     * @return String with a query
     */
    public static String makeCountsNativeQuery() {
        logger.info("Make DEFAULT native query for searching counts of elements");
        return "select count(distinct tsk.id)\n" + DEFAULT_QUERY;
    }

}
