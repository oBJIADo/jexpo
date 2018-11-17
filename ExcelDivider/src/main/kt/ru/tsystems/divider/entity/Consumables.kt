package ru.tsystems.divider.entity

import javax.persistence.*

@Entity(name = "Consumables")
data class Consumables(
        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "status")
        var status: Status? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "priority")
        var priority: Priority? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "resolution")
        var resolution: Resolution? = null,

        @Column(name = "description")
        var description: String? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "sprint")
        var sprint: Sprint? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "workers")
        var workers: Workers? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "epics")
        var epics: Epics? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "statistics")
        var statistics: Statistics? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "dates")
        var dates: Dates? = null,

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
        var teams: Set<Team> = HashSet<Team>()

) : GeneralEntity()