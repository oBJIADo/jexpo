package ru.tsystems.jirexpo.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsystems.jirexpo.components.impl.Converter;
import ru.tsystems.jirexpo.components.impl.QueryBuilder;
import ru.tsystems.jirexpo.dao.api.TaskDao;
import ru.tsystems.jirexpo.dto.TaskDto;
import ru.tsystems.jirexpo.entity.Task;
import ru.tsystems.jirexpo.service.api.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = Logger.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskDao taskDao;

    /**
     * Find Task by names.
     *
     * @param key
     *            Task's key.
     * @return Task.
     */
    @Override
    @Transactional
    public TaskDto findByKey(String key) {
        logger.info("Find the task. Key: " + key);
        try {
            Task task = taskDao.getBykey(key);
            return Converter.convertToDto(task);
        } catch (NullPointerException npexc) {
            logger.warn("No task finded by the key: " + key);
            return null;
        }
    }

    /**
     * Get count of pages, where on each pages const count of items.
     *
     * @param itemsOnPage
     *            Tasks on page.
     * @return Count of pages.
     */
    @Override
    @Transactional
    public int getPagesCount(int itemsOnPage) {
        logger.info("Get pages count. Items on a single page: " + itemsOnPage);
        int itemsCount = taskDao.getItemsCount();
        return (itemsCount % itemsOnPage == 0) ? (itemsCount / itemsOnPage) : (itemsCount / itemsOnPage + 1);
    }

    /**
     * Get count of pages.
     *
     * @param itemsOnPage
     *            Tasks on one page
     * @param param
     *            Param for searching
     * @return Count of a pages
     */
    @Override
    @Transactional
    public int getPagesCount(int itemsOnPage, String param) {
        logger.info("Get pages count. Items on a single page: " + itemsOnPage + "; param: " + param);
        String paramResult = "%" + param.toLowerCase() + "%";
        String nativeQuery = QueryBuilder.makeCountsNativeQuery();
        int itemsCount = taskDao.getItemsCount(nativeQuery, paramResult);
        return getPagesCount(itemsOnPage, itemsCount);
    }

    /**
     * Get count of pages.
     *
     * @param itemsOnPage
     *            Tasks on one page
     * @param param
     *            Param for searching
     * @param indexes
     *            Indexes of a params for searching {@link ru.tsystems.jirexpo.components.impl.QueryBuilder}
     * @return Count of a pages
     */
    @Override
    @Transactional
    public int getPagesCount(int itemsOnPage, String param, String indexes, boolean caseIgnore) {
        logger.info("Get pages count (With indexes). Items on a single page: " + itemsOnPage + "; param: " + param);
        String paramResult = "%" + (caseIgnore ? param.toLowerCase() : param) + "%";
        int[] numIndexes = convertStringArrayToIn(indexes.split(","));
        String nativeQuery = QueryBuilder.makeCountsNativeQuery(numIndexes, caseIgnore);
        int itemsCount = taskDao.getItemsCount(nativeQuery, paramResult);
        return getPagesCount(itemsOnPage, itemsCount);
    }

    private int getPagesCount(int itemsOnPage, int itemsCount) {
        return (itemsCount % itemsOnPage == 0) ? (itemsCount / itemsOnPage) : (itemsCount / itemsOnPage + 1);

    }

    /**
     * Get all tasks which should be on selected page.
     *
     * @param page
     *            selected page.
     * @param itemsOnPage
     *            Count of items on page.
     * @return List with tasks
     */
    @Transactional
    @Override
    public List<TaskDto> getTasks(int page, int itemsOnPage) {
        logger.info("Get tasks on a page: " + page + "; Tasks on a single page: " + itemsOnPage);
        int startPosition = (page - 1) * (itemsOnPage);
        return taskDao.getTasks(startPosition, itemsOnPage).stream().map(Converter::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all tasks which should be on selected page.
     *
     * @param page
     *            selected page.
     * @param itemsOnPage
     *            Count of items on page.
     * @param param
     *            Param for searching.
     * @return List with tasks
     */
    @Transactional
    @Override
    public List<TaskDto> getTasks(int page, int itemsOnPage, String param) {
        logger.info("Get tasks on a page: " + page + "; Tasks on a single page: " + itemsOnPage + "; param: " + param);
        int startPosition = (page - 1) * (itemsOnPage);
        String nativeQuery = QueryBuilder.makeNativeQuery();
        String paramResult = "%" + param.toLowerCase() + "%";
        return taskDao.getTasks(startPosition, itemsOnPage, paramResult, nativeQuery).stream()
                .map(Converter::convertToDto).collect(Collectors.toList());
    }

    /**
     * Get all tasks which should be on selected page.
     *
     * @param page
     *            selected page.
     * @param itemsOnPage
     *            Count of items on page.
     * @param param
     *            Param for searching.
     * @param indexes
     *            Sorted key for a map.
     * @return List with tasks
     * @see ru.tsystems.jirexpo.components.impl.QueryBuilder
     */
    @Transactional
    @Override
    public List<TaskDto> getTasks(int page, int itemsOnPage, String param, String indexes, boolean caseIgnore) {
        logger.info("Get tasks on a page(With indexes): " + page + "; Tasks on a single page: " + itemsOnPage
                    + "; param: " + param);
        int[] numIndexes = convertStringArrayToIn(indexes.split(","));
        String nativeQuery = QueryBuilder.makeNativeQuery(numIndexes, caseIgnore);
        int startPosition = (page - 1) * (itemsOnPage);
        String paramResult = "%" + (caseIgnore ? param.toLowerCase() : param) + "%";

        return taskDao.getTasks(startPosition, itemsOnPage, paramResult, nativeQuery).stream()
                .map(Converter::convertToDto).collect(Collectors.toList());
    }

    private int[] convertStringArrayToIn(String[] array) {
        try {
            return Arrays.stream(array).mapToInt(Integer::valueOf).sorted().toArray();
        } catch (NumberFormatException formatexc) {
            logger.error("Format exception. Cannot convert array: " + formatexc.getMessage());
            return QueryBuilder.DEFAULT_INDEXES;
        }
    }
}
//
