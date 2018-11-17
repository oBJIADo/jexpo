package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "IssueType")
data class IssueType(
        @Column(name = "param")
        var type: String? = null
) : GeneralEntity()
