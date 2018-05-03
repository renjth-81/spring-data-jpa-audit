package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public void add(EmployeeDto dto) {
		Employee employee = new Employee();
		employee.setAge(dto.getAge());
		employee.setName(dto.getName());
		employeeRepository.save(employee);
	}

}
