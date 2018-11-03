package ru.tsystems.divider.service.api;

import ru.tsystems.divider.entity.Employee;

public interface EmployeeService {

    /**
     * Persist into db Employee entity.
     *
     * @param employee
     *            Employee entity.
     */
    void persist(Employee employee);

    /**
     * Find employee by names.
     * 
     * @param firstName
     *            Employee's firstname.
     * @param secondName
     *            Employee's secondname.
     * @return Employee
     */
    Employee getByNames(String firstName, String secondName);
}
