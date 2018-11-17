package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Resolution")
data class Resolution(
        @Column(name = "param")
        var resolution: String? = null
) : GeneralEntity()
