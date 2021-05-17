package org.gruzdov.spring.rest.controller;

import org.gruzdov.spring.rest.entity.Employee;
import org.gruzdov.spring.rest.exception_handling.EmployeeIncorrectData;
import org.gruzdov.spring.rest.exception_handling.NoSuchEmployeeException;
import org.gruzdov.spring.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        List<Employee> allEmployee = employeeService.getAllEmployees();

        return allEmployee;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id);

        if (employee == null)
            throw new NoSuchEmployeeException("There is no employee with ID = " +
                    id + " int Database");

        return employee;
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(
            NoSuchEmployeeException exception) {
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
