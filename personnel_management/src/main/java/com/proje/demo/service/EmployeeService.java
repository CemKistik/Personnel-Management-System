package com.proje.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.proje.demo.PageSession;
import com.proje.demo.model.Employee;
import com.proje.demo.repository.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo repo;
	@Autowired
	PageSession page;
	List<String> fields;
	List<String> dirs;

	public EmployeeService() {
		fields = new ArrayList<>(Arrays.asList("name", "surname", "email", "date", "salary", "department", "salary", "age","position", "manager"));
		dirs = new ArrayList<>(Arrays.asList("asc", "desc"));
	}

	public List<Employee> getEmployees() {
		Pageable pageable;
		int size = 10;
		String field = page.getField();
		String dir = page.getDir();
		
		if(field == null)
			pageable = PageRequest.of(page.getPageNumber(), size, Sort.by("name").ascending());
		else if(dir.equals("asc"))
			pageable = PageRequest.of(page.getPageNumber(), size, Sort.by(field).ascending());
		else
			pageable = PageRequest.of(page.getPageNumber(), size, Sort.by(field).descending());
		
		Page<Employee> employees = repo.findAll(pageable);
		
		page.setMaxPage(employees.getTotalPages());
		
		return employees.getContent();
	}
	
	public void addEmployee(Employee employee) {
		repo.save(employee);
	}
	
	public void deleteEmployee(int id) {
		Employee employee = repo.findById(id).get();
		repo.delete(employee);
	}
	
	public void updateEmployee(
			int id,
			String name,
			String position,
			String surname,
			String email,
			String date,
			String salary,
			String department,
			String gender,
			String age,
			String manager) {
		Employee employee = repo.findById(id).get();
		employee.setName(name);
		employee.setSurname(surname);
		employee.setEmail(email);
		employee.setDate(date);
		employee.setAge(age);
		employee.setDepartment(department);
		employee.setGender(gender);
		employee.setSalary(salary);
		employee.setManager(manager);
		employee.setPosition(position);
		repo.save(employee);
	}
	
	public int getPageNumber() {
		return page.getPageNumber()+1;
	}
	
	public void switchPage(String action) {
		int curPage = page.getPageNumber();
		if(action.equals("down")) {
			if(curPage == 0)
				return;
			else
				page.setPageNumber(curPage-1);
		}
		else {
			if(curPage == page.getMaxPage()-1)
				return;
			else
				page.setPageNumber(curPage+1);
		}
	}
	
	public void switchSort(String field, String dir) {
		page.setField(field);
		page.setDir(dir);
	}
	
	public List<String> getFields() {
		return fields;
	}
	
	public List<String> getDirs() {
		return dirs;
	}
	
	public String getSelectedField() {
		return page.getField();
	}
	
	public String getSelectedDir() {
		return page.getDir();
	}
}
