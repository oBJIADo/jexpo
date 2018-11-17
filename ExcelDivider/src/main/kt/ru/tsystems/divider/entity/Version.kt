package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Version")
data class Version(
        @Column(name = "param")
        var version: String? = null
) : GeneralEntity()