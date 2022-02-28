package com.example.crud.service;


import com.example.crud.model.Employee;
import com.example.crud.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Set<Employee> getAllEmployees() {
		Set<Employee> employees=new HashSet<>();
		employeeRepository.findAll().forEach(employees::add);
		return employees;
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(long id) {
		return employeeRepository.findById(id).orElse(null);/*
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return employee;*/
	}

	@Override
	public void deleteEmployeeById(long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.employeeRepository.findAll(pageable);
	}
}
