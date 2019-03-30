package ru.tsystems.jirexpo.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Dates")
data class Dates(
        @Column(name = "updated")
        var updated: Date? = null,
        @Column(name = "resolved")
        var resolved: Date? = null,
        @Column(name = "due_date")
        var dueDate: Date? = null,
        @Column(name = "last_viewed")
        var lastViewed: Date? = null
) : GeneralEntity()