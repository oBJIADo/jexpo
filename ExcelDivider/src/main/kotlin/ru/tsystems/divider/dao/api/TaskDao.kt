package ru.tsystems.divider.dao.api

import ru.tsystems.divider.entity.Task

interface TaskDao : GeneralDao<Task> {

    /**
     * Finding task by key
     *
     * @param key
     * Key
     * @return [Task]
     */
    fun getBykey(key: String): Task?
}
