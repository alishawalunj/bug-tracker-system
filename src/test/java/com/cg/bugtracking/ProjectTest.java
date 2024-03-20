package com.cg.bugtracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.boot.test.context.SpringBootTest;
import static com.cg.bugtracking.util.AppConstant.*;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import com.cg.bugtracking.entity.Employee;
import com.cg.bugtracking.entity.Project;
import com.cg.bugtracking.exceptions.CantAddEmployee;
import com.cg.bugtracking.exceptions.CantDeleteEmployee;
import com.cg.bugtracking.exceptions.ResourceNotFoundException;
import com.cg.bugtracking.repository.EmployeeRepository;
import com.cg.bugtracking.repository.ProjectRepository;
import com.cg.bugtracking.service.ProjectServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProjectTest {
	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private ProjectServiceImpl projectService;

	private Project projectOne;
	private Project projectTwo;
	private Employee employeeOne;

// used in most test cases with a bit of difference in values
	@BeforeEach
	void create() {
		projectOne = new Project();
		projectOne.setProjectId(Long.valueOf(901));
		projectOne.setProjectName("first");
		projectOne.setProjectOwner(new String("capg"));
		projectOne.setStatus(new String("ongoing"));
		projectTwo = new Project();
		projectTwo.setProjectId(Long.valueOf(901));
		projectTwo.setProjectName("second");
		projectTwo.setProjectOwner(new String("capg"));
		projectTwo.setStatus(new String("ongoing"));
		employeeOne = new Employee();
		employeeOne.setEmployeeEmail("gmail");
		employeeOne.setEmployeeContact("09888");
		employeeOne.setEmpId(Long.valueOf(101));
		employeeOne.setEmpName(new String("raj"));

	}

// createProject test case
	@DisplayName(" Test Case for Creating a Project")
	@Test
	void testCreateProduct() {

		given(projectRepository.save(projectOne)).willReturn(projectOne);
		Project savedProject = projectService.createProject(projectOne);
		Assertions.assertThat(savedProject).isNotNull();
		verify(projectRepository).save(any(Project.class));

	}

// findProject test case
	@DisplayName("Test case for  finding a Project")
	@Test
	void testFindProjectById() {

		given(projectRepository.findById(Long.valueOf(901))).willReturn(Optional.of(projectOne));
		Project expected = projectService.findProject(Long.valueOf(901));
		Assertions.assertThat(expected).isNotNull();

	}

//getAllProject test case
	@DisplayName(" Test Case for find all Project")
	@Test
	public void testFindAllProjects() {

		List<Project> expectedProducts = Arrays.asList(projectOne);

		given(projectRepository.findAll()).willReturn(expectedProducts);

		List<Project> actualProducts = projectService.getAllProject();

		Assertions.assertThat(actualProducts).isEqualTo(expectedProducts);

	}

// updateProject test case
	@DisplayName(" Test Case for updating a Project")

	@Test
	void shouldUpdateProject() {

		
		given(projectRepository.findById(Long.valueOf(901))).willReturn(Optional.of(projectOne));

		Project expectedProject = projectService.updateProject(projectOne.getProjectId(), projectTwo);

		assertEquals(projectTwo.getProjectName(), expectedProject.getProjectName());

	}

//deleteProject test case
	@DisplayName(" Test Case for deleting a Project")

	@Test
	public void shouldBeDeleted() {

		given(projectRepository.findById(Long.valueOf(901))).willReturn(Optional.of(projectOne));
		projectService.deleteProject(Long.valueOf(901));
		projectService.deleteProject(Long.valueOf(901));
		verify(projectRepository, times(2)).delete(projectOne);

	}

//findProject test case
	@DisplayName(" Project Not Found Test case for finding project")

	@Test
	public void testFindProjectByIdWhenExceptionThrown() {

		int paramProjectId = 1000;
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			projectService.findProject(paramProjectId);
		});
		String expectedMessage = PROJECT_NOT_FOUND_CONST + paramProjectId;
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

