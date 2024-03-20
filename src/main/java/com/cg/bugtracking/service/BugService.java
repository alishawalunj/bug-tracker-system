package com.cg.bugtracking.service;

import java.util.List;

import com.cg.bugtracking.entity.Bug;



public interface BugService {
	public Bug createBug(Bug bug);
	public Bug updateBug(long id,Bug bug);
	public Bug getBug(long id);
	public List<Bug> getAllBugs();
	public List<Bug> getAllBugsByStatus(String status);
	public Bug deleteBug(long id);
}
