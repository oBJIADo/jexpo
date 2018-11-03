package ru.tsystems.divider.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.tsystems.divider.components.api.MessageWorker;
import ru.tsystems.divider.entity.*;
import ru.tsystems.divider.service.api.*;

@Service
public class EntityBuilderImpl implements EntityBuilder {
    private static final Logger logger = Logger.getLogger(EntityBuilderImpl.class);

    @Autowired
    private FieldBuilder rebuilder;

    @Autowired
    private OneParamService<OneParamEntity> oneParamService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaskService taskService;

    private static final String SYMBOLS_SOURCE = "symbol.divideSymbol.";

    private final String EMPLOYEE_DIVIDER;

    private final String ANOTHER_TASKS_DIVIDER;

    private final String COMMENTS_DIVIDER;

    private final String KEY_MODIFICATOR;

    private final String ANOTHER_DIVIDER;

    private final String FORMAT_DATE;

    public EntityBuilderImpl(@Autowired MessageWorker messageWorker) {
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
     * @param employee
     *            String with employee.
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
     * @param color
     *            color.
     * @return New EpicColor entity.
     */
    @Override
    public EpicColor buildEpicColor(String color) {
        return (EpicColor) getOrCreateOneParamEntity(new EpicColor(color), EpicColor.class);
    }

    /**
     * Make a IssueType entity and return it
     *
     * @param type
     *            Issue Type.
     * @return New IssueType entity.
     */
    @Override
    public IssueType buildIssueType(String type) {
        return (IssueType) getOrCreateOneParamEntity(new IssueType(type), IssueType.class);
    }

    /**
     * Make a Keyword entity and return it
     *
     * @param keyword
     *            keyword.
     * @return New keyword entity.
     */
    @Override
    public Keyword buildKeyword(String keyword) {
        return (Keyword) getOrCreateOneParamEntity(new Keyword(keyword), Keyword.class);
    }

    /**
     * Make a priority entity and return it
     *
     * @param priority
     *            priority.
     * @return New priority entity.
     */
    @Override
    public Priority buildPriority(String priority) {
        return (Priority) getOrCreateOneParamEntity(new Priority(priority), Priority.class);
    }

    /**
     * Make a resolution entity and return it
     *
     * @param resolution
     *            assignee.
     * @return New resolution entity.
     */
    @Override
    public Resolution buildResolution(String resolution) {
        return (Resolution) getOrCreateOneParamEntity(new Resolution(resolution), Resolution.class);
    }

    /**
     * Make a sprint entity and return it
     *
     * @param sprint
     *            sprint.
     * @return New sprint entity.
     */
    @Override
    public Sprint buildSprint(String sprint) {
        return (Sprint) getOrCreateOneParamEntity(new Sprint(sprint), Sprint.class);
    }

    /**
     * Make a status entity and return it
     *
     * @param status
     *            assignee.
     * @return New status entity.
     */
    @Override
    public Status buildStatus(String status) {
        return (Status) getOrCreateOneParamEntity(new Status(status), Status.class);
    }

    /**
     * Make a team entity and return it
     *
     * @param team
     *            team.
     * @return New team entity.
     */
    @Override
    public Set<Team> buildTeams(String team) {
        if (isEmtyOrNull(team))
            return null;

        Set<Team> components = new HashSet<>();

        String[] params = rebuilder.rebuildJiraField(team, ANOTHER_DIVIDER);
        for (String param : params)
            components.add(buildTeam(param));

        return components;
    }

    /**
     * Make a team entity and return it
     *
     * @param team
     *            team.
     * @return New team entity.
     */
    private Team buildTeam(String team) {
        return (Team) getOrCreateOneParamEntity(new Team(team), Team.class);
    }

    /**
     * Make a Comment entity and return it
     *
     * @param comment
     *            Comment.
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

        return new Comment(commentBirthDay, comentator, commentText);
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
     * @param date
     *            String with date.
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
     * @param component
     *            component.
     * @return Set with components component entity.
     */
    @Override
    public Set<Component> buildComponents(String component) {
        if (isEmtyOrNull(component))
            return null;

        Set<Component> components = new HashSet<>();

        String[] params = rebuilder.rebuildJiraField(component, ANOTHER_DIVIDER);
        for (String param : params)
            components.add(buildComponent(param));

        return components;
    }

    /**
     * Make a component entity and return it
     *
     * @param component
     *            component.
     * @return New component entity.
     */
    private Component buildComponent(String component) {
        return (Component) getOrCreateOneParamEntity(new Component(component), Component.class);
    }

    /**
     * Make a label entity and return it
     *
     * @param label
     *            label.
     * @return Set with labels entity.
     */
    @Override
    public Set<Label> buildLabels(String label) {
        if (isEmtyOrNull(label))
            return null;

        Set<Label> labels = new HashSet<>();

        String[] params = rebuilder.rebuildJiraField(label, ANOTHER_DIVIDER);
        for (String param : params)
            labels.add(buildLabel(param));

        return labels;
    }

    /**
     * Make a label entity and return it
     *
     * @param label
     *            label.
     * @return New label entity.
     */
    private Label buildLabel(String label) {
        return (Label) getOrCreateOneParamEntity(new Label(label), Label.class);
    }

    /**
     * Make a version entity and return it
     *
     * @param version
     *            version.
     * @return Set with version entities.
     */
    @Override
    public Set<Version> buildVersions(String version) {
        if (isEmtyOrNull(version))
            return null;
        Set<Version> labels = new HashSet<>();

        String[] params = rebuilder.rebuildJiraField(version, ANOTHER_DIVIDER);
        for (String param : params)
            labels.add(buildVersion(param));

        return labels;
    }

    /**
     * Make a resolution entity and return it
     *
     * @param version
     *            version.
     * @return New version entity.
     */
    private Version buildVersion(String version) {
        return (Version) getOrCreateOneParamEntity(new Version(version), Version.class);
    }

    /**
     * Try to find one param entity. If it doesn't exist, persist new.
     * 
     * @param uncheked
     *            Entity
     * @param className
     *            Entity's class.
     * @return OneParamEntity
     */
    private OneParamEntity getOrCreateOneParamEntity(OneParamEntity uncheked,
            Class<? extends OneParamEntity> className) {
        if (isEmtyOrNull(uncheked.getParam()))
            return null;

        OneParamEntity ope = oneParamService.findByParam(uncheked.getParam(), className);
        if (ope != null) {
            return ope;
        } else {
            oneParamService.persist(uncheked);
            return uncheked;
        }
    }

    /**
     * Get employee or create new if it not exist.
     * 
     * @param names
     *            String massive with firstname and secondname.
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
     * @param firstName
     *            Employee's firstname.
     * @param secondName
     *            Employee's secondname.
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
     * @param subTasks
     *            version.
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
