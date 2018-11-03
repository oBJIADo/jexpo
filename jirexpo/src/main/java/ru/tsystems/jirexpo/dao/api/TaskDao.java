package ru.tsystems.jirexpo.dao.api;

import java.util.List;

import ru.tsystems.jirexpo.entity.Task;

public interface TaskDao extends GeneralDao<Task> {

    /**
     * Finding task by key
     * 
     * @param key
     *            Key
     * @return {@link Task}
     */
    public Task getBykey(String key);

    /**
     * Get count of the tasks
     * 
     * @return Count of the tasks
     */
    public int getItemsCount();

    /**
     * Get count of the tasks
     * 
     * @param query
     *            Query for giving task's count
     * @param param
     *            Param for searching
     * @return Count of the tasks
     */
    public int getItemsCount(String query, String param);

    /**
     * Get some Tasks from starting count.
     *
     * @param from
     *            Starting count
     * @param count
     *            Count of tasks
     * @return List with {@link Task}
     */
    public List<Task> getTasks(int from, int count);

    /**
     * Get some Tasks from starting count.
     * 
     * @param from
     *            Starting count
     * @param count
     *            Count of tasks
     * @param param
     *            Param for searching
     * @param query
     *            Native query
     * @return List with {@link Task}
     */
    public List<Task> getTasks(int from, int count, String param, String query);
}
