package ru.tsystems.divider.context;

import ru.tsystems.divider.dao.api.TaskDao;
import ru.tsystems.divider.entity.Task;

public class InMemoryTask extends InMemoryDaoGeneral<Task> implements TaskDao, ContextSimmulator<Task> {
    private static InMemoryTask dao;

    protected static InMemoryTask getInMemoryDao() {
        if (dao == null) {
            dao = new InMemoryTask();
        }
        return dao;
    }

    protected InMemoryTask() {
        super();
        reset();
    }

    /**
     * Finding task by key
     *
     * @param key Key
     * @return {@link Task}
     */
    @Override
    public Task getBykey(String key) {
        for (Task task : database) {
            if (key.equals(task.getKeys())) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setValue(String key, Task value) {

    }

    @Override
    public Task getValue(String key) {
        return null;
    }
}
