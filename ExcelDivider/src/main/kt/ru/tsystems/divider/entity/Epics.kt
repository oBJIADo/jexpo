package ru.tsystems.divider.entity

import javax.persistence.*

@Entity(name = "Epics")
class Epics(
        @Column(name = "epic_name")
        var epicName: String? = null,
        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "epic_status")
        val epicStatus: Status? = null,
        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "epic_color")
        var epicColor: EpicColor? = null,
        @Column(name = "epic_link")
        var epicLink: String? = null
) : GeneralEntity()