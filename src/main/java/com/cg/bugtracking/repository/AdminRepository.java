package com.cg.bugtracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.bugtracking.entity.Admin;

/**
 * @author Alisha
 * 
 */
@Repository		//This annotation is used to represent the Repository Class
public interface AdminRepository extends JpaRepository<Admin,Integer>{//All the methods present in JPARepository can be used by AdminRepository
	/**
	 * This method is used to create the query to extract object
	 * from the admin table using the userId 
	 */
	
	@Query("Select a from Admin a where adminUserid=:userId")
	Admin findByAdmin_userid(@Param("userId") String userId);

}
