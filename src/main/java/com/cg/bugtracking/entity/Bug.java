package com.cg.bugtracking.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="bug_table")
public class Bug {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="bug_id",length=10)
	private long bugId;
	
	@Column(name = "bug_desc")
	private String bugDesc;
	
	@NotEmpty(message="Bug status should not be empty")
	@Column(name = "bug_status")
	private String status;
	
	@Column(name = "start_date")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate endDate;
	
	@Column(name = "assignee")
	private String assignee;
	
	@NotEmpty(message="Bug type should not be empty")
	@Column(name = "bug_type")
	private String type;
	
	@NotEmpty(message="Bug priority should not be empty")
	@Column(name = "priority")
	private String priority;

	//Bug
	public long getBugId() {
		return bugId;
	}

	public void setBugId(long bugId) {
		this.bugId = bugId;
	}

	//Bug Description
	public String getBugDesc() {
		return bugDesc;
	}

	public void setBugDesc(String bugDesc) {
		this.bugDesc = bugDesc;
	}

	//Bug status
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	//Start date
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	//End date
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	//Assignee
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	//Bug Type
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	//Bug priority
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	//Constructors
	
	public Bug() {
		super();
	}
	

	

}
