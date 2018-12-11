package ru.tsystems.divider.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "Workers")
data class Workers(
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "assignee")
    var assignee: Employee? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "reporter")
    var reporter: Employee? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "creater")
    var creater: Employee? = null
) : GeneralEntity()