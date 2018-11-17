package ru.tsystems.divider.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Component")
data class Component(
        @Column(name = "param")
        var component: String? = null
): GeneralEntity()
