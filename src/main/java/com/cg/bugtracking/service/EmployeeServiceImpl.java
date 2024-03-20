package com.cg.bugtracking.service;

import static com.cg.bugtracking.util.AppConstant.EMPLOYEE_NOT_FOUND_CONST;
import static com.cg.bugtracking.util.AppConstant.OPERATION_FAILED_CONST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.bugtracking.entity.Employee;
import com.cg.bugtracking.exceptions.LoginOperationException;
import com.cg.bugtracking.exceptions.OperationFailedException;
import com.cg.bugtracking.exceptions.ResourceNotFoundException;
import static com.cg.bugtracking.util.AppConstant.*;
import com.cg.bugtracking.payload.User;
import com.cg.bugtracking.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	private static Logger logger=LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	/**
	 * This method is used to create employee to the database.
	 * 
	 * @param employee This is the argument to createEmployee method
	 * @exception OperationException On input employee is null.
	 * @return object of created employee
	 */	
	@Transactional
	@Override
	public Employee createEmployee(Employee emp) {
		logger.info("Enter EmployeeController :: method=createEmployee");
		
		Employee employeeObj = null;
		try {
			employeeObj = employeeRepository.save(emp);

		} catch (Exception e) {
			throw new OperationFailedException(OPERATION_FAILED_CONST+ e.getMessage());
		}
		
		logger.info("Exit EmployeeController :: method=createEmployee");
		return employeeObj;
	}
	
	
	/**
	 * This method is used to update employee to the database.
	 * 
	 * @param long and employee This is the argument to updateProduct method
	 * @exception ResourceNotFoundException and OperationException On input employee id or employee object is null.
	 * @return Updated employee object
	 */
	@Transactional
	@Override
	public Employee updateEmployee(long id, Employee employee) {
		logger.info("Enter EmployeeController :: method=updateEmployee");
		
		Optional<Employee> employeeObj = null;
		Employee updatedEmployee = null;
		employeeObj = employeeRepository.findById(id);
		if (!employeeObj.isPresent()) {
			throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_CONST + id);
		} else {
			
			employeeObj.get().setEmpName(employee.getEmpName());
			employeeObj.get().setEmployeeEmail(employee.getEmployeeEmail());
			employeeObj.get().setBugList(employee.getBugList());
			employeeObj.get().setEmployeeContact(employee.getEmployeeContact());
			employeeObj.get().setEmpStatus(employee.getEmpStatus());

			try {
				updatedEmployee = employeeRepository.save(employeeObj.get());
			} catch (Exception e) {
				throw new OperationFailedException(OPERATION_FAILED_CONST + e.getMessage());
			}

		}
		
		logger.info("Exit EmployeeController :: method=updateEmployee");
		return updatedEmployee;
	}

	
	/**
	 * This method is used to delete employee to the database.
	 * 
	 * @param long This is the argument to deleteEmployee method
	 * @exception RsourceNotFoundException and OperationException On input employee id is null.
	 * @return object of deleted employee
	 */
	@Override
	public Employee deleteEmployee(long id) {
		logger.info("Enter EmployeeController :: method=deleteEmployee");
		
		Optional<Employee> employeeObj = employeeRepository.findById(id);
		if (!employeeObj.isPresent()) {
			
			throw new ResourceNotFoundException("Employee not found for this id: " + id);
			
		}
		
		else {
			try {
				employeeRepository.delete(employeeObj.get());
			}catch(Exception e) {
				throw new OperationFailedException("Delete operation failed"+e.getMessage());
			}			
		}
		
		logger.info("Exit EmployeeController :: method=deleteEmployee");
		return employeeObj.get();
	}

	
	/**
	 * This method is used to get list of employee from the database.
	 * 
	 * @param  There is no argument to getAllEmployee method
	 * @return List<Employee>
	 */
	@Override
	public Employee getEmployee(long id) {
		logger.info("Enter EmployeeController :: method=getEmployee");
		
		Optional<Employee> employee = employeeRepository.findById(id);

		if (!employee.isPresent())
			throw new ResourceNotFoundException("Employee not found for this id: " + id);
		
		logger.info("Exit EmployeeController :: method=getEmployee");
		return employee.get();
	}
	
	
	/**
	 * This method is used to get list of employee from the database.
	 * 
	 * @param  There is no argument to getAllEmployee method
	 * @return List<Employee>
	 */
	@Override
	public List<Employee> getAllEmployees() {
		logger.info("Enter EmployeeController :: method=getAllEmployees");
		logger.info("Exit EmployeeController :: method=getAllEmployees");
		return employeeRepository.findAll();
	}
	
	/**
	 * @This method is used for logging into the system using id and password
	 * @param euser              This is used as a payload which holds id and
	 *                           password of employee which is passed to the method
	 * @param loginEmployee      is used as temporary variable to fetch the user
	 * @param loggedEmployeepswd is used as temporary variable to store password
	 *                           retrieved from database
	 * @return str This Method returns the string describing which action has
	 *         executed
	 * @throws LoginOperationException is thrown
	 */

	@Override
	public String employeeLogin(User euser) {
		// TODO Auto-generated method stub
		String str = null;
		String employeeUid = euser.getUserId();
		euser.setRole("employee");
		Employee loginEmployee = employeeRepository.findByEmployeeUserId(employeeUid);
		
		if (loginEmployee == null) {
			throw new LoginOperationException(USER_NOT_REGISTERED+employeeUid);
		} else {

			String loggedEmployeepswd = loginEmployee.getEmployeePassword();
			if (loggedEmployeepswd.equals(euser.getUserPassword())) {
				str = LOGIN_SUCCESS;
			} else {
				throw new LoginOperationException(LOGIN_FAIL);
			}
		}
		return str;
	}



	

}
