package com.cg.bugtracking;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.bugtracking.entity.Admin;
import com.cg.bugtracking.entity.Employee;
import com.cg.bugtracking.exceptions.LoginOperationException;
import com.cg.bugtracking.payload.User;

import com.cg.bugtracking.repository.AdminRepository;
import com.cg.bugtracking.repository.EmployeeRepository;

import com.cg.bugtracking.service.AdminServiceImpl;

import com.cg.bugtracking.service.EmployeeServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestUser {
	@Mock // Used to annotate the Object to be Mocked
	private AdminRepository adminRepository;

	@InjectMocks // Used to annotate the Object in which mocked object is injected
	private AdminServiceImpl adminService;

	@Mock // Used to annotate the Object to be Mocked
	private EmployeeRepository employeeRepository;

	@InjectMocks // Used to annotate the Object in which mocked object is injected
	private EmployeeServiceImpl employeeService;

	/**
	 * This test is sample test
	 */
	@DisplayName("Sample test")
	@Test
	void sampleTest() {
		assertTrue(true);
	}

	/**
	 * This test is written to check whether the admin is created or not using
	 * createAdminTest
	 */
	@Test
	public void createAdminTest() {
		Admin admin = new Admin();
		admin.setAdminId(101);
		admin.setAdminName("alisha");
		admin.setAdminContact("8805184513");
		admin.setAdminUserid("alwalunj");
		admin.setAdminPassword("pswd");
		given(adminRepository.save(admin)).willReturn(admin);
		Admin savedAdmin = adminService.createAdmin(admin);
		Assertions.assertThat(savedAdmin).isNotNull();
		verify(adminRepository).save(any(Admin.class));
	}

	/**
	 * This test is written to check whether the admin credentials required for
	 * logging in are working or not
	 */
	@Test
	public void adminLoginTest() {
		String str = "Log in Success!! ";
		Admin admin = new Admin(1201, "alisha", "456987", "alwalunj", "pswd");
		User auser = new User();
		auser.setUserId("alwalunj");
		auser.setUserPassword("pswd");
		given(adminRepository.findByAdmin_userid(auser.getUserId())).willReturn(admin);
		String loggedadmin = adminService.adminLogin(auser);
		verify(adminRepository, times(1)).findByAdmin_userid(auser.getUserId());
		assertEquals(str, loggedadmin);
	}

	/**
	 * This test is written to check whether the admin userId passed to adminLogin
	 * function is already registered in the admin table or not
	 */
	@Test
	public void adminLoginIdExceptionTest() {
		String expectedstr = "User not registered with this id : ";
		Admin admin = null;
		User auser = new User();
		auser.setUserId("testid");
		auser.setUserPassword("testpswd");
		given(adminRepository.findByAdmin_userid(auser.getUserId())).willReturn(admin);
		Exception exception = assertThrows(LoginOperationException.class, () -> {
			adminService.adminLogin(auser);
		});
		String actualstr = exception.getMessage();
		assertEquals(actualstr, expectedstr);
	}

	/**
	 * This test is written to check whether the admin password passed to adminLogin
	 * function is correct or not
	 */
	@Test
	public void adminLoginPswdExceptionTest() {
		String expectedstr = "Log in Failed. Enter valid password ";
		Admin admin = new Admin(1201, "alisha", "456987", "alwalunj", "pswd");
		User auser = new User();
		auser.setUserId("alwalunj");
		auser.setUserPassword("test");
		given(adminRepository.findByAdmin_userid(auser.getUserId())).willReturn(admin);
		Exception exception = assertThrows(LoginOperationException.class, () -> {
			adminService.adminLogin(auser);
		});
		String actualstr = exception.getMessage();
		assertEquals(actualstr, expectedstr);
	}

	/**
	 * This test is written to check whether the employee credentials required for
	 * logging in are working or not
	 */
	@Test
	public void employeeLoginTest() {
		String str = "Log in Success!! ";
		Employee employee=new Employee();
		employee.setEmpId(12);
		employee.setEmpName("Sam");
		employee.setEmployeeEmail("gmail");
		employee.setEmployeeContact("23234");
		employee.setEmpStatus("Free");
		employee.setEmployeeUserId("nikiid");
		employee.setEmployeePassword("nikipswd");

		User euser = new User();
		euser.setUserId("nikiid");
		euser.setUserPassword("nikipswd");
		given(employeeRepository.findByEmployeeUserId(euser.getUserId())).willReturn(employee);
		String loggedemployee = employeeService.employeeLogin(euser);
		verify(employeeRepository, times(1)).findByEmployeeUserId(euser.getUserId());
		assertEquals(str, loggedemployee);
	}

	/**
	 * This test is written to check whether the employeeuserId passed to
	 * employeeLogin function is already registered in the employee table or not
	 */
	@Test
	public void employeeLoginIdExceptionTest() {
		String expectedstr = "User not registered with this id : ";
		Employee employee=new Employee();
		employee.setEmpId(12);
		employee.setEmpName("Sam");
		employee.setEmployeeEmail("gmail");
		employee.setEmployeeContact("23234");
		employee.setEmpStatus("Free");
		employee.setEmployeeUserId("nikiid");
		employee.setEmployeePassword("pswd");
		User auser = new User();
		auser.setUserId("nicki");
		auser.setUserPassword("pswd");
		Exception exception = assertThrows(LoginOperationException.class, () -> {
			adminService.adminLogin(auser);
		});
		String actualstr = exception.getMessage();
		assertEquals(actualstr, expectedstr);
	}

	/**
	 * This test is written to check whether the employee password passed to
	 * employeeLogin function is correct or not
	 */
	@Test
	public void employeeLoginPswdExceptionTest() {
		String expectedstr = "Log in Failed. Enter valid password ";
		Employee employee=new Employee();
		employee.setEmpId(12);
		employee.setEmpName("Sam");
		employee.setEmployeeEmail("gmail");
		employee.setEmployeeContact("23234");
		employee.setEmpStatus("Free");
		employee.setEmployeeUserId("nikiid");
		employee.setEmployeePassword("23e23wqw");
	
		User auser = new User();
		auser.setUserId("nikiid");
		auser.setUserPassword("test");
		given(employeeRepository.findByEmployeeUserId(auser.getUserId())).willReturn(employee);
		Exception exception = assertThrows(LoginOperationException.class, () -> {
			employeeService.employeeLogin(auser);
		});
		String actualstr = exception.getMessage();
		assertEquals(actualstr, expectedstr);
	}
	
}
