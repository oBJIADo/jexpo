package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Statistics")
data class Statistics(
        @Column(name = "progress")
        var progress: Int? = null,
        @Column(name = "time_spent")
        var time_spent: Int? = null,
        @Column(name = "original_estimate")
        var original_estimate: Int? = null,
        @Column(name = "remaining_estimate")
        var remaining_estimate: Int? = null,
        @Column(name = "work_ration")
        var work_ration: Int? = null,
        @Column(name = "sum_progress")
        var sum_progress: Int? = null,
        @Column(name = "sum_time_spant")
        var sum_time_spant: Int? = null,
        @Column(name = "sum_remaining_estimate")
        var sum_remaining_estimate: Int? = null,
        @Column(name = "sum_original_estimate")
        var sum_original_estimate: Int? = null
): GeneralEntity()