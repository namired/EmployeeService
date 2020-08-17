package com.paypal.bfs.test.employeeserv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.regex.MatchResult;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = EmployeeservApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:import.sql")
public class EmployeeApplicationIntegrationTests {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Test
	public void givenEmployeeID_WhenGetEmployeeById_ThenExpectedEmployee() throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/bfs/employees/100").accept(MediaType.APPLICATION_JSON)
		).andReturn();
		Employee respEmployee = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);
		Assert.assertNotNull(respEmployee);
		Assert.assertEquals(respEmployee.getId().intValue(), 100);
	}

	@Test
	public void givenEmployeeID_WhenGetEmployeeById_ThenEmployeeNull() throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/bfs/employees/200").accept(MediaType.APPLICATION_JSON)
		).andReturn();
		Assert.assertEquals(mvcResult.getResponse().getStatus(), 404);
		Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Employee id not found with ID : 200"));
	}

	@Test
	public void givenEmployee_WhenPerformCreateEmployee_Thencreated() throws Exception
	{
		Employee emp = new Employee();
		emp.setId(1);
		emp.setFirstName("Sam");
		emp.setLastName("Bear");
		emp.setDateOfBirth("12/12/1983");
		Address address = new Address();
		address.setLine1("15711 Clems");
		address.setCity("Charlotte");
		address.setState("NC");
		address.setCountry("USA");
		address.setZip(28277);
		emp.setAddress(address);

		mockMvc.perform( MockMvcRequestBuilders
				.post("/v1/bfs/createEmployee")
				.content(objectMapper.writeValueAsString(emp))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	@Test
	public void givenEmployee_WhenPerformCreateEmployee_ThenBadRequest() throws Exception
	{
		Employee emp = new Employee();
		emp.setId(1);
		emp.setFirstName("Sam");
		emp.setLastName("");
		emp.setDateOfBirth("12/12/1983");
		Address address = new Address();
		address.setLine1("15711 Clems");
		address.setCity("Charlotte");
		address.setState("NC");
		address.setCountry("USA");
		address.setZip(28277);
		emp.setAddress(address);

		mockMvc.perform( MockMvcRequestBuilders
				.post("/v1/bfs/createEmployee")
				.content(objectMapper.writeValueAsString(emp))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("size must be between 1 and 255")));
	}
}
