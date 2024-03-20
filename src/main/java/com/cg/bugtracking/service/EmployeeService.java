package com.cg.bugtracking.service;

import java.util.List;

import com.cg.bugtracking.entity.Employee;

import com.cg.bugtracking.payload.User;

public interface EmployeeService {
	public Employee createEmployee(Employee emp);
	public Employee updateEmployee(long id,Employee employee);
	public Employee deleteEmployee(long id);
	public Employee getEmployee(long id);
	public  List<Employee> getAllEmployees();
	public String employeeLogin(User euser);
}
