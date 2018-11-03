package ru.tsystems.jirexpo.entity

import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * Parent class for all entities which has only one field with name "param". It uses in dao and same another functions
 * which read, convert or do another same operations with this entities.
 */
@MappedSuperclass
open class OneParamEntity(
        @Column(name = "param")
        var param: String? = null
) : GeneralEntity()