package ru.tsystems.jirexpo.entity

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "Workers")
data class Workers(
        @ManyToOne
        @JoinColumn(name = "assignee")
        var assignee: Employee? = null,

        @ManyToOne
        @JoinColumn(name = "reporter")
        var reporter: Employee? = null,

        @ManyToOne
        @JoinColumn(name = "creater")
        var creater: Employee? = null
) : GeneralEntity()