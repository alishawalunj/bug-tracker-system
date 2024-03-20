package com.cg.bugtracking.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bugtracking.entity.Project;

import com.cg.bugtracking.payload.BaseResponse;

import com.cg.bugtracking.service.ProjectService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/project")
@Validated
@Api(value = "Project Controller", description = "Operations on Project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	private static Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	/**
	 * This method is used to Create the object of type project in the database.
	 * 
	 * @param project This is the only parameter to createProject Method which is of
	 *                Project type.
	 * @return ResponseEntity<?> This returns the baseResponse and the HttpStatus
	 */
	@PostMapping("/")
	@ApiOperation(value = "Create a  new Project")
	public ResponseEntity<?> createProject(@Valid @RequestBody Project project) {
		logger.info("Enter ProjectController:: method=createProject");

		Project projectObj = projectService.createProject(project);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(projectObj);
		logger.info("Exit ProjectController:: method=createProject");

		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	/**
	 * This method is used to get the list of All Projects
	 * 
	 * @return  ResponseEntity<?> This returns the baseResponse and the HttpStatus
	 */
	@GetMapping("/all")
	@ApiOperation(value = "Show All Project")
	public ResponseEntity<?> fetchAllProjects() {
		logger.info("Enter ProjectController:: method=fetchAllProjects");

		List<Project> project = projectService.getAllProject();

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(project);
		logger.info("Exit ProjectController:: method=fetchAllProjects");

		return new ResponseEntity<>(baseResponse, HttpStatus.OK);

	}

	/**
	 * This method is used to get a Database entry .
	 * 
	 * @param projectId This is the only parameter to findProject Method which is of
	 *                  long type.
	 * 
	 * @return  ResponseEntity<?> This returns the baseResponse and the HttpStatus
	 */

	@GetMapping("/{id}")
	@ApiOperation(value = "Search a project with an ID")
	public ResponseEntity<?> fetchProductDetails(@PathVariable("id") @Min(1) long id) {
		logger.info("Enter ProjectController:: method=fetchProductDetails");

		Project project = projectService.findProject(id);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(project);
		logger.info("Exit ProjectController:: method=fetchProductDetails");

		return new ResponseEntity<>(baseResponse, HttpStatus.OK);

	}

	/**
	 * This method is used to delete a Database entry using the Id of the project.
	 * 
	 * @param projectId This is the only parameter to deleteProject Method which is
	 *                  of long type.
	 * @return ResponseEntity<?> This returns the baseResponse and the HttpStatus
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a Project")
	public ResponseEntity<?> deleteProject(@PathVariable("id") @Min(1) long projectId) {
		logger.info("Enter ProjectController:: method=deleteProject");

		Project projectObj = projectService.deleteProject(projectId);

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(projectObj);
		logger.info("Exit ProjectController:: method=deleteProject");

		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	/**
	 * This method is used to add a employee to a project.
	 * 
	 * @param projectId  This is the first parameter to addEmployeeProject Method
	 *                   which is of long type.
	 * @param employeeId This is the second parameter to addEmployeeProject Method
	 *                   which is of long type.
	 * @return ResponseEntity<?> This returns the baseResponse and the HttpStatus
	 */
	@PutMapping("/Addemp/{pid}/{eid}")
	@ApiOperation(value = "Add employee to a project")
	public ResponseEntity<?> addEmployeeproject(@PathVariable("pid") @Min(1) long projectId,
			@PathVariable("eid") @Min(1) long employeeId) {
		logger.info("Enter ProjectController:: method=addEmployeeproject");

		Project updatedprojectObj = projectService.addEmployeeProject(projectId, employeeId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(updatedprojectObj);
		logger.info("Exit ProjectController:: method=addEmployeeproject");

		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	/**
	 * This method is used to delete a employee from a project.
	 * 
	 * @param projectId  This is the first parameter to deleteEmployeeProject Method
	 *                   which is of long type.
	 * @param employeeId This is the second parameter to deleteEmployeeProject
	 *                   Method which is of long type.
	 * @return ResponseEntity<?> This returns the baseResponse and the HttpStatus
	 */
	@PutMapping("/DeleteEmp/{pid}/{eid}")
	@ApiOperation(value = "delete employee from a project")
	public ResponseEntity<?> deleteEmployeeproject(@PathVariable("pid") @Min(1) long projectId,
			@PathVariable("eid") @Min(1) long employeeId) {
		logger.info("Enter ProjectController:: method=deleteEmployeeproject");

		Project updatedprojectObj = projectService.deleteEmployeeProject(projectId, employeeId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(updatedprojectObj);
		logger.info("Exit ProjectController:: method=deleteEmployeeproject");

		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	/**
	 * This method is used to update a Database entry .
	 * 
	 * @param projectId This is the first parameter to updateProject Method which is
	 *                  of long type.
	 * @param project   This is the second parameter to updateProject Method which
	 *                  is of Project type.
	 * @return ResponseEntity<?> This returns the baseResponse and the HttpStatus
	 */
	@PutMapping("/{id}")
	@ApiOperation(value = "Update a project")
	public ResponseEntity<?> updateproject(@PathVariable("id") @Min(1) long projectId,
			@Valid @RequestBody Project project) {
		logger.info("Enter ProjectController:: method=updateproject");

		Project projectObj = projectService.updateProject(projectId, project);

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(projectObj);
		logger.info("Exit ProjectController:: method=updateproject");

		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}
}
