package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeAlreadyExistException;
import com.paypal.bfs.test.employeeserv.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.mapper.EmployeeModelMapper;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation class for employee resource.
 */
@RestController
@Validated
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        Optional<com.paypal.bfs.test.employeeserv.entity.Employee> employeeEntity
                = employeeService.getEmployeeById(Integer.valueOf(id));
        if(!employeeEntity.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }
        return new ResponseEntity<>(EmployeeModelMapper.converEmployeeEntiryToModel(employeeEntity.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws Exception{
        if(employeeService.getEmployeeById(employee.getId()).isPresent()) {
            throw new EmployeeAlreadyExistException(employee.getId());
        }
        com.paypal.bfs.test.employeeserv.entity.Employee emp
                = employeeService.createEmployee(EmployeeModelMapper.convertEmployeeModelToEntity(employee));
        return new ResponseEntity<>(EmployeeModelMapper.converEmployeeEntiryToModel(emp), HttpStatus.CREATED);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(cv -> {
            errors.put("message", cv.getMessage());
            errors.put("path", (cv.getPropertyPath()).toString());
        });

        return errors;
    }

}
