package ru.tsystems.divider.service.api.functional

import ru.tsystems.divider.entity.Employee

interface EmployeeBuilder {
    fun buildEmployee(employee: String): Employee
}