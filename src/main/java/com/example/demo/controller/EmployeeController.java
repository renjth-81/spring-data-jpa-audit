package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Value("${profile-name}")
	private String profileName;
	
	@GetMapping("/")
	public String foo(){
		return "profile - " + profileName;
	}
	
	@GetMapping("/add")
	public String add(){
		logger.info("employeeController add enter");
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("test");
		employeeDto.setAge(24);
		employeeService.add(employeeDto);
		logger.info("employeeController add return");
		return "added";
	}
	
	@GetMapping("/update")
	public String update(){
		logger.info("employeeController update enter");
		Optional<Employee> optional = employeeRepository.findById(1);
		if(optional.isPresent()){
			Employee employee = optional.get();
			employee.setName("dane");
			employeeRepository.save(employee);
		}
		logger.info("employeeController update return");
		return "updated";
	}
	
	@GetMapping("/all")
	public List<EmployeeDto> getAll(){
		logger.info("employeeController getAll enter");
		return employeeService.getAll();
	}

}
