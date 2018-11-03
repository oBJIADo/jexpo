package ru.tsystems.jirexpo.entity

import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@Entity(name = "Task")
class Task(
        @Column(name = "keys")
        var keys: String? = null,

        @Column(name = "summary")
        var summary: String? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "issue_type")
        var issueType: IssueType? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "status")
        var status: Status? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "priority")
        var priority: Priority? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "resolution")
        var resolution: Resolution? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "assignee")
        var assignee: Employee? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "reporter")
        var reporter: Employee? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "creater")
        var creater: Employee? = null,

        @Column(name = "created")
        var created: Date? = null,

        @Column(name = "last_viewed")
        var lastViewed: Date? = null,

        @Column(name = "updated")
        var updated: Date? = null,

        @Column(name = "resolved")
        var resolved: Date? = null,

        @Column(name = "due_date")
        var dueDate: Date? = null,

        @Column(name = "original_estimate")
        var originalEstimate: Int? = null,

        @Column(name = "remaining_estimate")
        var remainingEstimate: Int? = null,

        @Column(name = "time_spent")
        var timeSpent: Int? = null,

        @Column(name = "work_ration")
        var workRation: Int? = null,

        @Column(name = "description")
        var description: String? = null,

        @Column(name = "progress")
        var progress: Int? = null,

        @Column(name = "sum_progress")
        var sumProgress: Int? = null,

        @Column(name = "sum_time_spant")
        var sumTimeSpant: Int? = null,

        @Column(name = "sum_remaining_estimate")
        var sumRemainingEstimate: Int? = null,

        @Column(name = "sum_original_estimate")
        var sumOriginalEstimate: Int? = null,

        @Column(name = "epic_name")
        var epicName: String? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "epic_status")
        var epicStatus: Status? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "epicColor")
        var epicColor: EpicColor? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "sprint")
        var sprint: Sprint? = null,

        @Column(name = "epic_link")
        var epicLink: String? = null,

        @Column(name = "order_number")
        var orderNumber: String? = null,

        @Column(name = "delivered_version")
        var deliveredVersion: String? = null,

        @Column(name = "drc_number")
        var drcNumber: String? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "keyword")
        var keyword: Keyword? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "fix_priority")
        var fixPriority: Priority? = null,

        @OneToMany(mappedBy = "task")
        var comments: List<Comment> = ArrayList<Comment>(),

        @ManyToMany
        @JoinTable(name = "sub_affects_version", joinColumns = [JoinColumn(name = "task_id")],
                inverseJoinColumns = [JoinColumn(name = "version_id")])
        var affectsVersions: Set<Version> = HashSet<Version>(),

        @ManyToMany
        @JoinTable(name = "task_fix_version", joinColumns = [JoinColumn(name = "task_id")],
                inverseJoinColumns = [JoinColumn(name = "version_id")])
        var fixVersions: Set<Version> = HashSet<Version>(),

        @ManyToMany
        @JoinTable(name = "task_component", joinColumns = [JoinColumn(name = "task_id")],
                inverseJoinColumns = [JoinColumn(name = "component_id")])
        var components: Set<Component> = HashSet<Component>(),

        @ManyToMany
        @JoinTable(name = "task_label", joinColumns = [JoinColumn(name = "task_id")],
                inverseJoinColumns = [JoinColumn(name = "label_id")])
        var labels: Set<Label> = HashSet<Label>(),

        @ManyToMany
        @JoinTable(name = "task_team", joinColumns = [JoinColumn(name = "task_id")],
                inverseJoinColumns = [JoinColumn(name = "team_id")])
        var teams: Set<Team> = HashSet<Team>(),

        @ManyToMany
        @JoinTable(name = "sub_task", joinColumns = [JoinColumn(name = "task_id")],
                inverseJoinColumns = [JoinColumn(name = "subtask_id")])
        var subTasks: Set<Task> = HashSet<Task>(),

        @ManyToMany
        @JoinTable(name = "sub_task", joinColumns = [JoinColumn(name = "subtask_id")],
                inverseJoinColumns = [JoinColumn(name = "task_id")])
        var parentTasks: Set<Task> = HashSet<Task>(),

        @ManyToMany
        @JoinTable(name = "relation_task", joinColumns = [JoinColumn(name = "task_id")],
                inverseJoinColumns = [JoinColumn(name = "relation_task_id")])
        var relationTasks: Set<Task> = HashSet<Task>(),

        @ManyToMany
        @JoinTable(name = "duplicate_task", joinColumns = [JoinColumn(name = "task_id")],
                inverseJoinColumns = [JoinColumn(name = "duplicate_task_id")])
        var duplicateTasks: Set<Task> = HashSet<Task>()
) : GeneralEntity()
