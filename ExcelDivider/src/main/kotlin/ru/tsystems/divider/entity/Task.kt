package ru.tsystems.divider.entity

import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@Entity(name = "Task")
data class Task(
    @Column(name = "keys")
    var keys: String? = null,

    @Column(name = "summary")
    var summary: String? = null,

    @ManyToOne(cascade = [], fetch = FetchType.EAGER)
    @JoinColumn(name = "issue_type")
    var issueType: Feature? = null,

    @Column(name = "created")
    var created: Date? = null,

    @OneToMany(mappedBy = "task")
    var comments: List<Comment> = ArrayList<Comment>(),

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.PERSIST])//todo: OneToOne
    @JoinColumn(name = "consumables")
    var consumables: Consumables? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "sub_task", joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "subtask_id")]
    )
    var subTasks: Set<Task> = HashSet(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "sub_task", joinColumns = [JoinColumn(name = "subtask_id")],
        inverseJoinColumns = [JoinColumn(name = "task_id")]
    )
    var parentTasks: Set<Task> = HashSet(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "relation_task", joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "relation_task_id")]
    )
    var relationTasks: Set<Task> = HashSet(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "duplicate_task", joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "duplicate_task_id")]
    )
    var duplicateTasks: Set<Task> = HashSet()
) : GeneralEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Task

        if (keys != other.keys) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (keys?.hashCode() ?: 0)
        return result
    }
}
