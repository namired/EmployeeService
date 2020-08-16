package com.paypal.bfs.test.employeeserv.exception;

public class EmployeeAlreadyExistException extends RuntimeException{
    public EmployeeAlreadyExistException(Integer id) {
        super("Employee already exists with ID : " + id);
    }
}

