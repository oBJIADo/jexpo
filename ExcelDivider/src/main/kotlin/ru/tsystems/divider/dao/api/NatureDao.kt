package ru.tsystems.divider.dao.api

import ru.tsystems.divider.entity.Nature

interface NatureDao : GeneralDao<Nature> {

    fun getByTitle(title: String): Nature?

}