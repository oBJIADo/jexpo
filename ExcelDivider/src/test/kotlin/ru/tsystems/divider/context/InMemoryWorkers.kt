package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.WorkersDao
import ru.tsystems.divider.entity.Workers

object InMemoryWorkers : InMemoryDaoGeneral<Workers>(), WorkersDao, ContextSimmulator<Workers> {
    init {
        reset()
    }
}