package com.cg.bugtracking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.cg.bugtracking.entity.Admin;
import com.cg.bugtracking.payload.User;
import com.cg.bugtracking.payload.BaseResponse;
import com.cg.bugtracking.service.AdminService;
import com.cg.bugtracking.service.AdminServiceImpl;
import com.cg.bugtracking.service.EmployeeService;
import com.cg.bugtracking.service.EmployeeServiceImpl;

import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alisha
 * 
 */

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/") // Map a specific request path or pattern onto a controller
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired // To establish the relationship with admin service
	private AdminService adminService;
	@Autowired // To establish the relationship with employee service
	private EmployeeService employeeService;

	/**
	 * This method is used to call the createAdmin method from the service layer.
	 * 
	 * @param newAdmin     is used to pass values of admin to the admin service
	 *                     layer
	 * @param baseresponse is used to store the object of Baseresponse class and
	 *                     invoke it's parameters
	 */
	@PostMapping("/admin/create-admin")
	@ApiOperation(value = "create an admin")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody Admin admin) {
		logger.info("Enter UserController :: method=registerAdmin");

		Admin admin1 = adminService.createAdmin(admin);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(admin1);

		logger.info("Exit UserController :: method=registerAdmin");

		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * This method is used to call the adminLogin method from the service layer.
	 * 
	 * @param str          is used to pass values of admin to the admin service
	 *                     layer
	 * @param baseresponse is used to store the object of Baseresponse class and
	 *                     invoke it's parameters
	 */
	@PostMapping("/admin/admin-login")
	@ApiOperation(value = "Admin Login")
	public ResponseEntity<?> adminLogin(@Valid @RequestBody User auser) {
		logger.info("Enter UserController :: method=admiLogin");

		String str = adminService.adminLogin(auser);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(str);

		logger.info("Exit UserController :: method=adminLogin");

		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * This method is used to call the employeeLogin method from the service layer.
	 * 
	 * @param str          is used to pass values of employee to the employee
	 *                     service layer
	 * @param baseresponse is used to store the object of Baseresponse class and
	 *                     invoke it's parameters
	 */
	@PostMapping("/employees/employee-login")
	@ApiOperation(value = "Employee Login")
	public ResponseEntity<?> employeeLogin(@Valid @RequestBody User euser) {
		logger.info("Enter UserController :: method=employeeLogin");
		String str = employeeService.employeeLogin(euser);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(str);

		logger.info("Exit UserController :: method=employeeLogin");

		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);

	}

}
