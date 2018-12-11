package ru.tsystems.divider.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "Epics")
data class Epics(
    @Column(name = "epic_name")
    var epicName: String? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "epic_status")
    val epicStatus: Feature? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "epic_color")
    var epicColor: Feature? = null,
    @Column(name = "epic_link")
    var epicLink: String? = null
) : GeneralEntity()