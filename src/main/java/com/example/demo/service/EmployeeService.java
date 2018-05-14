package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<EmployeeDto> getAll() {
		return convertToDtos(employeeRepository.findAll());
	}

	public List<EmployeeDto> convertToDtos(List<Employee> employees) {
		List<EmployeeDto> employeeDtos = new ArrayList<>();
		if(employees!=null && !employees.isEmpty()){
			employeeDtos = new ArrayList<>();
			for(Employee employee: employees){
				employeeDtos.add(convertToDto(employee));
			}
		}
		return employeeDtos;
	}

	public EmployeeDto convertToDto(Employee employee) {
		EmployeeDto dto = new EmployeeDto();
		dto.setAge(employee.getAge());
		dto.setName(employee.getName());
		dto.setId(employee.getId());
		return dto;
	}

	public EmployeeDto getById(int id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		EmployeeDto dto =null;
		if (optional.isPresent()) {
			dto = convertToDto(optional.get());
		}
		return dto;
	}

}
