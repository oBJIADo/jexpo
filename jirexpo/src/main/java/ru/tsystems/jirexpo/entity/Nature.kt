package ru.tsystems.jirexpo.entity

import javax.persistence.Column
import javax.persistence.Entity

const val NATURE_DEFAULT = "feature"

@Entity(name = "Nature")
data class Nature(
    @Column(name = "title")
    var title: String = NATURE_DEFAULT
) : GeneralEntity()