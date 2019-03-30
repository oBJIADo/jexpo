package ru.tsystems.jirexpo.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Comment(
        @JoinColumn(name = "task_id")
        var task: Task? = null,

        @Column(name = "comment_date")
        var commentDate: LocalDateTime? = null,

        @ManyToOne
        @JoinColumn(name = "author")
        var author: Employee? = null,

        @Column(name = "comment")
        var comment: String? = null
) : GeneralEntity() {

    override fun toString():
            String = "Author: ${author.toString()}\nDate: ${commentDate.toString()}\n-----------\n$comment"

}