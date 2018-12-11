package ru.tsystems.divider.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "feature")
data class Feature(
    @Column(name = "title")
    var title: String? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "nature")
    var nature: Nature? = null
) : GeneralEntity()