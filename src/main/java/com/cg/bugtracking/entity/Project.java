package com.cg.bugtracking.entity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

//Project entity has Mapping with Employee and Bug entity
@Entity
@Table(name = "project_tbl")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "proj_id")
	private long projectId;
	@NotEmpty(message = "Please provide a Project Owner")
	@Column(name = "project_owner")
	private String projectOwner;
	@NotEmpty(message = "Please provide a Project Name")
	@Column(name = "project_name")
	private String projectName;
	@NotEmpty(message = "Please provide Project Status")
	@Column(name = "proj_status")
	private String status;

	// Mapping with with Employee entity (one to many)
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "proj_id")
	private List<Employee> members = new ArrayList<>();

	// Mapping with Bug entity(one to many)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "proj_id")
	private List<Bug> bugList = new ArrayList<>();

    // Project Id
	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

    // Project Owner Info
	public String getProjectOwner() {
		return projectOwner;
	}

	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}

    //Name of Project
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

    // Project Status
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Members i.e Set of Employees working on the project
	public List<Employee> getMembers() {
		return members;
	}

	public void setMembers(List<Employee> members) {
		this.members = members;
	}

	// Project Bug List
	public List<Bug> getBugList() {
		return bugList;
	}

	public void setBugList(List<Bug> bugList) {
		this.bugList = bugList;
	}

}
