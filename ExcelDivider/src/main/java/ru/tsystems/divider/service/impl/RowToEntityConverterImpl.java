package ru.tsystems.divider.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsystems.divider.components.api.MessageWorker;
import ru.tsystems.divider.entity.Comment;
import ru.tsystems.divider.entity.Task;
import ru.tsystems.divider.service.api.*;

/**
 * It should be rebuilded, in my opinion. All file readings move to a ExcelFileReader. There should be only indexes of
 * row and cell. Or not.
 */
@Service
public class RowToEntityConverterImpl implements RowToEntityConverter {
    private static final Logger logger = Logger.getLogger(RowToEntityConverterImpl.class);

    private final Map<String, Integer> FIELDS;

    private final String KEY_MODIFICATOR;

    private static final String SOURCE = "column.index.";

    /**
     * Oh my lord. After add properties and delete hardcode, complete time upped 5-10 times. Wow, just Wow. Maybe some
     * clever people can do smthng better then this solution, which looks like crutch.
     */
    public RowToEntityConverterImpl(@Autowired MessageWorker messageWorker) {

        KEY_MODIFICATOR = messageWorker.getSourceValue("modificator.keys.pre");

        FIELDS = new HashMap<>();
        FIELDS.put("keys", getIndex("keys", messageWorker));
        FIELDS.put("summary", getIndex("summary", messageWorker));
        FIELDS.put("issueType", getIndex("issueType", messageWorker));
        FIELDS.put("status", getIndex("status", messageWorker));
        FIELDS.put("priority", getIndex("priority", messageWorker));
        FIELDS.put("resolution", getIndex("resolution", messageWorker));
        FIELDS.put("assignee", getIndex("assignee", messageWorker));
        FIELDS.put("reporter", getIndex("reporter", messageWorker));
        FIELDS.put("creater", getIndex("creater", messageWorker));
        FIELDS.put("created", getIndex("created", messageWorker));
        FIELDS.put("lastViewed", getIndex("lastViewed", messageWorker));
        FIELDS.put("updated", getIndex("updated", messageWorker));
        FIELDS.put("resolved", getIndex("resolved", messageWorker));
        FIELDS.put("affectsVersion", getIndex("affectsVersion", messageWorker));
        FIELDS.put("fixVersion", getIndex("fixVersion", messageWorker));
        FIELDS.put("components", getIndex("components", messageWorker));
        FIELDS.put("dueDate", getIndex("dueDate", messageWorker));
        FIELDS.put("originalEstimate", getIndex("originalEstimate", messageWorker));
        FIELDS.put("remainingEstimate", getIndex("remainingEstimate", messageWorker));
        FIELDS.put("timeSpent", getIndex("timeSpent", messageWorker));
        FIELDS.put("workRatio", getIndex("workRatio", messageWorker));
        FIELDS.put("description", getIndex("description", messageWorker));
        FIELDS.put("progress", getIndex("progress", messageWorker));
        FIELDS.put("sumProgress", getIndex("sumProgress", messageWorker));
        FIELDS.put("sumTimeSpent", getIndex("sumTimeSpent", messageWorker));
        FIELDS.put("sumRemainingEstimate", getIndex("sumRemainingEstimate", messageWorker));
        FIELDS.put("sumOriginalEstimate", getIndex("sumOriginalEstimate", messageWorker));
        FIELDS.put("labels", getIndex("labels", messageWorker));
        FIELDS.put("teams", getIndex("teams", messageWorker));
        FIELDS.put("epicName", getIndex("epicName", messageWorker));
        FIELDS.put("epicStatus", getIndex("epicStatus", messageWorker));
        FIELDS.put("epicColor", getIndex("epicColor", messageWorker));
        FIELDS.put("sprint", getIndex("sprint", messageWorker));
        FIELDS.put("epicLink", getIndex("epicLink", messageWorker));
        FIELDS.put("orderNumber", getIndex("orderNumber", messageWorker));
        FIELDS.put("deliveredVersion", getIndex("deliveredVersion", messageWorker));
        FIELDS.put("drcNumber", getIndex("drcNumber", messageWorker));
        FIELDS.put("keyword", getIndex("keyword", messageWorker));
        FIELDS.put("fixPriority", getIndex("fixPriority", messageWorker));
        FIELDS.put("commentStart", getIndex("commentStart", messageWorker));
        FIELDS.put("subTasks", getIndex("subTasks", messageWorker));
        FIELDS.put("relationTasks", getIndex("relationTasks", messageWorker));
        FIELDS.put("duplicateTasks", getIndex("duplicateTasks", messageWorker));
        FIELDS.put("commentMode.commentStart", getIndex("commentMode.commentStart", messageWorker));

    }

