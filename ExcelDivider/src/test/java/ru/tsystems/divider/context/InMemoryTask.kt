package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.TaskDao
import ru.tsystems.divider.entity.Task

object InMemoryTask : InMemoryDaoGeneral<Task>(), TaskDao, ContextSimmulator<Task> {

    init {
        reset()
    }

    /**
     * Finding task by key
     *
     * @param key Key
     * @return [Task]
     */
    override fun getBykey(key: String): Task? {
        for (task in database) {
            if (key == task.keys) {
                return task
            }
        }
        return null
    }
}