package ru.tsystems.divider.service.api;

import ru.tsystems.divider.entity.Task;

public interface TaskService {

    /**
     * Persist into db Task entity.
     *
     * @param task
     *            Task entity.
     */
    void persist(Task task);

    /**
     * Find Task by names.
     *
     * @param key
     *            Task's key.
     * @return Task.
     */
    Task findByKey(String key);

    /**
     * Merge
     * 
     * @param task
     *            Task for merge
     */
    void merge(Task task);
}
