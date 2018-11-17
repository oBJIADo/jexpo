package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Status")
data class Status(
        @Column(name = "param")
        var status: String? = null
) : GeneralEntity()