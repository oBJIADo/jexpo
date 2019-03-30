package ru.tsystems.jirexpo.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Statistics")
data class Statistics(
        @Column(name = "progress")
        var progress: Int? = null,
        @Column(name = "time_spent")
        var timeSpent: Int? = null,
        @Column(name = "original_estimate")
        var originalEstimate: Int? = null,
        @Column(name = "remaining_estimate")
        var remainingEstimate: Int? = null,
        @Column(name = "work_ration")
        var workRation: Int? = null,
        @Column(name = "sum_progress")
        var sumProgress: Int? = null,
        @Column(name = "sum_time_spant")
        var sumTimeSpant: Int? = null,
        @Column(name = "sum_remaining_estimate")
        var sumRemainingEstimate: Int? = null,
        @Column(name = "sum_original_estimate")
        var sumOriginalEstimate: Int? = null
) : GeneralEntity()