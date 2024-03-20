package com.cg.bugtracking.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.cg.bugtracking.entity.Project;
import com.cg.bugtracking.payload.BaseResponse;
import com.cg.bugtracking.service.BugService;
import com.cg.bugtracking.service.EmployeeService;
import com.cg.bugtracking.service.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/bug")
@Api(value="Bug Controller", description="Operations on Bug")
public class BugController {
	
	@Autowired
	private BugService bugService;	
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EmployeeService employeeService;
	
	private static Logger logger=LoggerFactory.getLogger(BugController.class);
	
	/**
	 * This method is used to store the object of type bug in the database
	 * 
	 * @param bug This is the parameter for the bug Object
	 * @return ResponseEntity<?> This returns the object which is stored in database
	 */
	@PostMapping("/")
	@ApiOperation(value = "Add a bug")
	public ResponseEntity<?> createBug(@Valid @RequestBody Bug bug){
		
		logger.info("Enter BugController :: method=createBug");
		Bug bugObj = bugService.createBug(bug);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(bugObj);		
		logger.info("Exit BugController :: method=createBug");
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}
	
	/**
	 * This method is used to assign a bug to the project
	 * 
	 * @param ProjectId This is the parameter for primary key of project object
	 * @param bugId This is the parameter for primary key of bug object
	 * @return ResponseEntity<?> This returns the object which is stored in database
	 */
	@PutMapping("/assign-project/{pid}/{bid}")
	@ApiOperation(value = "Assign a bug to project")
	public ResponseEntity<?> assignBugToProject(@PathVariable("pid") long projectId,@PathVariable("bid") long bugId) {
		
		logger.info("Enter BugController :: method=assignBugToProject");
		Bug bug=bugService.	getBug(bugId);
		Project project=projectService.findProject(projectId);
		project.getBugList().add(bug);
		Project projectObj=projectService.updateProject(projectId, project);
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(projectObj);	
		logger.info("Exit BugController :: method=assignBugToProject");
		
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);				
	}
	
	/**
	 * This method is used to assign a bug to an employee
	 * 
	 * @param ProjectId This is the parameter for primary key of project object
	 * @param employeeId This is the parameter for primary key of employee object
	 * @param bugId This is the parameter for primary key of bug object
	 * @return ResponseEntity<?> This returns the object which is stored in database
	 */
	@PutMapping("/assign-employee/{pid}/{eid}/{bid}")
	@ApiOperation(value = "Assign a bug to employee")
	public ResponseEntity<?> assignBugToEmployee(@PathVariable("pid") long projectId,@PathVariable("eid") long employeeId,@PathVariable("bid") long bugId) {
		
		logger.info("Enter BugController :: method=assignBugToEmployee");
		Project project=projectService.findProject(projectId);
		Employee employee=employeeService.getEmployee(employeeId);
		Bug bug=bugService.getBug(bugId);
		BaseResponse baseResponse = new BaseResponse();
		
		//Checking if the bug and employee is part of project
		if(project.getMembers().contains(employee)&&project.getBugList().contains(bug)) {
			bug.setAssignee(employee.getEmpName());
			employee.getBugList().add(bug);
			Employee employeeObj=employeeService.updateEmployee(employeeId, employee);
			baseResponse.setStatusCode(1);
			baseResponse.setResponse(employeeObj);	
			logger.info("Exit BugController :: method=assignBugToEmployee");
			return new ResponseEntity<>(baseResponse, HttpStatus.OK);	
		}
		else {
			baseResponse.setStatusCode(-1);
			baseResponse.setResponse("Bug or employee is not associated with project");
			logger.info("Exit BugController :: method=assignBugToEmployee");
			return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);	
		}
					
	}

	/**
	 * This method is used to get the object of type bug from the database
	 * 
	 * @param id This is the parameter for the primary key of the object
	 * @return ResponseEntity<?> This returns the object which is stored in database
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Search a bug with an ID",response = Bug.class)
	public ResponseEntity<?> getBug(@PathVariable("id") long bugId) {
	
		logger.info("Enter BugController :: method=getBug");
		Bug	bugObj = bugService.getBug(bugId);	
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(bugObj);	
		logger.info("Exit BugController :: method=getBug");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);		
		
	}
	
	/**
	 * This method is used to update the object of type bug in the database
	 * 
	 * @param bugId  This is the parameter for the primary key of the object
	 * @param bug This is the parameter for the bug Object
	 * @return ResponseEntity<?> This returns the object which is stored in database
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Update a bug")
	public ResponseEntity<?> updateBug(@PathVariable("id") long bugId,@Valid @RequestBody Bug bug) {
			
		logger.info("Enter BugController :: method=updateBug");
		Bug bugObj = bugService.updateBug(bugId,bug);	
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(bugObj);	
		logger.info("Exit BugController :: method=updateBug");
		
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);				
	}
	
	/**
	 * This method is used to delete the object of type bug from the database
	 * 
	 * @param id This is the parameter for the primary key of the object
	 * @return	ResponseEntity<?> This returns the object of type Bug which is deleted from the
	 *         database
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a bug")
	public ResponseEntity<?> deleteBug(@PathVariable("id") long bugId) {
		
		logger.info("Enter BugController :: method=deleteBug");
		Bug bugObj = bugService.deleteBug(bugId);
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(bugObj);	
		logger.info("Exit BugController :: method=deleteBug");
		
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);			
	}
	
	/**
	 * This method is used to get the list of objects of type bug from the database
	 * 
	 * @return ResponseEntity<?> This returns the list of objects which is stored in
	 *         database
	 */
	@GetMapping("/all")
	@ApiOperation(value = "Show all bugs")
	public ResponseEntity<?> getAllBugs() {
		
		logger.info("Enter BugController :: method=getAllBugs");
		List<Bug> bugs = bugService.getAllBugs();	
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(bugs);	
		logger.info("Exit BugController :: method=getAllBugs");
		
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);		
		
	}
	
	/**
	 * This method is used to delete the object of type bug from the database
	 * 
	 * @param id This is the parameter for the primary key of the object
	 * @return ResponseEntity<?> This returns the object of type Bug which is deleted from the
	 *         database
	 */
	@GetMapping("/getby/{status}")
	@ApiOperation(value = "Search a bug with status",response = Bug.class)
	public ResponseEntity<?> getAllBugsByStatus(@PathVariable("status") String status) {
		
		logger.info("Enter BugController :: method=getAllBugsByStatus");
		List<Bug> bugs = bugService.getAllBugsByStatus(status);
		
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(bugs);	
		logger.info("Exit BugController :: method=getAllBugsByStatus");
		
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);		
		
	}
	
}
