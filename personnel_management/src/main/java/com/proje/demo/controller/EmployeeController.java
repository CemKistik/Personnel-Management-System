package com.proje.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proje.demo.PageSession;
import com.proje.demo.model.Employee;
import com.proje.demo.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService service;

	
	@RequestMapping("home")
	public String welcome(
			@RequestParam(required=false) String field,
			@RequestParam(required=false) String dir,
			Model model) {
		
		model.addAttribute("employees", service.getEmployees());
		model.addAttribute("page", service.getPageNumber());
		model.addAttribute("fields", service.getFields());
		model.addAttribute("dirs", service.getDirs());
		model.addAttribute("selectedField", service.getSelectedField());
		model.addAttribute("selectedDir", service.getSelectedDir());
		
		return "home";
	}
	
	@PostMapping("addEmployee")
	public String addEmployee(Employee employee) {
		
		service.addEmployee(employee);
		return "redirect:home";
	}
	
	@PostMapping("updateEmployee")
	public String updateEmployee(
			Integer id,
			String name,
			String surname,
			String email,
			String date,
			String salary,
			String department,
			String gender,
			String age,
			String position,
			String manager,
			String action) {
		
		if(action.equals("update"))
			service.updateEmployee(id, name, surname, email, date, salary, department, gender, age, position, manager);
		else
			service.deleteEmployee(id);

		return "redirect:home";
	}
	
	@PostMapping("sortEmployee")
	public String sortEmployee(String field, String dir, RedirectAttributes redirectAttributes) {
		service.switchSort(field, dir);
		return "redirect:home";
	}
	
	@PostMapping("switchPage")
	public String switchPage(String action) {
		service.switchPage(action);
		return "redirect:home";
	}
}
