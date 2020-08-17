package com.paypal.bfs.test.employeeserv;

import com.paypal.bfs.test.employeeserv.entity.Address;
import com.paypal.bfs.test.employeeserv.entity.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeApplicationTests {
	@Autowired
	EmployeeRepository repository;

	@Test
	public void testCreateEmployee() throws Exception {
		Employee emp = new Employee();
		emp.setId(1);
		emp.setFirstName("Sam");
		emp.setLastName("Bear");
		emp.setDateOfBirth(new Date());
		Address address = new Address();
		address.setLine1("15711 Clems");
		address.setCity("Charlotte");
		address.setState("NC");
		address.setCountry("USA");
		address.setZip(28277);
		emp.setAddress(address);

		//Create Test
		Employee emp1 = repository.save(emp);
		assertNotNull(emp1);
		assertEquals("Sam", emp1.getFirstName());
	}

	@Test
	public void testRetriveEmployee() throws Exception {
		//Retrieve test
		Optional<Employee> emp2 = repository.findById(100);
		assertTrue(emp2.isPresent());
		assertNotNull(emp2.get());
		assertEquals("Json", emp2.get().getFirstName());
	}

}
