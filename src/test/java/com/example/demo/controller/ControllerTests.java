package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class ControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmployeeService employeeService;

	@MockBean
	EmployeeRepository employeeRepository;

	@Test
	public void testAdd() throws Exception {
		when(this.employeeService.getById(Mockito.anyInt())).thenReturn(getEmployeeDto());
		this.mvc.perform(get("/update").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(content().string("updated"));
	}

	@Test
	public void testAll() throws Exception {
		when(this.employeeService.getAll()).thenReturn(getEmployeeDtos());
		this.mvc.perform(get("/all").accept(MediaType.TEXT_PLAIN)).andExpect(status().isNotAcceptable());
		this.mvc.perform(get("/all").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(getJsonString("emp_list.json")));
	}

	private String getJsonString(String fileName) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(new ClassPathResource("emp_list.json").getFile()));
		JSONArray jsonArray = (JSONArray) obj;
		return jsonArray.toJSONString();
	}

	private EmployeeDto getEmployeeDto() {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setAge(23);
		employeeDto.setName("testEmployee");
		employeeDto.setId(1);
		return employeeDto;
	}

	private List<EmployeeDto> getEmployeeDtos() {
		List<EmployeeDto> employeeDtos = new ArrayList<>();
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setAge(22);
		employeeDto.setName("goku");
		employeeDto.setId(1);
		employeeDtos.add(employeeDto);
		EmployeeDto employeeDto1 = new EmployeeDto();
		employeeDto1.setAge(25);
		employeeDto1.setName("testEmployee2");
		employeeDto1.setId(2);
		employeeDtos.add(employeeDto1);
		return employeeDtos;
	}

}
