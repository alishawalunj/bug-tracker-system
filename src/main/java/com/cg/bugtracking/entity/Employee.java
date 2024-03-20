package com.cg.bugtracking.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="employees_table")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="employee_id",length=10)
	private long empId;
	
	@NotEmpty(message="employee name should not be empty")
	@Column(name = "employee_name")
	private String empName;
	
	@NotEmpty(message="employee email should not be empty")
	@Column(name = "employee_email")
	private String employeeEmail;
	
	@NotEmpty(message = "Please provide a contact")
	@Column(name = "emloyee_contact")
	private String employeeContact;
	
	@NotNull(message="Userid cannot be empty")
	@Column(name="employeeUserId")
	private String employeeUserId;
	
	@NotEmpty(message="Password cannot be empty")
	@Column(name="employee_password")
	private String employeePassword;
	
	@NotEmpty(message="Provide employee status")
	@Column(name="employee_status")
	private String empStatus;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "empId")
	private List<Bug> bugList=new ArrayList<>();
	
	
	private String role;
	


	public Employee(long empId, String empName, String employeeEmail, String employeeContact, String employeeUserId,
			String employeePassword, String empStatus, List<Bug> bugList) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.employeeEmail = employeeEmail;
		this.employeeContact = employeeContact;
		this.employeeUserId = employeeUserId;
		this.employeePassword = employeePassword;
		this.empStatus = empStatus;
		this.bugList = bugList;
		this.role="employee";
	}


	public String getRole() {
		return role;
	}


	public long getEmpId() {
		return empId;
	}


	public void setEmpId(long empId) {
		this.empId = empId;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getEmployeeEmail() {
		return employeeEmail;
	}


	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}


	public String getEmployeeContact() {
		return employeeContact;
	}


	public void setEmployeeContact(String employeeContact) {
		this.employeeContact = employeeContact;
	}


	public String getEmployeeUserId() {
		return employeeUserId;
	}


	public void setEmployeeUserId(String employeeUserId) {
		this.employeeUserId = employeeUserId;
	}


	public String getEmployeePassword() {
		return employeePassword;
	}


	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}


	public String getEmpStatus() {
		return empStatus;
	}


	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}


	public List<Bug> getBugList() {
		return bugList;
	}


	public void setBugList(List<Bug> bugList) {
		this.bugList = bugList;
	}


	public Employee() {
		super();
	}
	
	


	
}