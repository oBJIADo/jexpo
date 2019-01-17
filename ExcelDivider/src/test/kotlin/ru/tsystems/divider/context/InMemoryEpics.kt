package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.EpicsDao
import ru.tsystems.divider.entity.Epics

object InMemoryEpics : InMemoryDaoGeneral<Epics>(), EpicsDao, ContextSimmulator<Epics> {
    init {
        reset()
    }
}