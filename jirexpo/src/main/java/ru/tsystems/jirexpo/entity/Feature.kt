package ru.tsystems.jirexpo.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "Feature")
data class Feature(
        @Column(name = "title")
        var title: String? = null,

        @ManyToOne
        @JoinColumn(name = "nature")
        var nature: Nature? = null
) : GeneralEntity()