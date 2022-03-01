package com.example.crud.service;


import com.example.crud.model.Employee;
import com.example.crud.repository.EmployeeRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final RabbitTemplate rabbitTemplate;
	private final EmployeeRepository employeeRepository;

	@Value("${spring.rabbitmq.exchange}")
	private String exchange;

	@Value("${spring.rabbitmq.routingkey}")
	private String routingkey;

	public EmployeeServiceImpl(RabbitTemplate rabbitTemplate, EmployeeRepository employeeRepository) {
		this.rabbitTemplate = rabbitTemplate;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Set<Employee> getAllEmployees() {
		Set<Employee> employees=new HashSet<>();
		employeeRepository.findAll().forEach(employees::add);
		return employees;
	}

	@Override
	public Employee getEmployeeById(long id) {
		return employeeRepository.findById(id).orElse(null);
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepository.save(employee);
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


	@Override
	public void sendMessage(String message){
		rabbitTemplate.convertAndSend(exchange,routingkey, message);
	}

}
