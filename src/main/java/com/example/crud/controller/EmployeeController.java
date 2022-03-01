package com.example.crud.controller;


import com.example.crud.model.Employee;
import com.example.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

	private EmployeeService employeeService;

	@Value("${app.message}")
	private String message;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@RequestMapping(value = "/")
	public String viewHomePage(Model model) {
		return findPaginated(1, 3,model);
	}
	
	@RequestMapping(value = "/showNewEmployeeForm", method = RequestMethod.GET)
	public String showNewEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@RequestMapping(value = "/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		employeeService.sendMessage(employee.toString());
		return "redirect:/page/1/size/10";
	}
	
	@RequestMapping(value = "/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@RequestMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		Employee employee = employeeService.getEmployeeById(id);
		return "redirect:/page/1/size/10";
	}
	
	
	@RequestMapping("/page/{pageNo}/size/{pageSize}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
								@PathVariable (value = "pageSize") int pageSize,Model model) {
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize);
		List<Employee> listEmployees = page.getContent();
		String number = Integer.toString(employeeService.getAllEmployees().size());
		employeeService.sendMessage(number);
		
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}


}
