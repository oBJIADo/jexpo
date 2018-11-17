package ru.tsystems.divider.service.api

import org.apache.poi.ss.usermodel.Row
import ru.tsystems.divider.entity.Task

interface RowToEntityConverter {

    /**
     * Convert row to Entities. Save all entities into DB.
     *
     * @param row
     * Row which should be converted.
     */
    fun addTaskFromRow(row: Row)

    /**
     * Lining sub tasks to task.
     *
     * @param row
     * Row.
     */
    fun addTasksConnectFromRow(row: Row)

    fun saveAllComments(row: Row, task: Task)

    fun saveAllComments(row: Row)

}
