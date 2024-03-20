package com.cg.bugtracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.bugtracking.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {//All the methods present in JPARepository can be used by EmployeeRepository
	/**
	 * This method is used to create the query to extract object
	 * from the employee table using the userId 
	 */
	
	@Query("Select e from Employee e where employeeUserId=:userId")
	public Employee findByEmployeeUserId(@Param("userId") String userId);
}

