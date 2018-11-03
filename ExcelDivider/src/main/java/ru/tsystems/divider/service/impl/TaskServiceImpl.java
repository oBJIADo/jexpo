package ru.tsystems.divider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsystems.divider.dao.api.TaskDao;
import ru.tsystems.divider.entity.Task;
import ru.tsystems.divider.service.api.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    @Transactional
    public void persist(Task task) {
        taskDao.persist(task);
    }

    @Override
    @Transactional
    public Task findByKey(String key) {
        return taskDao.getBykey(key);
    }

    /**
     * Merge
     *
     * @param task Task for merge
     */
    @Override
    public void merge(Task task) {
        taskDao.merge(task);
    }
}
