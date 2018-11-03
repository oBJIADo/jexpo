package ru.tsystems.jirexpo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.tsystems.jirexpo.dto.EmployeeDto;
import ru.tsystems.jirexpo.service.api.EmployeeService;

@RestController
@RequestMapping("/rest/employees")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Get all employees.
     * 
     * @return List with {@link EmployeeDto}
     */
    @GetMapping()
    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }

}
