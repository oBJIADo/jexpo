package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Team")
data class Team(
        @Column(name = "param")
        var team: String? = null
): GeneralEntity()