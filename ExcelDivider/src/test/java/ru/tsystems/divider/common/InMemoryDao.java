package ru.tsystems.divider.common;

import ru.tsystems.divider.dao.api.CommentDao;
import ru.tsystems.divider.dao.api.EmployeeDao;
import ru.tsystems.divider.dao.api.FeatureDao;
import ru.tsystems.divider.dao.api.NatureDao;
import ru.tsystems.divider.dao.api.TaskDao;
import ru.tsystems.divider.entity.Comment;
import ru.tsystems.divider.entity.Employee;
import ru.tsystems.divider.entity.Feature;
import ru.tsystems.divider.entity.Nature;
import ru.tsystems.divider.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDao extends InMemoryDaoGeneral implements CommentDao, EmployeeDao, FeatureDao, NatureDao, TaskDao {
    private List<Comment> comments;
    private List<Employee> employees;
    private List<Feature> features;
    private List<Nature> natures;
    private List<Task> tasks;

    private InMemoryDao() {
        comments = new ArrayList<>();
        employees = new ArrayList<>();
        features = new ArrayList<>();
        natures = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    /**
     * Finding employee who has specified first and second names
     *
     * @param firstName  first name
     * @param secondName second name
     * @return {@link Employee}
     */
    @Override
    public Employee getByNames(String firstName, String secondName) {
        for (Employee employee : employees) {
            if (firstName.equals(employee.getFirstname()) && secondName.equals(employee.getSecondname())) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Get entity by param if it exist, return null if not.
     *
     * @param param  String with param.
     * @param nature
     * @return Entity if it exist either null.
     */
    @Override
    public Feature getByParam(String param, String nature) {
        for (Feature feature : features) {
            if (param.equals(feature.getTitle()) && nature.equals(feature.getNature().getTitle())) {
                return feature;
            }
        }
        return null;
    }

    @Override
    public Nature getByTitle(String title) {
        for (Nature nature : natures) {
            if (title.equals(nature.getTitle())) {
                return nature;
            }
        }
        return null;
    }

    /**
     * Finding task by key
     *
     * @param key Key
     * @return {@link Task}
     */
    @Override
    public Task getBykey(String key) {
        for (Task task : tasks) {
            if (key.equals(task.getKeys())) {
                return task;
            }
        }
        return null;
    }
}
