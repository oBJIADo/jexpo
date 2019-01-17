package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.ConsumablesDao
import ru.tsystems.divider.entity.Consumables

object InMemoryConsumables : InMemoryDaoGeneral<Consumables>(), ConsumablesDao, ContextSimmulator<Consumables> {
    init {
        reset()
    }
}