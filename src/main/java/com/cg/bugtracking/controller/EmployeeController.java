package com.cg.bugtracking.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bugtracking.entity.Bug;
import com.cg.bugtracking.entity.Employee;
import com.cg.bugtracking.payload.BaseResponse;
import com.cg.bugtracking.service.BugService;
import com.cg.bugtracking.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employee")
@Api(value="Employee Controller", description="Operations on employee")
public class EmployeeController {
	
	private static Logger logger=LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;	
	
	@Autowired
	private BugService bugService;
	
	/**
	 * This method is used to create employee to the database.
	 * 
	 * @param employee This is the argument to createEmployee method
	 * @return object of created employee
	 */
	@PostMapping("/")
	@ApiOperation(value = "Add an employee")
	public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee){
		logger.info("Enter EmployeeController :: method=createEmployee");
		
		Employee employeeObj = employeeService.createEmployee(employee);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(employeeObj);	
		
		logger.info("Exit EmployeeController :: method=createEmployee");
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}
	
	/**
	 * This method is used to get details of employee from the database.
	 * 
	 * @param long This is the argument to getEmployee method
	 * @return object of employee for given id.
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Search an employee with an ID",response = Employee.class)
	public ResponseEntity<?> getEmployee(@PathVariable("id") @Min(1) long empId) {
		logger.info("Enter EmployeeController :: method=getEmployee");
		
		Employee employeeObj = employeeService.getEmployee(empId);	
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(employeeObj);	
		
		logger.info("Exit EmployeeController :: method=getEmployee");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);		
		
	}
	
	/**
	 * This method is used to update employee to the database.
	 * 
	 * @param long and employee This is the argument to updateProduct method
	 * @return Updated employee object
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Update an employee")
	public ResponseEntity<?> updateEmployee(@PathVariable("id") @Min(1) long empId,@Valid @RequestBody Employee employee) {
		logger.info("Enter EmployeeController :: method=updateEmployee");
		Employee employeeObj = employeeService.updateEmployee(empId,employee);	
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(employeeObj);	
		
		logger.info("Exit EmployeeController :: method=updateEmployee");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);				
	}
	
	/**
	 * This method is used to delete employee to the database.
	 * 
	 * @param long This is the argument to deleteEmployee method
	 * @return object of deleted employee
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete an employee")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") @Min(1) long empId) {
		logger.info("Enter EmployeeController :: method=deleteEmployee");
		Employee employeeObj = employeeService.deleteEmployee(empId);
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(employeeObj);	
		
		logger.info("Exit EmployeeController :: method=deleteEmployee");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);			
	}
	
	/**
	 * This method is used to get list of employee from the database.
	 * 
	 * @param  There is no argument to getAllEmployee method
	 * @return List<Employee>
	 */
	@GetMapping("/all")
	@ApiOperation(value = "Show all employee")
	public ResponseEntity<?> getAllEmployees() {
		logger.info("Enter EmployeeController :: method=getAllEmployees");
		List<Employee> Employees = employeeService.getAllEmployees();	
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(Employees);	
		
		logger.info("Exit EmployeeController :: method=getAllEmployees");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);		
		
	}
	

	/**
	 * This method is used to get updated employee after deleting bug.
	 * 
	 * @param  Here are the arguments are bugId and employeeId
	 * @return Employee object.
	 */
	@PutMapping("/remove-bug/{eid}/{bid}")
	@ApiOperation(value = "remove a bug from employee")
	public ResponseEntity<?> removeBugFromEmployee(@PathVariable("eid") long employeeId,@PathVariable("bid") long bugId) {
		Bug bug=bugService.getBug(bugId);
		Employee employee=employeeService.getEmployee(employeeId);
		BaseResponse baseResponse = new BaseResponse();
		if(employee.getBugList().contains(bug)) {
			bug.setAssignee(null);
			employee.getBugList().remove(bug);
			Employee employeeObj=employeeService.updateEmployee(employeeId, employee);
			baseResponse.setStatusCode(1);
			baseResponse.setResponse(employeeObj);	
			return new ResponseEntity<>(baseResponse, HttpStatus.OK);
		}
		else {
			baseResponse.setStatusCode(1);
			baseResponse.setResponse("Employee is not assigned with bugId:"+bugId);
			return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
			
		}
	}
				
}
	
	
	
