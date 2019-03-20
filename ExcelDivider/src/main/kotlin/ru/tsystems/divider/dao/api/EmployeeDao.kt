package ru.tsystems.divider.dao.api

import ru.tsystems.divider.entity.Employee

interface EmployeeDao : GeneralDao<Employee> {
    /**
     * Finding employee who has specified first and second names
     *
     * @param firstName
     * first name
     * @param secondName
     * second name
     * @return [Employee]
     */
    fun getByNamesIgnoreCase(firstName: String, secondName: String): Employee?
}
