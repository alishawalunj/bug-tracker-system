package com.cg.bugtracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.bugtracking.entity.Bug;



public interface BugRepository extends JpaRepository<Bug, Long> {
	public List<Bug> findByStatus(String name);

}
