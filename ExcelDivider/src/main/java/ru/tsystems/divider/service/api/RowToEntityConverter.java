package ru.tsystems.divider.service.api;

import org.apache.poi.ss.usermodel.Row;

import ru.tsystems.divider.entity.Task;

public interface RowToEntityConverter {

    /**
     * Convert row to Entities. Save all entities into DB.
     * 
     * @param row
     *            Row which should be converted.
     */
    void addTaskFromRow(Row row);

    /**
     * Lining sub tasks to task.
     * 
     * @param row
     *            Row.
     */
    void addTasksConnectFromRow(Row row);

    void saveAllComments(Row row, Task task);

    void saveAllComments(Row row);

}