    @Autowired
    private EntityBuilder builder;

    @Autowired
    private FieldBuilder fieldBuilder;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    /**
     * Convert row to Entities. Save all entities into DB.
     *
     * @param row
     *            Row which should be converted.
     */
    @Override
    @Transactional
    public void addTaskFromRow(Row row) {
        String key = getStringCellValue(getCell(row, "keys"));
        if (KEY_MODIFICATOR != null)
            key = fieldBuilder.buildTaskKey(key, KEY_MODIFICATOR);
        if (taskService.findByKey(key) == null) {
            this.createNewTask(row);
        }
    }

    private void createNewTask(Row row) {
        Task task = new Task();
        task.setKeys(fieldBuilder.buildTaskKey(getStringCellValue(getCell(row, "keys")), KEY_MODIFICATOR)); // 1
        task.setSummary(getStringCellValue(getCell(row, "summary")));
        task.setIssueType(builder.buildIssueType(getStringCellValue(getCell(row, "issueType"))));
        task.setStatus(builder.buildStatus(getStringCellValue(getCell(row, "status"))));
        task.setPriority(builder.buildPriority(getStringCellValue(getCell(row, "priority"))));// 5
        task.setResolution(builder.buildResolution(getStringCellValue(getCell(row, "resolution"))));
        task.setAssignee(builder.buildEmployee(getStringCellValue(getCell(row, "assignee"))));
        task.setReporter(builder.buildEmployee(getStringCellValue(getCell(row, "reporter"))));
        task.setCreater(builder.buildEmployee(getStringCellValue(getCell(row, "creater"))));
        task.setCreated(getDateCellValue(getCell(row, "created")));// 10
        task.setLastViewed(getDateCellValue(getCell(row, "lastViewed")));// 11
        task.setUpdated(getDateCellValue(getCell(row, "updated")));
        task.setResolved(getDateCellValue(getCell(row, "resolved")));
        task.setAffectsVersions(builder.buildVersions(getStringCellValue(getCell(row, "affectsVersion"))));
        task.setFixVersions(builder.buildVersions(getStringCellValue(getCell(row, "fixVersion"))));// 15
        task.setComponents(builder.buildComponents(getStringCellValue(getCell(row, "components"))));// 16
        task.setDueDate(getDateCellValue(getCell(row, "dueDate")));
        task.setOriginalEstimate(getNumericCellValue(getCell(row, "originalEstimate")));
        task.setRemainingEstimate(getNumericCellValue(getCell(row, "remainingEstimate")));
        task.setTimeSpent(getNumericCellValue(getCell(row, "timeSpent")));// 20
        task.setWorkRation(getPercentCellValue(getCell(row, "workRatio"))); // percent value => need to multiply to 100
        task.setDescription(getStringCellValue(getCell(row, "description")));
        task.setProgress(getPercentCellValue(getCell(row, "progress"))); // percent value => need to multiply to 100
        task.setSumProgress(getPercentCellValue(getCell(row, "sumProgress"))); // percent value => need to multiply to
                                                                               // 100
        task.setSumTimeSpant(getNumericCellValue(getCell(row, "sumTimeSpent")));
        task.setSumRemainingEstimate(getNumericCellValue(getCell(row, "sumRemainingEstimate")));
        task.setSumOriginalEstimate(getNumericCellValue(getCell(row, "sumOriginalEstimate")));
        task.setLabels(builder.buildLabels(getStringCellValue(getCell(row, "labels"))));
        task.setTeams(builder.buildTeams(getStringCellValue(getCell(row, "teams"))));// 30
        task.setEpicName(getStringCellValue(getCell(row, "epicName")));
        task.setEpicStatus(builder.buildStatus(getStringCellValue(getCell(row, "epicStatus"))));
        task.setEpicColor(builder.buildEpicColor(getStringCellValue(getCell(row, "epicColor"))));
        task.setSprint(builder.buildSprint(getStringCellValue(getCell(row, "sprint"))));
        task.setEpicLink(getStringCellValue(getCell(row, "epicLink")));// 35
        task.setOrderNumber(getStringCellValue(getCell(row, "orderNumber")));
        task.setDeliveredVersion(getStringCellValue(getCell(row, "deliveredVersion")));
        task.setDrcNumber(getStringCellValue(getCell(row, "drcNumber")));
        task.setKeyword(builder.buildKeyword(getStringCellValue(getCell(row, "keyword"))));
        task.setFixPriority(builder.buildPriority(getStringCellValue(getCell(row, "fixPriority"))));// 40

        taskService.persist(task);

        saveAllComments(row, task);
    }

