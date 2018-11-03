package ru.tsystems.divider.dao.api;

import ru.tsystems.divider.entity.Task;

public interface TaskDao extends GeneralDao<Task> {

    /**
     * Finding task by key
     *
     * @param key
     *            Key
     * @return {@link Task}
     */
    Task getBykey(String key);
}
