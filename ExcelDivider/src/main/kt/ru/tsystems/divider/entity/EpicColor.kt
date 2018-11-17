package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "EpicColor")
data class EpicColor(
        @Column(name = "param")
        var color: String? = null
): GeneralEntity()
