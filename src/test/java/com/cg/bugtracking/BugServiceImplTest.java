package com.cg.bugtracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.bugtracking.entity.Bug;
import com.cg.bugtracking.exceptions.ResourceNotFoundException;
import com.cg.bugtracking.repository.BugRepository;
import com.cg.bugtracking.service.BugServiceImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static com.cg.bugtracking.util.AppConstant.BUG_NOT_FOUND_CONST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BugServiceImplTest {

	@Mock
	private BugRepository bugRepository;

	@InjectMocks
	private BugServiceImpl bugService;
	

	@DisplayName("Sample test")
	@Test
	void sampleTest() {
		assertTrue(true);
	}

	@Test
	void testCreateBug() {
		final long bugId = 100;
		Bug bug = createBug(bugId);
		given(bugRepository.save(bug)).willReturn(bug);
		Bug savedBug = bugService.createBug(bug);
		Assertions.assertThat(savedBug).isNotNull();
		verify(bugRepository).save(any(Bug.class));

	}

	@Test
	void testFindBugById() {
		final long bugId = 100;
		Bug bug = createBug(bugId);
		given(bugRepository.findById(bugId)).willReturn(Optional.of(bug));
		final Bug expected = bugService.getBug(bugId);
		Assertions.assertThat(expected).isNotNull();

	}

	@Test
	public void shouldDeleteBug() {
		final long bugId = 100;
		Bug bug = createBug(bugId);
		given(bugRepository.findById(bugId)).willReturn(Optional.of(bug));
		bugService.deleteBug(bugId);
		bugService.deleteBug(bugId);
		verify(bugRepository, times(2)).delete(bug);

	}

	@Test
	public void testFindAllBugs() {
		final long bugId = 100;
		Bug bug = createBug(bugId);
		List<Bug> expectedBugs = Arrays.asList(bug);
		given(bugRepository.findAll()).willReturn(expectedBugs);
		List<Bug> actualBugs = bugService.getAllBugs();
		Assertions.assertThat(actualBugs).isEqualTo(expectedBugs);

	}

	@Test
	public void testFindAllBugsByStatus() {
		final long bugId = 100;
		Bug bug = createBug(bugId);
		List<Bug> expectedBugs = Arrays.asList(bug);
		given(bugRepository.findByStatus("fixed")).willReturn(expectedBugs);
		List<Bug> actualBugs = bugService.getAllBugsByStatus("fixed");
		Assertions.assertThat(actualBugs).isEqualTo(expectedBugs);

	}

	@Test
	void shouldUpdateBug() {
		final long bugId = 100;
		Bug bug = createBug(bugId);
		given(bugRepository.save(bug)).willReturn(bug);
		given(bugRepository.findById(bugId)).willReturn(Optional.of(bug));
		Bug expectedBug = bugService.updateBug(bug.getBugId(), bug);
		Assertions.assertThat(expectedBug).isNotNull();

	}

	@Test
	public void testFindBugByIdWhenExceptionThrown() {
		final long bugId = 1000;
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			bugService.getBug(bugId);
		});
		String expectedMessage = BUG_NOT_FOUND_CONST + bugId;
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	@Test
	void NotFoundExceptionUpdateProject() {
		final long bugId = 100;
		Bug bug = createBug(bugId);
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			bugService.updateBug(bug.getBugId(), bug);
		});
		String expectedMessage = BUG_NOT_FOUND_CONST + bugId;
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);

	}

	@Test
	public void NotFoundDeleted() {

		final long bugId = 1000;
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			bugService.deleteBug(bugId);
		});
		String expectedMessage = BUG_NOT_FOUND_CONST + bugId;
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	Bug createBug(long bugId) {
		Bug bug = new Bug();
		bug.setBugId(bugId);
		bug.setPriority("High");
		bug.setStatus("Fixed");
		bug.setType("Functional defects");
		return bug;
	}

}
