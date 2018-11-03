package ru.tsystems.jirexpo.service.api;

import java.util.List;

import ru.tsystems.jirexpo.dto.EmployeeDto;
import ru.tsystems.jirexpo.entity.Employee;

public interface EmployeeService {

    /**
     * Persist into db Employee entity.
     *
     * @param employee
     *            Employee entity.
     */
    public void persist(Employee employee);

    /**
     * Find employee by names.
     * 
     * @param firstName
     *            Employee's firstname.
     * @param secondName
     *            Employee's secondname.
     * @return Employee
     */
    public EmployeeDto getByNames(String firstName, String secondName);

    /**
     * Get all employees
     * @return List with {@link EmployeeDto}
     */
    public List<EmployeeDto> getAll();
}
