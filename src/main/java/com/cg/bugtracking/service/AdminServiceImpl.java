package com.cg.bugtracking.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bugtracking.entity.Admin;
import com.cg.bugtracking.exceptions.LoginOperationException;
import com.cg.bugtracking.payload.User;
import com.cg.bugtracking.repository.AdminRepository;
import static com.cg.bugtracking.util.AppConstant.*;


@Service	// @Service annotation is used to mark the class as a service provider
public class AdminServiceImpl implements AdminService {// AdminService should implement all the methods present in

	@Autowired // To get a relation with Admin repository
	public AdminRepository adminRespository;

	/**
	 * This method is used to create the object of type Admin in the database.
	 * 
	 * @param newAdmin is used to pass values of admin to the repository
	 */
	@Override
	public Admin createAdmin(Admin admin) {
		// TODO Auto-generated method stub
		Admin newAdmin = new Admin();
		newAdmin = adminRespository.save(admin); // save is used to insert the records in the table
		return newAdmin;
	}

	/**
	 * @This method is used for logging into the system using id and password
	 * @param auser           This is used as a payload which holds id and password
	 *                        of admin which is passed to the method
	 * @param loginAdmin      is used as temporary variable to fetch the user
	 * @param loggedAdminpswd is used as temporary variable to store password
	 *                        retrieved from database
	 * @return str This Method returns the string describing which action has
	 *         executed
	 * @throws LoginOperationException is thrown
	 */

	@Override
	public String adminLogin(User auser) {
		// TODO Auto-generated method stub
		String str = null;
		auser.setRole("admin");
		String adminUid = auser.getUserId();
		String adminPswd = auser.getUserPassword();
		Admin loginAdmin = adminRespository.findByAdmin_userid(adminUid);
		if (loginAdmin == null) {
			throw new LoginOperationException(USER_NOT_REGISTERED+adminUid);
		} else {
			String loggedAdminpswd = loginAdmin.getAdminPassword();
			if (loggedAdminpswd.equals(adminPswd)) {
				str = LOGIN_SUCCESS;
			} else {
				throw new LoginOperationException(LOGIN_FAIL);
			}
		}
		return str;
	}

	/**
	 * This method is used to logout from the system.
	 * 
	 * @return str return the string as logged out successfully along with the
	 *         userId
	 */
	@Override
	public String adminLogout(User auser) {
		// TODO Auto-generated method stub
		String str = auser.getUserId() + LOGOUT_SUCCESS;
		return str;
	}
}
