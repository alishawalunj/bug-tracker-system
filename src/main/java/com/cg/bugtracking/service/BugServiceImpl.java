package com.cg.bugtracking.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bugtracking.entity.Bug;
import com.cg.bugtracking.exceptions.OperationFailedException;
import com.cg.bugtracking.exceptions.ResourceNotFoundException;
import com.cg.bugtracking.repository.BugRepository;

import static com.cg.bugtracking.util.AppConstant.OPERATION_FAILED_CONST;
import static com.cg.bugtracking.util.AppConstant.BUG_NOT_FOUND_CONST;

@Service
public class BugServiceImpl implements BugService {

	@Autowired
	private BugRepository bugRepository;

	private static Logger logger=LoggerFactory.getLogger(BugServiceImpl.class);
	
	/**
	 * This method is used to store the object of type bug in the database
	 * 
	 * @param bug This is the parameter for the bug Object
	 * @return Bug This returns the object which is stored in database
	 */
	@Transactional
	@Override
	public Bug createBug(Bug bug) {
		logger.info("Enter BugServiceImpl:: method=createBug");
		Bug bugObj = null;
		try {
			bugObj = bugRepository.save(bug);

		} catch (Exception e) {
			throw new OperationFailedException(OPERATION_FAILED_CONST + e.getMessage());
		}
		logger.info("Exit BugServiceImpl:: method=createBug");
		return bugObj;
	}

	
	/**
	 * This method is used to update the object of type bug in the database
	 * 
	 * @param id  This is the parameter for the primary key of the object
	 * @param bug This is the parameter for the bug Object
	 * @return Bug This returns the object which is stored in database
	 */
	@Transactional
	@Override
	public Bug updateBug(long id, Bug bug) {
		logger.info("Enter BugServiceImpl:: method=updateBug");
		Optional<Bug> bugObj = null;
		Bug updatedBug = null;
		bugObj = bugRepository.findById(id);
		if (!bugObj.isPresent()) {
			throw new ResourceNotFoundException(BUG_NOT_FOUND_CONST + bug.getBugId());
		} else {
			
			bugObj.get().setStatus(bug.getStatus());
			bugObj.get().setPriority(bug.getPriority());
			bugObj.get().setAssignee(bug.getAssignee());
			bugObj.get().setBugDesc(bug.getBugDesc());
			bugObj.get().setEndDate(bug.getEndDate());
			bugObj.get().setStartDate(bug.getStartDate());
			bugObj.get().setType(bug.getType());
			try {
				updatedBug = bugRepository.save(bugObj.get());
			} catch (Exception e) {
				throw new OperationFailedException(OPERATION_FAILED_CONST + e.getMessage());
			}

		}
		logger.info("Exit BugServiceImpl:: method=updateBug");
		return updatedBug;
	}

	/**
	 * This method is used to get the object of type bug from the database
	 * 
	 * @param id This is the parameter for the primary key of the object
	 * @return Bug This returns the object which is stored in database
	 */
	@Override
	public Bug getBug(long id) {
		logger.info("Enter BugServiceImpl:: method=getBug");
		Optional<Bug> bug = bugRepository.findById(id);
		
		if (!bug.isPresent())
			throw new ResourceNotFoundException(BUG_NOT_FOUND_CONST + id);

		logger.info("Exit BugServiceImpl:: method=getBug");
		return bug.get();
	}

	/**
	 * This method is used to delete the object of type bug from the database
	 * 
	 * @param id This is the parameter for the primary key of the object
	 * @return Bug This returns the object of type Bug which is deleted from the
	 *         database
	 */
	@Override
	public Bug deleteBug(long id) {
		logger.info("Enter BugServiceImpl:: method=deleteBug");
		Optional<Bug> bugObj = bugRepository.findById(id);
		if (!bugObj.isPresent()) {
			throw new ResourceNotFoundException(BUG_NOT_FOUND_CONST + id);
		} else {
			try {
				bugRepository.delete(bugObj.get());
			}catch(Exception e) {
				throw new OperationFailedException(OPERATION_FAILED_CONST +e.getMessage());
			}			
		}
		logger.info("Exit BugServiceImpl:: method=deleteBug");
		return bugObj.get();
	}
	
	/**
	 * This method is used to get the list of objects of type bug from the database
	 * 
	 * @return List<Bug> This returns the list of objects which is stored in
	 *         database
	 */
	@Override
	public List<Bug> getAllBugs() {
		logger.info("Enter BugServiceImpl:: method=getAllBugs");
		logger.info("Exit BugServiceImpl:: method=getAllBugs");
		return bugRepository.findAll();
	}

	/**
	 * This method is used to get the list of objects of type bug from the database
	 * with status mentioned
	 * 
	 * @param status This is the parameter for selecting the object with status
	 * @return List<Bug>This returns the list of Bug objects which is stored in
	 *         database with a specific status
	 */
	@Override
	public List<Bug> getAllBugsByStatus(String status) {
		logger.info("Enter BugServiceImpl:: method=getAllBugsByStatus");
		logger.info("Exit BugServiceImpl:: method=getAllBugsByStatus");
		return bugRepository.findByStatus(status);
	}



}
