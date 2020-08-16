package com.paypal.bfs.test.employeeserv.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(String id) {
        super("Employee id not found with ID : " + id);
    }
}
