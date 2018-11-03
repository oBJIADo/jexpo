package ru.tsystems.divider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsystems.divider.dao.api.EmployeeDao;
import ru.tsystems.divider.entity.Employee;
import ru.tsystems.divider.service.api.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    @Transactional
    /**
     * Persist into db Employee entity.
     *
     * @param employee
     *            Employee entity.
     */
    public void persist(Employee employee) {
        employeeDao.persist(employee);
    }

    @Override
    @Transactional
    /**
     * Find employee by names.
     *
     * @param firstName
     *            Employee's firstname.
     * @param secondName
     *            Employee's secondname.
     * @return Employee
     */
    public Employee getByNames(String firstName, String secondName) {
        return employeeDao.getByNames(firstName, secondName);
    }
}
