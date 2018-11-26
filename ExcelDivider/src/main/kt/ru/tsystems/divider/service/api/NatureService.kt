package ru.tsystems.divider.service.api

import ru.tsystems.divider.entity.Nature

interface NatureService {

    fun getOrAddNatureByTitle(title: String): Nature

}