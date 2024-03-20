package com.cg.bugtracking.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bugtracking.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	

}

