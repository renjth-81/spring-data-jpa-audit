package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	// @InjectMocks
	// EmployeeService employeeService;
	//
	// @Mock
	// EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;

	@MockBean
	EmployeeRepository employeeRepository;

	@Test
	public void findEmployeeValidId() {
		when(employeeRepository.findById(Mockito.anyInt())).thenReturn(validEmployee());
		EmployeeDto dto = employeeService.getById(1);
		assertEquals("testEmployee", dto.getName());
		assertEquals(23, dto.getAge());
		assertEquals(1, dto.getId());
	}

	@Test
	public void findEmployeeInvalidId() {
		when(employeeRepository.findById(Mockito.anyInt())).thenReturn(invalidEmployee());
		EmployeeDto dto = employeeService.getById(1);
		assertEquals(null, dto.getName());
		assertEquals(0, dto.getAge());
		assertEquals(0, dto.getId());
	}

	private Optional<Employee> validEmployee() {
		Employee employee = new Employee();
		employee.setAge(23);
		employee.setName("testEmployee");
		employee.setId(1);
		return Optional.of(employee);
	}

	private Optional<Employee> invalidEmployee() {
		return Optional.empty();
	}

}
