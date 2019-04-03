package ru.tsystems.jirexpo.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "Employee")
data class Employee(
        @Column(name = "firstname") val firstname: String? = null,
        @Column(name = "secondname") val secondname: String? = null
) : GeneralEntity() {
    override fun toString(): String = "$firstname, $secondname"
}