    @Override
    @Transactional
    public void addTasksConnectFromRow(Row row) {
        String key = fieldBuilder.buildTaskKey(getStringCellValue(getCell(row, "keys")), KEY_MODIFICATOR);
        Task task = taskService.findByKey(key);

        if (task == null)
            throw new IllegalArgumentException();

        task.setSubTasks(builder.buildConnectionToAnotherTasks(getStringCellValue(getCell(row, "subTasks"))));
        task.setRelationTasks(builder.buildConnectionToAnotherTasks(getStringCellValue(getCell(row, "relationTasks"))));
        task.setDuplicateTasks(builder
                .buildConnectionToAnotherTasks(getStringCellValue(getCell(row, "duplicateTasks"))));
        taskService.merge(task);
    }

    @Override
    public void saveAllComments(Row row, Task task) {
        int currentIndex = FIELDS.get("commentStart");
        String commentString;
        Comment curComment;

        while ((commentString = getStringCellValue(row.getCell(currentIndex))) != null) {
            curComment = builder.buildComments(commentString);
            curComment.setTask(task);
            commentService.persist(curComment);
            currentIndex++;
        }
    }

    @Override
    @Transactional
    public void saveAllComments(Row row) {
        int currentIndex = FIELDS.get("commentMode.commentStart");
        String commentString;
        Comment curComment;

        while ((commentString = getStringCellValue(row.getCell(currentIndex))) != null) {
            curComment = builder.buildCommentsWithTask(commentString);
            if (curComment != null){
                commentService.persist(curComment);
            }
            currentIndex++;
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null)
            return null;

        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            return String.valueOf(cell.getNumericCellValue());
        if (cell.getCellType() == Cell.CELL_TYPE_ERROR)
            return String.valueOf(cell.getErrorCellValue()); // wow
        return cell.getStringCellValue();
    }

    private Date getDateCellValue(Cell cell) {
        if (cell == null)
            return null;
        return cell.getDateCellValue();
    }

    private Integer getNumericCellValue(Cell cell) {
        if (cell == null)
            return null;
        return doubleToInt(cell.getNumericCellValue());
    }

    private Integer getPercentCellValue(Cell cell) {
        if (cell == null)
            return null;
        return doubleToInt(cell.getNumericCellValue()) * 100;
    }

    private int doubleToInt(double value) {
        if (value < 1 && value > 0)
            value *= 100;
        return (int) value;
    }

    private static int getIndex(String fieldName, MessageWorker messageWorker) {
        try {
            String index = messageWorker.getSourceValue(SOURCE, fieldName);
            if (index == null)
                return -1;
            return Integer.valueOf(index);
        } catch (NumberFormatException nfexc) {
            logger.error("Wrong number: " + fieldName + "; Returned -1");
            return -1;
        }
    }

    private Cell getCell(Row row, String fieldName) {
        int index = FIELDS.get(fieldName);
        return index < 0 ? null : row.getCell(index);
    }
}