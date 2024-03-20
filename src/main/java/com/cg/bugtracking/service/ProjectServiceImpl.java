package com.cg.bugtracking.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.cg.bugtracking.exceptions.CantAddEmployee;
import com.cg.bugtracking.exceptions.CantDeleteEmployee;
import com.cg.bugtracking.exceptions.OperationFailedException;
import com.cg.bugtracking.exceptions.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.bugtracking.entity.Employee;
import com.cg.bugtracking.entity.Project;

import com.cg.bugtracking.repository.EmployeeRepository;
import com.cg.bugtracking.repository.ProjectRepository;
import static com.cg.bugtracking.util.AppConstant.PROJECT_NOT_FOUND_CONST;
import static com.cg.bugtracking.util.AppConstant.EMPLOYEE_NOT_FOUND_CONST;
import static com.cg.bugtracking.util.AppConstant.CANT_ADD_EMPLOYEE;
import static com.cg.bugtracking.util.AppConstant.OPERATION_FAILED_CONST;
import static com.cg.bugtracking.util.AppConstant.EMPLOYEE_NOT_PART;
import static com.cg.bugtracking.util.AppConstant.EMPLOYEE_ALERADY_PART;

/**
 * Project Service Implementation
 * 
 * @author mushkan
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	/**
	 * This method is used to Create the object of type project in the database.
	 * 
	 * @param project This is the only parameter to createProject Method which is of
	 *                Project type.
	 * @return Project This Method returns the Project object which was stored in
	 *         the database.
	 * @throws This method throws OperationFailedException.
	 */
	@Override
	public Project createProject(Project project) {
		// TODO Auto-generated method stub
		logger.info("Enter ProjectServiceImpl:: method=createProject");

		Project projectObj = null;
		try {
			projectObj = projectRepository.save(project);

		} catch (Exception e) {
			throw new OperationFailedException(OPERATION_FAILED_CONST + e.getMessage());
		}
		logger.info("Exit ProjectServiceImpl:: method=createProject");

		return projectObj;
	}

	/**
	 * This method is used to get the list of All Projects
	 * 
	 * @return List<Project> This method returns the list of objects of type Project
	 *         which are stored in the database
	 */
	@Override
	public List<Project> getAllProject() {
		logger.info("Enter ProjectServiceImpl:: method=getAllProject");

		List<Project> projectList = null;
		projectList = projectRepository.findAll();
		projectList.sort(Comparator.comparing(Project::getProjectId));
		logger.info("Exit ProjectServiceImpl:: method=getAllProject");

		return projectList;
	}

	/**
	 * This method is used to get a Database entry .
	 * 
	 * @param projectId This is the only parameter to findProject Method which is of
	 *                  long type.
	 * 
	 * @return Project This returns the Project object which was fetched from the
	 *         database
	 * @throws This method throws ResourceNotFoundException.
	 */
	@Override
	public Project findProject(long projectId) {
		logger.info("Enter ProjectServiceImpl:: method=findProject");

		Optional<Project> project = projectRepository.findById(projectId);

		if (!project.isPresent())
			throw new ResourceNotFoundException(PROJECT_NOT_FOUND_CONST + projectId);
		logger.info("Exit ProjectServiceImpl:: method=findProject");

		return project.get();
	}

	/**
	 * This method is used to update a Database entry .
	 * 
	 * @param projectId This is the first parameter to updateProject Method which is
	 *                  of long type.
	 * @param project   This is the second parameter to updateProject Method which
	 *                  is of Project type.
	 * @return Project This returns the Updated Project object which is stored in
	 *         the database.
	 * @throws This method throws ResourceNotFoundException .
	 */
	@Transactional
	@Override
	public Project updateProject(long projectId, Project project) {
		logger.info("Enter ProjectServiceImpl:: method=updateProject");

		Optional<Project> projectObj = null;

		projectObj = projectRepository.findById(projectId);
		if (!projectObj.isPresent()) {
			throw new ResourceNotFoundException(PROJECT_NOT_FOUND_CONST + projectId);
		}

		projectObj.get().setProjectName(project.getProjectName());
		projectObj.get().setProjectOwner(project.getProjectOwner());
		projectObj.get().setStatus(project.getStatus());
		projectObj.get().setBugList(project.getBugList());

		logger.info("Exit ProjectServiceImpl:: method=updateProject");

		return projectObj.get();
	}

	/**
	 * This method is used to delete a Database entry using the Id of the project.
	 * 
	 * @param projectId This is the only parameter to deleteProject Method which is
	 *                  of long type.
	 * @return Project This returns the Project object which was deleted from the
	 *         database.
	 * @throws This method throws ResourceNotFoundException and
	 *              OperationFailedException.
	 */
	@Transactional
	@Override
	public Project deleteProject(long projectId) {
		logger.info("Enter ProjectServiceImpl:: method= deleteProject");

		Optional<Project> projectObj = projectRepository.findById(projectId);
		if (!projectObj.isPresent()) {
			throw new ResourceNotFoundException(PROJECT_NOT_FOUND_CONST + projectId);
		} else {
			try {
				List<Employee> members=projectObj.get().getMembers();
				projectRepository.delete(projectObj.get());
				for (Employee emp : members) 
				    emp.setEmpStatus("free");
				projectObj.get().getMembers().clear();
				projectObj.get().getBugList().clear();
			} catch (Exception e) {
				throw new OperationFailedException(OPERATION_FAILED_CONST + e.getMessage());
			}
		} 
		logger.info("Exit ProjectServiceImpl:: method= deleteProject");

		return projectObj.get();
	}

	/**
	 * This method is used to add a employee to a project.
	 * 
	 * @param projectId  This is the first parameter to addEmployeeProject Method
	 *                   which is of long type.
	 * @param employeeId This is the second parameter to addEmployeeProject Method
	 *                   which is of long type.
	 * @return Project This returns the new Project object .
	 * @throws This method throws ResourceNotFoundException and CantAddEmployee.
	 */
	@Transactional
	@Override
	public Project addEmployeeProject(long projectId, long employeeId) {
		// TODO Auto-generated method stub
		logger.info("Enter ProjectServiceImpl:: method= addEmployeeProject");

		Optional<Project> projectObj = projectRepository.findById(projectId);
		Optional<Employee> employeeObj = employeeRepository.findById(employeeId);
		if (!projectObj.isPresent()) {
			throw new ResourceNotFoundException(PROJECT_NOT_FOUND_CONST + projectId);
		}
		if (!employeeObj.isPresent()) {
			throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_CONST + employeeId);
		}
		Employee emp = employeeObj.get();
		Project proj = projectObj.get();

		if (projectObj.get().getMembers().contains(emp)) {
			throw new CantAddEmployee(EMPLOYEE_ALERADY_PART + employeeId);
		}

		if (emp.getEmpStatus().equals("Assigned")) {
			throw new CantAddEmployee(CANT_ADD_EMPLOYEE + employeeId);
		}
		emp.setEmpStatus("Assigned");
		proj.getMembers().add(emp);
		logger.info("Exit ProjectServiceImpl:: method= addEmployeeProject");

		return proj;
	}

	/**
	 * This method is used to delete a employee from a project.
	 * 
	 * @param projectId  This is the first parameter to deleteEmployeeProject Method
	 *                   which is of long type.
	 * @param employeeId This is the second parameter to deleteEmployeeProject
	 *                   Method which is of long type.
	 * @return Project This returns the new Project object .
	 * @throws This method throws ResourceNotFoundException and CantDeleteEmployee.
	 */
	@Transactional
	@Override
	public Project deleteEmployeeProject(long projectId, long employeeId) {
		// TODO Auto-generated method stub
		logger.info("Enter ProjectServiceImpl:: method=deleteEmployeeProject");

		Optional<Project> projectObj = projectRepository.findById(projectId);
		Optional<Employee> employeeObj = employeeRepository.findById(employeeId);

		if (!projectObj.isPresent()) {
			throw new ResourceNotFoundException(PROJECT_NOT_FOUND_CONST + projectId);
		}
		if (!employeeObj.isPresent()) {
			throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_CONST + employeeId);
		}
		Employee emp = employeeObj.get();
		Project proj = projectObj.get();
		if (!proj.getMembers().contains(emp)) {
			throw new CantDeleteEmployee(EMPLOYEE_NOT_PART + employeeId);
		}

		emp.getBugList().clear();
		emp.setEmpStatus("free");
		proj.getMembers().remove(emp);
		logger.info("Exit ProjectServiceImpl:: method=deleteEmployeeProject");

		return proj;
	}

}
