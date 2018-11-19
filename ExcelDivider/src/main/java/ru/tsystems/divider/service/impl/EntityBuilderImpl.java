package ru.tsystems.divider.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsystems.divider.components.api.MessageWorker;
import ru.tsystems.divider.entity.Comment;
import ru.tsystems.divider.entity.Employee;
import ru.tsystems.divider.entity.Feature;
import ru.tsystems.divider.entity.Task;
import ru.tsystems.divider.service.api.EmployeeService;
import ru.tsystems.divider.service.api.EntityBuilder;
import ru.tsystems.divider.service.api.FeatureService;
import ru.tsystems.divider.service.api.FieldBuilder;
import ru.tsystems.divider.service.api.TaskService;
import ru.tsystems.divider.utils.constants.NatureConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class EntityBuilderImpl implements EntityBuilder {
    private static final Logger logger = Logger.getLogger(EntityBuilderImpl.class);

    private FieldBuilder rebuilder;

    private FeatureService featureService;

    private EmployeeService employeeService;

    private TaskService taskService;

    private static final String SYMBOLS_SOURCE = "symbol.divideSymbol.";

    private final String EMPLOYEE_DIVIDER;

    private final String ANOTHER_TASKS_DIVIDER;

    private final String COMMENTS_DIVIDER;

    private final String KEY_MODIFICATOR;

    private final String ANOTHER_DIVIDER;

    private final String FORMAT_DATE;

    public EntityBuilderImpl(@Autowired MessageWorker messageWorker,
                             @Autowired FieldBuilder rebuilder,
                             @Autowired FeatureService featureService,
                             @Autowired EmployeeService employeeService,
                             @Autowired TaskService taskService) {
        this.rebuilder = rebuilder;
        this.featureService = featureService;
        this.employeeService = employeeService;
        this.taskService = taskService;

        KEY_MODIFICATOR = messageWorker.getSourceValue("modificator.keys.pre");

        FORMAT_DATE = messageWorker.getSourceValue("format.read.date") == null ? "dd.MM.yyyy HH:mm"
                : messageWorker.getSourceValue("format.read.date");

        EMPLOYEE_DIVIDER = messageWorker.getSourceValue(SYMBOLS_SOURCE, "employee");
        ANOTHER_TASKS_DIVIDER = messageWorker.getSourceValue(SYMBOLS_SOURCE, "anotherTasks");
        COMMENTS_DIVIDER = messageWorker.getSourceValue(SYMBOLS_SOURCE, "comments");
        ANOTHER_DIVIDER = messageWorker.getSourceValue(SYMBOLS_SOURCE, "another");
    }

    /**
     * Make a Employee entity and return it
     *
     * @param employee String with employee.
     * @return New Employee entity.
     */
    @Override
    public Employee buildEmployee(String employee) {
        if (isEmtyOrNull(employee))
            return null;
        return getOrCreateEmployee(rebuilder.rebuildJiraField(employee, EMPLOYEE_DIVIDER));
    }

    /**
     * Make a EpicColor entity and return it
     *
     * @param color color.
     * @return New EpicColor entity.
     */
    @Override
    public Feature buildEpicColor(String color) {
        return getOrCreateOneParamEntity(color, NatureConstants.EPIC_COLOR);
    }

    /**
     * Make a IssueType entity and return it
     *
     * @param type Issue Type.
     * @return New IssueType entity.
     */
    @Override
    public Feature buildIssueType(String type) {
        return getOrCreateOneParamEntity(type, NatureConstants.ISSUE_TYPE);
    }

    /**
     * Make a Keyword entity and return it
     *
     * @param keyword keyword.
     * @return New keyword entity.
     */
    @Override
    public Feature buildKeyword(String keyword) {
        return getOrCreateOneParamEntity(keyword, NatureConstants.KEYWORD);
    }

    /**
     * Make a priority entity and return it
     *
     * @param priority priority.
     * @return New priority entity.
     */
    @Override
    public Feature buildPriority(String priority) {
        return getOrCreateOneParamEntity(priority, NatureConstants.PRIORITY);
    }

    /**
     * Make a resolution entity and return it
     *
     * @param resolution assignee.
     * @return New resolution entity.
     */
    @Override
    public Feature buildResolution(String resolution) {
        return getOrCreateOneParamEntity(resolution, NatureConstants.RESOLUTION);
    }

    /**
     * Make a sprint entity and return it
     *
     * @param sprint sprint.
     * @return New sprint entity.
     */
    @Override
    public Feature buildSprint(String sprint) {
        return getOrCreateOneParamEntity(sprint, NatureConstants.SPRINT);
    }

    /**
     * Make a status entity and return it
     *
     * @param status assignee.
     * @return New status entity.
     */
    @Override
    public Feature buildStatus(String status) {
        return getOrCreateOneParamEntity(status, NatureConstants.STATUS);
    }

    //todo: javadocs
    @Override
    public Feature buildDeliveredVersion(String status) {
        return getOrCreateOneParamEntity(status, NatureConstants.VERSION);
    }

    /**
     * Make a team entity and return it
     *
     * @param team team.
     * @return New team entity.
     */
    @Override
    public Set<Feature> buildTeams(String team) {
        if (isEmtyOrNull(team))
            return null;

        Set<Feature> components = new HashSet<>();

        String[] params = rebuilder.rebuildJiraField(team, ANOTHER_DIVIDER);
        for (String param : params)
            components.add(buildTeam(param));

        return components;
    }

    /**
     * Make a team entity and return it
     *
     * @param team team.
     * @return New team entity.
     */
    private Feature buildTeam(String team) {
        return getOrCreateOneParamEntity(team, NatureConstants.TEAM);
    }

    /**
     * Make a Comment entity and return it
     *
     * @param comment Comment.
     * @return New comment.
     */
    @Override
    public Comment buildComments(String comment) {
        String[] commentDividingResult = rebuilder.rebuildComment(comment, COMMENTS_DIVIDER);
        String date = commentDividingResult[1];
        String author = commentDividingResult[2];
        String commentText = commentDividingResult[3];

        Date commentBirthDay;
        Employee comentator;

        comentator = this.buildEmployee(author);
        commentBirthDay = this.dateFromString(date);

        return new Comment(null, commentBirthDay, comentator, commentText);
    }

    @Override
    public Comment buildCommentsWithTask(String comment) {

        String[] commentDividingResult = rebuilder.rebuildComment(comment, COMMENTS_DIVIDER);
        if (commentDividingResult == null)
            return null;
        String key = rebuilder.buildTaskKey(commentDividingResult[0], KEY_MODIFICATOR);
        String date = commentDividingResult[1];
        String author = commentDividingResult[2];
        String commentText = commentDividingResult[3];

        Date commentBirthDay;
        Employee comentator;

        comentator = this.buildEmployee(author);
        commentBirthDay = this.dateFromString(date);
        Task task = taskService.findByKey(key);

        return new Comment(task, commentBirthDay, comentator, commentText);
    }

    /**
     * Convert String with special format (dd.MM.yyyy HH:mm) into Date.
     *
     * @param date String with date.
     * @return Date
     */
    private Date dateFromString(String date) {
        try {
            DateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
            return formatter.parse(date);
        } catch (ParseException | NullPointerException dateExc) {
            logger.error("Can't to convert String to Date: " + date);
            return null;
        }
    }

    /**
     * Make a component entity and return it
     *
     * @param component component.
     * @return Set with components component entity.
     */
    @Override
    public Set<Feature> buildComponents(String component) {
        if (isEmtyOrNull(component))
            return null;

        Set<Feature> components = new HashSet<>();

        String[] params = rebuilder.rebuildJiraField(component, ANOTHER_DIVIDER);
        for (String param : params)
            components.add(buildComponent(param));

        return components;
    }

    /**
     * Make a component entity and return it
     *
     * @param component component.
     * @return New component entity.
     */
    private Feature buildComponent(String component) {
        return getOrCreateOneParamEntity(component, NatureConstants.COMPONENT);
    }

    /**
     * Make a label entity and return it
     *
     * @param label label.
     * @return Set with labels entity.
     */
    @Override
    public Set<Feature> buildLabels(String label) {
        if (isEmtyOrNull(label))
            return null;

        Set<Feature> labels = new HashSet<>();

        String[] params = rebuilder.rebuildJiraField(label, ANOTHER_DIVIDER);
        for (String param : params)
            labels.add(buildLabel(param));

        return labels;
    }

    /**
     * Make a label entity and return it
     *
     * @param label label.
     * @return New label entity.
     */
    private Feature buildLabel(String label) {
        return getOrCreateOneParamEntity(label, NatureConstants.LABEL);
    }

    /**
     * Make a version entity and return it
     *
     * @param version version.
     * @return Set with version entities.
     */
    @Override
    public Set<Feature> buildVersions(String version) {
        if (isEmtyOrNull(version))
            return null;
        Set<Feature> labels = new HashSet<>();

        String[] params = rebuilder.rebuildJiraField(version, ANOTHER_DIVIDER);
        for (String param : params)
            labels.add(buildVersion(param));

        return labels;
    }

    /**
     * Make a resolution entity and return it
     *
     * @param version version.
     * @return New version entity.
     */
    private Feature buildVersion(String version) {
        return getOrCreateOneParamEntity(version, NatureConstants.VERSION);
    }

    /**
     * Try to find one param entity. If it doesn't exist, persist new.
     *
     * @param title  Title
     * @param nature Nature constant.
     * @return OneParamEntity
     */
    private Feature getOrCreateOneParamEntity(String title, String nature) {
        if (title == null || title.isEmpty() || nature == null || nature.isEmpty())
            return null;

        Feature feature = featureService.findByParam(title, nature);
        if (feature != null) {
            return feature;
        } else {
            return featureService.createFeatur(title, nature);
        }
    }

    /**
     * Get employee or create new if it not exist.
     *
     * @param names String massive with firstname and secondname.
     * @return Employee.
     */
    private Employee getOrCreateEmployee(String[] names) {
        if (names == null || names.length <= 0) {
            return getUnassignedEmployee();
        } else if (names.length == 1) {
            return findOrPersistEmployee(names[0], names[0]);
        } else {
            return findOrPersistEmployee(names[0], names[1]);
        }
    }

    /**
     * Try to find employee into db. If it not exit, create new employee and persist it into.
     *
     * @param firstName  Employee's firstname.
     * @param secondName Employee's secondname.
     * @return Employee.
     */
    private Employee findOrPersistEmployee(String firstName, String secondName) {
        Employee employee;
        if ((employee = employeeService.getByNames(firstName, secondName)) != null) {
            return employee;
        } else if ((employee = employeeService.getByNames(secondName, firstName)) != null) {
            return employee;
        } else {
            employee = new Employee(firstName, secondName);
            employeeService.persist(employee);
            return employee;
        }
    }

    /**
     * Logging and get null user. Calling if names has null values.
     *
     * @return null
     */
    private Employee getUnassignedEmployee() {
        logger.info("Unassigned user");
        return null;
    }

    /**
     * Make a subtask set and return it
     *
     * @param subTasks version.
     * @return Set with version entities.
     */
    @Override
    public Set<Task> buildConnectionToAnotherTasks(String subTasks) {
        if (isEmtyOrNull(subTasks) || ANOTHER_TASKS_DIVIDER == null)
            return null;
        Set<Task> tasks = new HashSet<>();

        String[] keys = rebuilder.rebuildJiraField(subTasks, ANOTHER_TASKS_DIVIDER);
        for (String key : keys) {
            if (key != null)
                tasks.add(buildSubTask(rebuilder.buildTaskKey(key, KEY_MODIFICATOR)));
        }

        if (tasks.isEmpty())
            return null;

        return tasks;
    }

    private Task buildSubTask(String key) {
        try {
            Task task = taskService.findByKey(key);
            if (task == null)
                throw new IllegalStateException("Illegal task connection with key: " + key);
            return task;
        } catch (IllegalStateException ilStExc) {
            logger.error(ilStExc.getMessage() + "; This task not added to dependencies!!!");
            return null;
        }
    }

    private boolean isEmtyOrNull(String param) {
        return param == null || param.isEmpty();
    }
}
