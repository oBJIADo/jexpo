package ru.tsystems.jirexpo.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "Epics")
data class Epics(
        @Column(name = "epic_name")
        var epicName: String? = null,
        @ManyToOne
        @JoinColumn(name = "epic_status")
        var epicStatus: Feature? = null,
        @ManyToOne
        @JoinColumn(name = "epic_color")
        var epicColor: Feature? = null,
        @Column(name = "epic_link")
        var epicLink: String? = null
) : GeneralEntity()