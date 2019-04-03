package ru.tsystems.jirexpo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsystems.jirexpo.dto.EmployeeDto;
import ru.tsystems.jirexpo.service.api.EmployeeService;

import java.util.List;

@RestController
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Get all employees.
     *
     * @return List with {@link EmployeeDto}
     */
    @GetMapping("employees")
    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }
}
