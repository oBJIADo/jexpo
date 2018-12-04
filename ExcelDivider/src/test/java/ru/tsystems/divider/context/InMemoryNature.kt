package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.NatureDao
import ru.tsystems.divider.entity.Nature

object InMemoryNature : InMemoryDaoGeneral<Nature>(), NatureDao, ContextSimmulator<Nature> {

    init {
        reset()
    }

    override fun getByTitle(title: String): Nature? {
        for (nature in database) {
            if (title == nature.title) {
                return nature
            }
        }
        return null
    }
}