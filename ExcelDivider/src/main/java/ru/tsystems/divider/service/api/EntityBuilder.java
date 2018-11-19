package ru.tsystems.divider.service.api;

import ru.tsystems.divider.entity.Comment;
import ru.tsystems.divider.entity.Employee;
import ru.tsystems.divider.entity.Feature;
import ru.tsystems.divider.entity.Task;

import java.util.Set;

//Think about this
public interface EntityBuilder {

    /**
     * Make a Comment entity and return it
     *
     * @param comment Comment.
     * @return New comment.
     */
    Comment buildComments(String comment);

    /**
     * Make a Employee entity and return it
     *
     * @param employee String with employee.
     * @return New Employee entity.
     */
    Employee buildEmployee(String employee);

    /**
     * Make a component entity and return it
     *
     * @param component component.
     * @return Set with components component entity.
     */
    Set<Feature> buildComponents(String component);

    /**
     * Make a EpicColor entity and return it
     *
     * @param color color.
     * @return New EpicColor entity.
     */
    Feature buildEpicColor(String color);

    /**
     * Make a IssueType entity and return it
     *
     * @param type Issue Type.
     * @return New IssueType entity.
     */
    Feature buildIssueType(String type);

    /**
     * Make a Keyword entity and return it
     *
     * @param keyword keyword.
     * @return New keyword entity.
     */
    Feature buildKeyword(String keyword);

    /**
     * Make a label entity and return it
     *
     * @param label label.
     * @return Set with labels entity.
     */
    Set<Feature> buildLabels(String label);

    /**
     * Make a priority entity and return it
     *
     * @param priority priority.
     * @return New priority entity.
     */
    Feature buildPriority(String priority);

    /**
     * Make a resolution entity and return it
     *
     * @param resolution resolution.
     * @return New resolution entity.
     */
    Feature buildResolution(String resolution);

    /**
     * Make a sprint entity and return it
     *
     * @param sprint sprint.
     * @return New sprint entity.
     */
    Feature buildSprint(String sprint);

    /**
     * Make a status entity and return it
     *
     * @param status assignee.
     * @return New status entity.
     */
    Feature buildStatus(String status);

    /**
     * Make a team entity and return it
     *
     * @param team team.
     * @return New team entity.
     */
    Set<Feature> buildTeams(String team);

    /**
     * Make a version entity and return it
     *
     * @param version version.
     * @return Set with version entities.
     */
    Set<Feature> buildVersions(String version);

    /**
     * Make a subtask set and return it
     *
     * @param subTasks version.
     * @return Set with version entities.
     */
    Set<Task> buildConnectionToAnotherTasks(String subTasks);

    Comment buildCommentsWithTask(String comment);

    //todo: javadocks
    public Feature buildDeliveredVersion(String status);

}
