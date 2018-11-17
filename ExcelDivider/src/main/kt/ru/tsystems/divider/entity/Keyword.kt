package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Keyword")
data class Keyword(
        @Column(name = "param")
        var keyword: String? = null
) : GeneralEntity()