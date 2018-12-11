package ru.tsystems.divider.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity(name = "Consumables")
data class Consumables(
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "status")
    var status: Feature? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "priority")
    var priority: Feature? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "resolution")
    var resolution: Feature? = null,

    @Column(name = "description")
    var description: String? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "sprint")
    var sprint: Feature? = null,

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

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "drc_number")
    var drcNumber: String? = null,

    @Column(name = "order_number")
    var orderNumber: String? = null,

    @Column(name = "delivered_version")
    var deliveredVersion: Feature? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "keyword")
    var keyword: Feature? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "fix_priority")
    var fixPriority: Feature? = null,

    @ManyToMany
    @JoinTable(
        name = "sub_affects_version", joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "version_id")]
    )
    var affectsVersions: Set<Feature> = HashSet(),

    @ManyToMany
    @JoinTable(
        name = "task_fix_version", joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "version_id")]
    )
    var fixVersions: Set<Feature> = HashSet(),

    @ManyToMany
    @JoinTable(
        name = "task_component", joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "component_id")]
    )
    var components: Set<Feature> = HashSet(),

    @ManyToMany
    @JoinTable(
        name = "task_label", joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "label_id")]
    )
    var labels: Set<Feature> = HashSet(),

    @ManyToMany
    @JoinTable(
        name = "task_team", joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "team_id")]
    )
    var teams: Set<Feature> = HashSet()

) : GeneralEntity()