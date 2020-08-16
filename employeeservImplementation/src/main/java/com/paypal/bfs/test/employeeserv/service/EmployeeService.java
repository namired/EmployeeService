package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Integer id);
    Employee createEmployee(Employee employee);
}
