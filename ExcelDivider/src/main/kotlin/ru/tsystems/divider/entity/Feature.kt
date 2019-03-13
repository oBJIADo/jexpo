package ru.tsystems.divider.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "Feature")
data class Feature(
    @Column(name = "title")
    var title: String? = null,

    @ManyToOne(cascade = [CascadeType.REFRESH])
    @JoinColumn(name = "nature")
    var nature: Nature? = null
) : GeneralEntity()