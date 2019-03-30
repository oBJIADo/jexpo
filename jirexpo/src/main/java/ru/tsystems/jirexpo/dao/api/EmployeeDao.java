package ru.tsystems.jirexpo.dao.api;

import ru.tsystems.jirexpo.entity.Employee;

public interface EmployeeDao extends GeneralDao<Employee> {
    /**
     * Finding employee who has specified first and second names
     *
     * @param firstName  first name
     * @param secondName second name
     * @return {@link Employee}
     */
    Employee getByNames(String firstName, String secondName);
}
