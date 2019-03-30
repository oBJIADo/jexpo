package ru.tsystems.jirexpo.entity

import javax.persistence.*

@Entity(name = "Consumables")
data class Consumables(
        @ManyToOne
        @JoinColumn(name = "status")
        var status: Feature? = null,

        @ManyToOne
        @JoinColumn(name = "priority")
        var priority: Feature? = null,

        @ManyToOne
        @JoinColumn(name = "resolution")
        var resolution: Feature? = null,

        @Column(name = "description")
        var description: String? = null,

        @ManyToOne
        @JoinColumn(name = "sprint")
        var sprint: Feature? = null,

        @ManyToOne
        @JoinColumn(name = "workers")
        var workers: Workers? = null,

        @ManyToOne
        @JoinColumn(name = "epics")
        var epics: Epics? = null,

        @ManyToOne
        @JoinColumn(name = "statistics")
        var statistics: Statistics? = null,

        @ManyToOne
        @JoinColumn(name = "dates")
        var dates: Dates? = null,

        @Column(name = "drc_number")
        var drcNumber: String? = null,

        @Column(name = "order_number")
        var orderNumber: String? = null,

        @ManyToOne
        @JoinColumn(name = "delivered_version")
        var deliveredVersion: Feature? = null,

        @ManyToOne
        @JoinColumn(name = "keyword")
        var keyword: Feature? = null,

        @ManyToOne
        @JoinColumn(name = "fix_priority")
        var fixPriority: Feature? = null,

        @ManyToMany
        @JoinTable(
                name = "sub_affects_version", joinColumns = [JoinColumn(name = "consumables_id")],
                inverseJoinColumns = [JoinColumn(name = "version_id")]
        )
        var affectsVersions: Set<Feature> = HashSet(),

        @ManyToMany
        @JoinTable(
                name = "task_fix_version", joinColumns = [JoinColumn(name = "consumables_id")],
                inverseJoinColumns = [JoinColumn(name = "version_id")]
        )
        var fixVersions: Set<Feature> = HashSet(),

        @ManyToMany
        @JoinTable(
                name = "task_component", joinColumns = [JoinColumn(name = "consumables_id")],
                inverseJoinColumns = [JoinColumn(name = "component_id")]
        )
        var components: Set<Feature> = HashSet(),

        @ManyToMany
        @JoinTable(
                name = "task_label", joinColumns = [JoinColumn(name = "consumables_id")],
                inverseJoinColumns = [JoinColumn(name = "label_id")]
        )
        var labels: Set<Feature> = HashSet(),

        @ManyToMany
        @JoinTable(
                name = "task_team", joinColumns = [JoinColumn(name = "consumables_id")],
                inverseJoinColumns = [JoinColumn(name = "team_id")]
        )
        var teams: Set<Feature> = HashSet()

) : GeneralEntity()