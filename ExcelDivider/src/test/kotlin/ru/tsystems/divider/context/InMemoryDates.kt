package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.DatesDao
import ru.tsystems.divider.entity.Dates

object InMemoryDates : InMemoryDaoGeneral<Dates>(), DatesDao, ContextSimmulator<Dates> {
    init {
        reset()
    }
}