//updateProject(projectnotfound) test case
	@DisplayName(" Project Not Found Test case for update a project")

	@Test
	void NotFoundExceptionUpdateProject() {

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			projectService.updateProject(projectOne.getProjectId(), projectOne);
		});
		String expectedMessage = PROJECT_NOT_FOUND_CONST + Long.valueOf(901);
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);

	}

	// deleteProject(projectnotfound) test case
	@DisplayName(" Project Not Found Test case for Deleting a project")

	@Test
	public void NotFoundDeleted() {

		int paramProjectId = 1000;
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			projectService.deleteProject(paramProjectId);
		});
		String expectedMessage = PROJECT_NOT_FOUND_CONST + paramProjectId;
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);
	}

	// addEmployeeProject test case
	@DisplayName(" add employee to a  project")

	@Test
	void addEmployeeProjectTest() {

		employeeOne.setEmpStatus(new String("free"));

		given(projectRepository.findById(Long.valueOf(901))).willReturn(Optional.of(projectOne));
		given(employeeRepository.findById(Long.valueOf(101))).willReturn(Optional.of(employeeOne));
		Project expected = projectService.addEmployeeProject(Long.valueOf(901), Long.valueOf(101)); //
		Assertions.assertThat(expected).isNotNull();

		assertEquals(1, expected.getMembers().size());
	}

	// deleteEmployeeProject test case
	@DisplayName(" delete employee from a  project")

	@Test
	void deleteEmployeeProjectTest() {

		employeeOne.setEmpStatus("Assigned");
		List<Employee> members = new ArrayList<>();
		members.add(employeeOne);
		projectOne.setMembers(members);
		given(projectRepository.findById(Long.valueOf(901))).willReturn(Optional.of(projectOne));
		given(employeeRepository.findById(Long.valueOf(101))).willReturn(Optional.of(employeeOne));
		System.out.print(projectOne.getMembers().size());
		Project expected = projectService.deleteEmployeeProject(projectOne.getProjectId(), employeeOne.getEmpId()); // Assertions.assertThat(expected).isNotNull();

		assertEquals(0, expected.getMembers().size());
	}

	// addEmployeeProject(aleradyPart) test case
	@DisplayName(" cant add employee to the project he is alerady part of the project")

	@Test
	void cantAddEmployeeAleradyProjectTest() {

		employeeOne.setEmpStatus(new String("free"));
		List<Employee> members = new ArrayList<>();
		members.add(employeeOne);
		projectOne.setMembers(members);
		given(projectRepository.findById(Long.valueOf(901))).willReturn(Optional.of(projectOne));
		given(employeeRepository.findById(Long.valueOf(101))).willReturn(Optional.of(employeeOne)); // Assertions.assertThat(expected).isNotNull();

		Exception exception = assertThrows(CantAddEmployee.class, () -> {
			projectService.addEmployeeProject(Long.valueOf(901), Long.valueOf(101));
		});
		String expectedMessage = EMPLOYEE_ALERADY_PART + Long.valueOf(101);
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);

	}

	// addEmployeeProject(anotherProject) test case
	@DisplayName(" cant add employee to the project he is part of other project")

	@Test
	void cantAddEmployeeOtherProjectTest() {

		employeeOne.setEmpStatus(new String("Assigned"));

		given(projectRepository.findById(Long.valueOf(901))).willReturn(Optional.of(projectOne));
		given(employeeRepository.findById(Long.valueOf(101))).willReturn(Optional.of(employeeOne)); // Assertions.assertThat(expected).isNotNull();

		Exception exception = assertThrows(CantAddEmployee.class, () -> {
			projectService.addEmployeeProject(Long.valueOf(901), Long.valueOf(101));
		});
		String expectedMessage = CANT_ADD_EMPLOYEE + Long.valueOf(101);
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);

	}

	// deleteEmployeeProject(cantDeleteEmployee) test case
	@DisplayName(" cant delete employee from project")

	@Test
	void cantDeleteEmployeeProjectTest() {

		employeeOne.setEmpStatus(new String("Assigned"));

		given(projectRepository.findById(Long.valueOf(901))).willReturn(Optional.of(projectOne));
		given(employeeRepository.findById(Long.valueOf(101))).willReturn(Optional.of(employeeOne)); // Assertions.assertThat(expected).isNotNull();

		Exception exception = assertThrows(CantDeleteEmployee.class, () -> {
			projectService.deleteEmployeeProject(Long.valueOf(901), Long.valueOf(101));
		});
		String expectedMessage = EMPLOYEE_NOT_PART + Long.valueOf(101);
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, expectedMessage);

	}

}
