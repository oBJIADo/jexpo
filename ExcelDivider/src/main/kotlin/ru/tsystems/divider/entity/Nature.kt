package ru.tsystems.divider.entity

import ru.tsystems.divider.utils.constants.NATURE_DEFAULT
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Nature")
data class Nature(
    @Column(name = "title")
    var title: String = NATURE_DEFAULT
) : GeneralEntity()