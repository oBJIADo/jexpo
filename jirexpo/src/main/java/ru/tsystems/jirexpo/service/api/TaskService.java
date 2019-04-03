package ru.tsystems.jirexpo.service.api;

import ru.tsystems.jirexpo.dto.TaskDto;

import java.util.List;

public interface TaskService {

    /**
     * Find Task by names.
     *
     * @param key Task's key.
     * @return Task.
     */
    public TaskDto findByKey(String key);

    /**
     * Get count of pages, where on each pages const count of items.
     *
     * @param itemsOnPage Tasks on page.
     * @return Count of pages.
     */
    public int getPagesCount(int itemsOnPage);

    /**
     * Get all tasks which should be on selected page.
     *
     * @param page        selected page.
     * @param itemsOnPage Count of items on page.
     * @return List with tasks
     */
    public List<TaskDto> getTasks(int page, int itemsOnPage);

    /**
     * Get all tasks which should be on selected page.
     *
     * @param page        selected page.
     * @param itemsOnPage Count of items on page.
     * @param param       Param for searching.
     * @return List with tasks
     */
    public List<TaskDto> getTasks(int page, int itemsOnPage, String param);

    /**
     * Get all tasks which should be on selected page.
     *
     * @param page        selected page.
     * @param itemsOnPage Count of items on page.
     * @param param       Param for searching.
     * @param indexes     Sorted key for a map.
     * @return List with tasks
     * @see ru.tsystems.jirexpo.components.impl.QueryBuilder
     */
    public List<TaskDto> getTasks(int page, int itemsOnPage, String param, String indexes, boolean caseIgnore);

    /**
     * Get count of pages.
     *
     * @param itemsOnPage Tasks on one page
     * @param param       Param for searching
     * @return Count of a pages
     */
    public int getPagesCount(int itemsOnPage, String param);

    /**
     * Get count of pages.
     *
     * @param itemsOnPage Tasks on one page
     * @param param       Param for searching
     * @param indexes     Indexes of a params for searching {@link ru.tsystems.jirexpo.components.impl.QueryBuilder}
     * @return Count of a pages
     */
    public int getPagesCount(int itemsOnPage, String param, String indexes, boolean caseIgnore);
}
