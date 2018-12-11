package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "nature")
data class Nature(
    @Column(name = "title")
    var title: String? = null
) : GeneralEntity()