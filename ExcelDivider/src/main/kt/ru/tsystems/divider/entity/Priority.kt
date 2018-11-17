package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Priority")
data class Priority(
        @Column(name = "param")
        var priority: String? = null
) : GeneralEntity()
