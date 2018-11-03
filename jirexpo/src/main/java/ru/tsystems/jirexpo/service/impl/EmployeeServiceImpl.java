package ru.tsystems.jirexpo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsystems.jirexpo.components.impl.Converter;
import ru.tsystems.jirexpo.dao.api.EmployeeDao;
import ru.tsystems.jirexpo.dto.EmployeeDto;
import ru.tsystems.jirexpo.entity.Employee;
import ru.tsystems.jirexpo.service.api.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * Persist into db Employee entity.
     *
     * @param employee
     *            Employee entity.
     */
    @Override
    @Transactional
    public void persist(Employee employee) {
        employeeDao.persist(employee);
    }

    /**
     * Find employee by names.
     *
     * @param firstName
     *            Employee's firstname.
     * @param secondName
     *            Employee's secondname.
     * @return Employee
     */
    @Override
    @Transactional
    public EmployeeDto getByNames(String firstName, String secondName) {
        return Converter.convertToDto(employeeDao.getByNames(firstName, secondName));
    }

    /**
     * Get all employees
     * @return List with {@link EmployeeDto}
     */
    @Override
    public List<EmployeeDto> getAll() {
        return employeeDao.getAll(Employee.class).stream().map(Converter::convertToDto)
                .collect(Collectors.toList());
    }
}
