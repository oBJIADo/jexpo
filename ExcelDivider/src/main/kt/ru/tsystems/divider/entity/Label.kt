package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Label")
data class Label(
        @Column(name = "param")
        var label: String? = null
) : GeneralEntity()
