package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.NatureDao
import ru.tsystems.divider.entity.Nature
import ru.tsystems.divider.utils.constants.*

object InMemoryNature : InMemoryDaoGeneral<Nature>(), NatureDao, ContextSimmulator<Nature> {

    override fun reset() {
        super.reset()
        database.add(Nature(NATURE_VERSION))
        database.add(Nature(NATURE_EPIC_STATUS))
        database.add(Nature(NATURE_EPIC_COLOR))
        database.add(Nature(NATURE_STATUS))
        database.add(Nature(NATURE_PRIORITY))
        database.add(Nature(NATURE_RESOLUTION))
        database.add(Nature(NATURE_SPRINT))
        database.add(Nature(NATURE_KEYWORD))
        database.add(Nature(NATURE_ISSUE_TYPE))
        database.add(Nature(NATURE_COMPONENT))
        database.add(Nature(NATURE_LABEL))
        database.add(Nature(NATURE_TEAM))
        database.add(Nature(NATURE_DEFAULT))
    }

    override fun getDefaultNature(): Nature {
        for (nature in database) {
            if (nature.title == NATURE_DEFAULT) {
                return nature
            }
        }
        database.add(Nature(NATURE_DEFAULT))
        return database[database.size - 1]
    }

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