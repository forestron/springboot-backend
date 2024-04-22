package com.smart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.smart.exception.ResourceNotFoundException;
import com.smart.model.Employee;
import com.smart.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.PutMapping;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
	}
	
	//create employee saving restapi
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return employeeRepository.save(employee);
	}
	
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeebyId(@PathVariable long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(null));
		 
		return ResponseEntity.ok(employee);
		
	}
	
	
	 @PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id,  @RequestBody Employee employeedetails)
	{
			Employee employee=employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(null));
			employee.setFirstName(employeedetails.getFirstName());
			employee.setLastName(employeedetails.getLastName());
			employee.setEmailId(employeedetails.getEmailId());
			
			Employee updatedEmployee=employeeRepository.save(employee);
			return ResponseEntity.ok(updatedEmployee);

		 
	}
	 
	 @DeleteMapping("/employees/{id}")
		public ResponseEntity<Map<String, Boolean>> DeleteEmployee(@PathVariable long id)
		{
				Employee employee=employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(null));
				
				employeeRepository.delete(employee);
				Map<String, Boolean> response=new HashMap<>();
				response.put("DELETED", Boolean.TRUE);
				return ResponseEntity.ok(response);
			 
		}
	
	 
	 
	
}
