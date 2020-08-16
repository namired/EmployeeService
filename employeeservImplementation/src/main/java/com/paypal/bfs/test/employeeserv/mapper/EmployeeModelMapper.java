package com.paypal.bfs.test.employeeserv.mapper;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EmployeeModelMapper {
    public static final String MM_DD_YYYY = "MM/dd/yyyy";
    public static com.paypal.bfs.test.employeeserv.entity.Employee convertEmployeeModelToEntity(Employee employee) throws Exception{
        com.paypal.bfs.test.employeeserv.entity.Employee entityEmp = new com.paypal.bfs.test.employeeserv.entity.Employee();
        entityEmp.setId(employee.getId());
        entityEmp.setFirstName(employee.getFirstName());
        entityEmp.setLastName(employee.getLastName());
        DateFormat dateFormatter = new SimpleDateFormat(MM_DD_YYYY);
        try {
            entityEmp.setDateOfBirth(dateFormatter.parse(employee.getDateOfBirth()));
        } catch (Exception ex) {
            throw ex;
        }
        entityEmp.setAddress(convertAddressModelToEntity(employee.getAddress()));
        return entityEmp;
    }
    public static Employee converEmployeeEntiryToModel(com.paypal.bfs.test.employeeserv.entity.Employee entityEmp) {
        Employee employee = new Employee();
        employee.setId(entityEmp.getId());
        employee.setFirstName(entityEmp.getFirstName());
        employee.setLastName(entityEmp.getLastName());
        DateFormat dateFormatter = new SimpleDateFormat(MM_DD_YYYY);
        employee.setDateOfBirth(dateFormatter.format(entityEmp.getDateOfBirth()));
        employee.setAddress(convertAddressEntityToModel(entityEmp.getAddress()));
        return employee;
    }

    public static com.paypal.bfs.test.employeeserv.entity.Address convertAddressModelToEntity(Address addressModel) {
        com.paypal.bfs.test.employeeserv.entity.Address addressEntity = new com.paypal.bfs.test.employeeserv.entity.Address();
        addressEntity.setLine1(addressModel.getLine1());
        addressEntity.setLine2(addressModel.getLine2());
        addressEntity.setCity(addressModel.getCity());
        addressEntity.setState(addressModel.getState());
        addressEntity.setCountry(addressModel.getCountry());
        addressEntity.setZip(addressModel.getZip());
        return addressEntity;

    }

    public static Address convertAddressEntityToModel(com.paypal.bfs.test.employeeserv.entity.Address addressEntity) {
        Address addressModel = new Address();
        addressModel.setLine1(addressEntity.getLine1());
        addressModel.setLine2(addressEntity.getLine2());
        addressModel.setCity(addressEntity.getCity());
        addressModel.setState(addressEntity.getState());
        addressModel.setCountry(addressEntity.getCountry());
        addressModel.setZip(addressEntity.getZip());
        return addressModel;

    }
}
