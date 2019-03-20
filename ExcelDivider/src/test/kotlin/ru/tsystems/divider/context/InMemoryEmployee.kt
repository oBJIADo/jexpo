package ru.tsystems.divider.context

import ru.tsystems.divider.dao.api.EmployeeDao
import ru.tsystems.divider.entity.Employee

object InMemoryEmployee : InMemoryDaoGeneral<Employee>(), EmployeeDao, ContextSimmulator<Employee> {

    init {
        reset()
    }

    /**
     * Finding employee who has specified first and second names
     *
     * @param firstName  first name
     * @param secondName second name
     * @return [Employee]
     */
    override fun getByNamesIgnoreCase(firstName: String, secondName: String): Employee? {
        for (employee in database) {
            if (firstName.toLowerCase() == employee.firstname?.toLowerCase() &&
                secondName.toLowerCase() == employee.secondname?.toLowerCase()
            ) {
                return employee
            }
        }
        return null
    }
}