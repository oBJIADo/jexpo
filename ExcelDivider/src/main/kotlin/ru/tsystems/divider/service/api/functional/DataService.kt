package ru.tsystems.divider.service.api.functional

import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Task

interface DataService {

    //Task
    fun findTaskByKey(key: String): Task?

    fun updateTask(task: Task)
    fun addTask(task: Task)
    fun addTaskRelation(
        subTasks: Set<Task>? = null,
        relationTasks: Set<Task>? = null,
        duplicateTasks: Set<Task>? = null,
        taskKey: String
    )

    //Feature
    fun findFeature(featureTitle: String, natureTitle: String): Feature

    fun findOrCreateFeature(featureTitle: String, natureTitle: String): Feature
    fun addFeature(feature: Feature)

    //Comment
    fun addComment(taskKey: String, comment: Comment)

    fun addComment(comment: Comment)

    //Employee
    fun findOrCreateEmployee(firstName: String, secondName: String): Employee

    fun findOrCreateEmployee(name: String): Employee
    fun addEmployee(employee: Employee)
}