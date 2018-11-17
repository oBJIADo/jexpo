package ru.tsystems.divider.entity

import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Comment(
        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "task_id")
        var task: Task? = null,

        @Column(name = "comment_date")
        var commentDate: Date? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "author")
        var author: Employee? = null,

        @Column(name = "comment")
        var comment: String? = null
) : GeneralEntity()