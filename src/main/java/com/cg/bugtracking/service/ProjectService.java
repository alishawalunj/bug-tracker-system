package com.cg.bugtracking.service;

import java.util.List;


import com.cg.bugtracking.entity.Project;

public interface ProjectService {

	public List<Project> getAllProject();       

	public Project createProject(Project project);
	
	public Project findProject(long projectId);

	public Project deleteProject(long projectId);

	public Project updateProject(long projectId,Project project);
	
	public Project addEmployeeProject(long projectId,long employeeId);
	
	public Project deleteEmployeeProject(long projectId,long employeeId);

}
