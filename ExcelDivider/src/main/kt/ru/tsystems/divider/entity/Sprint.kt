package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Sprint")
class Sprint(
        @Column(name = "param")
        var sprint: String? = null
) : GeneralEntity()