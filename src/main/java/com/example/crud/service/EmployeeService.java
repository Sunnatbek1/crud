package com.example.crud.service;

import com.example.crud.model.Employee;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface EmployeeService {
	Set<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	void deleteEmployeeById(long id);
	Page<Employee> findPaginated(int pageNo, int pageSize);
}
