package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.StatisticsDao
import ru.tsystems.divider.entity.Statistics

object InMemoryStatistics : InMemoryDaoGeneral<Statistics>(), StatisticsDao, ContextSimmulator<Statistics> {
    init {
        reset()
    }
}