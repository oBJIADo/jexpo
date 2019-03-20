package ru.tsystems.divider.service.api.functional

import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Task

interface EntityBuilder {

    /**
     * Make a Comment entity and return it
     *
     * @param comment Comment.
     * @return New comment.
     */
    fun buildComments(comment: String): Comment?

    /**
     * Make a Employee entity and return it
     *
     * @param employee String with employee.
     * @return New Employee entity.
     */
    fun buildEmployee(employee: String): Employee?

    /**
     *
     * @param title
     * @param nature
     * @return
     */
    fun buildFeatureSet(title: String, nature: String): Set<Feature>

    /**
     *
     * @param title
     * @param nature
     * @return
     */
    fun buildFeature(title: String, nature: String): Feature?
}