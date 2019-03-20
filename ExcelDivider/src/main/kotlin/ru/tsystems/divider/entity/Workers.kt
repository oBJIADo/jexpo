package ru.tsystems.divider.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "Workers")
data class Workers(
    @ManyToOne(cascade = [])
    @JoinColumn(name = "assignee")
    var assignee: Employee? = null,

    @ManyToOne(cascade = [])
    @JoinColumn(name = "reporter")
    var reporter: Employee? = null,

    @ManyToOne(cascade = [])
    @JoinColumn(name = "creater")
    var creater: Employee? = null
) : GeneralEntity()