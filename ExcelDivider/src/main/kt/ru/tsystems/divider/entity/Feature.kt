package ru.tsystems.divider.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

data class Feature(
        @Column(name = "title")
        var title: String? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "nature")
        var nature: Nature? = null
) : GeneralEntity()