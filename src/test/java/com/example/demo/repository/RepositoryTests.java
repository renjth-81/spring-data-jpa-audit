package com.example.demo.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.Address;
import com.example.demo.entity.Employee;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTests {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	TestEntityManager testEntityManager;

	@Test
	public void testAdd() {
		employeeRepository.save(getEmployee());
		Employee employee = employeeRepository.findByName("Turing");
		assertEquals("Turing", employee.getName());
		assertEquals(32, employee.getAge());
	}

	private Employee getEmployee() {
		Employee employee = new Employee();
		employee.setAge(32);
		employee.setName("Turing");
		return employee;
	}

	@Before
	public void init() {
		List<Employee> emps = new ArrayList<>();

		// 1st employee
		Employee e1 = new Employee();
		e1.setName("e1");
		e1.setAge(23);

		Address a1 = new Address();
		a1.setLine1("omr");
		a1.setLine2("tidel park");
		a1.setState("TN");
		a1.setZipcode("600113");
		a1.setCity("chennai");

		Address a2 = new Address();
		a2.setLine1("taramani");
		a2.setLine2("ascendas it park");
		a2.setState("TN");
		a2.setZipcode("600113");
		a2.setCity("chennai");

		e1.addAddress(a1);
		e1.addAddress(a2);
		emps.add(e1);

		// 2nd employee
		// Employee e2 = new Employee();
		// e2.setName("e2");
		// e2.setAge(24);
		// e2.addAddress(a1);
		// emps.add(e2);

		employeeRepository.saveAll(emps);
	}
	
	@Test
	public void test_cascadeDelete() {
			
		Employee employee = employeeRepository.findByName("e1");
		assertEquals("e1", employee.getName());
		List<Address> addresses = addressRepository.findByEmployee_id(employee.getId());
		assertEquals(2, addresses.size());
		
		employeeRepository.delete(employee);
		Employee employee1 = employeeRepository.findByName("e1");
		assertEquals(null, employee1);
		List<Address> addresses1 = addressRepository.findByEmployee_id(employee.getId());
		assertEquals(0, addresses1.size());
	}

	@Test
	public void test_Fetch_nPlusOne() {
		List<Employee> employees = employeeRepository.findAll();
		List<Address> addresses = addressRepository.findAll();
		assertEquals(1, employees.size());
		assertEquals(2, addresses.size());
	}

	@Test
	public void test_Fetch_All_Employees() {
		List<Employee> employees = employeeRepository.findAll();
		Employee e1 = employees.get(0);
		assertEquals(2, e1.getAddresses().size());
		Address a2 = e1.getAddresses().get(1);
		e1.getAddresses().remove(a2);
		Employee savedEmp = employeeRepository.save(e1);
		assertEquals(1, savedEmp.getAddresses().size());
		assertEquals("omr", savedEmp.getAddresses().get(0).getLine1());
	}

	@Test
	public void test_Cascade_Test_Remove() {
		List<Employee> employees = employeeRepository.findAll();
		Employee e1 = employees.get(0);
		assertEquals(2, e1.getAddresses().size());
		Address a2 = e1.getAddresses().get(1);
		e1.getAddresses().remove(a2);
		Employee savedEmp = employeeRepository.save(e1);
		assertEquals(1, savedEmp.getAddresses().size());
		assertEquals("omr", savedEmp.getAddresses().get(0).getLine1());
	}

	@Test
	public void cascadeTest1() {
		List<Employee> employees = employeeRepository.findAll();
		assertEquals(1, employees.size());
		Employee e1 = employees.get(0);
		Address a2 = e1.getAddresses().get(1);
		assertEquals("taramani", a2.getLine1());
	}

	@Test
	public void test_cascadeSave() {
		Employee e1 = new Employee();
		e1.setName("e1");
		e1.setAge(23);

		Address a1 = new Address();
		a1.setLine1("omr");
		a1.setLine2("tidel park");
		a1.setState("TN");
		a1.setZipcode("600113");
		a1.setCity("chennai");

		e1.addAddress(a1);

		Employee savedEmp = employeeRepository.save(e1);
		assertEquals("e1", savedEmp.getName());
		List<Address> addresses = savedEmp.getAddresses();
		assertEquals("omr", addresses.get(0).getLine1());
	}

}
