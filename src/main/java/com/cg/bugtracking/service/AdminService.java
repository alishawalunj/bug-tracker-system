package com.cg.bugtracking.service;

import com.cg.bugtracking.entity.Admin;
import com.cg.bugtracking.payload.User;


public interface AdminService { // By default all the methods present in interface are public abstract
	public Admin createAdmin(Admin admin);

	// ******************BugTracking**********************//
	/**
	 * Function Name: createAdmin(Admin admin) Input Parameters: (Admin admin)
	 * ReturnType: Admin creation Date: 25/12/2020 Author: Alisha Description:
	 * create Admin by giving the needed credentials
	 */

	public String adminLogin(User auser);

	// ******************BugTracking**********************//
	/**
	 * Function Name: adminLogin(User auser) Input Parameters: (User auser)
	 * ReturnType: String creation Date: 25/12/2020 Author: Alisha Description:
	 * login Admin by giving the needed credentials
	 */

	public String adminLogout(User auser);
	// ******************BugTracking**********************//
	/**
	 * Function Name: adminLogout(User auser) Input Parameters: (User auser)
	 * ReturnType: String creation Date: 25/12/2020 Author: Alisha Description:
	 * logout Admin by giving the needed credentials
	 */

}
