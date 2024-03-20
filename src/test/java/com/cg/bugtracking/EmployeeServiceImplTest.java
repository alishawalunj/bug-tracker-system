package com.cg.bugtracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.bugtracking.entity.Employee;
import com.cg.bugtracking.exceptions.ResourceNotFoundException;
import com.cg.bugtracking.repository.EmployeeRepository;
import com.cg.bugtracking.service.EmployeeServiceImpl;
import static com.cg.bugtracking.util.AppConstant.EMPLOYEE_NOT_FOUND_CONST;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
	
	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks 
	private EmployeeServiceImpl employeeService;

	@DisplayName("Sample test")
    @Test
    void sampleTest() {
        assertTrue(true);
    }
	
	@Test
	void testCreateEmployee(){
		Employee employee=new Employee();
		employee.setEmpId(12);
		employee.setEmpName("Sam");
		employee.setEmployeeEmail("gmail");
		employee.setEmployeeContact("23234");
		employee.setEmpStatus("Free");
		employee.setEmployeeUserId("1232");
		employee.setEmployeePassword("23e23wqw");
	
	
	    given(employeeRepository.save(employee)).willReturn(employee);	    
	    Employee createdEmployee = employeeService.createEmployee(employee);	     
	    Assertions.assertThat(createdEmployee).isNotNull();	    
	    verify(employeeRepository).save(any(Employee.class));  
       	    
	}
	
	@Test
	void testFindEmployeeById(){
		final long employeeId= 100;
		Employee employee=new Employee();
		employee.setEmpId(employeeId);
		employee.setEmpName("Sam");
		employee.setEmployeeEmail("gmail");
		employee.setEmployeeContact("23234");
		employee.setEmpStatus("Free");
		employee.setEmployeeUserId("1232");
		employee.setEmployeePassword("23e23wqw");
		

        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));
        final Employee expected  =employeeService.getEmployee(employeeId);
        Assertions.assertThat(expected).isNotNull();	 
       
	}
	
	
		@Test
		public void testFindEmployeeByIdWhenExceptionThrown(){
			final long employeeId= 100;
			Employee employee=new Employee();
			employee.setEmpId(employeeId);
			employee.setEmpName("Sam");
			employee.setEmployeeEmail("gmail");
			employee.setEmployeeContact("23234");
			employee.setEmpStatus("Free");
			employee.setEmployeeUserId("1232");
			employee.setEmployeePassword("23e23wqw");
			
		    
			final int empId = 1000;
			Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
		    	employeeService.getEmployee(employeeId);
		    });		
		    String expectedMessage = "Employee not found for this id: "+employeeId;
		    String actualMessage = exception.getMessage();
		    assertEquals(actualMessage,expectedMessage);
		}
		
		@Test
	    public void testFindAllEmployees(){
	        // given
			Employee employee=new Employee();
			employee.setEmpId(12);
			employee.setEmpName("Sam");
			employee.setEmployeeEmail("gmail");
			employee.setEmployeeContact("23234");
			employee.setEmpStatus("Free");
			employee.setEmployeeUserId("1232");
			employee.setEmployeePassword("23e23wqw");
		
		    
	        List<Employee> expectedEmployees = Arrays.asList(employee);
	        given(employeeRepository.findAll()).willReturn(expectedEmployees);
	        List<Employee> actualEmployees = employeeService.getAllEmployees();
	        Assertions.assertThat(actualEmployees).isEqualTo(expectedEmployees);
	      
	    }

		
		@Test
		void shouldUpdateEmployee() {
			long employeeId =92;
			Employee employee=new Employee();
			employee.setEmpId(employeeId);
			employee.setEmpName("Sam");
			employee.setEmployeeEmail("gmail");
			employee.setEmployeeContact("23234");
			employee.setEmpStatus("Free");
			employee.setEmployeeUserId("1232");
			employee.setEmployeePassword("23e23wqw");

			given(employeeRepository.save(employee)).willReturn(employee);
			given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

			Employee expectedEmployee = employeeService.updateEmployee(employeeId, employee);

			Assertions.assertThat(expectedEmployee).isNotNull();
			 
		}
		
		
		@Test
		void NotFoundExceptionUpdateEmployee() {
			long employeeId = 901;
			Employee employee=new Employee();
			employee.setEmpId(employeeId);
			employee.setEmpName("Sam");
			employee.setEmployeeEmail("gmail");
			employee.setEmployeeContact("23234");
			employee.setEmpStatus("Free");
			employee.setEmployeeUserId("1232");
			employee.setEmployeePassword("23e23wqw");

			Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
				employeeService.updateEmployee(employee.getEmpId(), employee);
			});
			String expectedMessage = EMPLOYEE_NOT_FOUND_CONST + employeeId;
			String actualMessage = exception.getMessage();
			assertEquals(actualMessage, expectedMessage);

		}
		
		
		
		@Test
		public void shouldBeDeleteEmployee() {
			long empId = 92;

			Employee employee=new Employee();
			employee.setEmpId(empId);
			employee.setEmpName("Sam");
			employee.setEmployeeEmail("gmail");
			employee.setEmployeeContact("23234");
			employee.setEmpStatus("Free");
			employee.setEmployeeUserId("1232");
			employee.setEmployeePassword("23e23wqw");
			
			given(employeeRepository.findById(empId)).willReturn(Optional.of(employee));
			employeeService.deleteEmployee(empId);
			employeeService.deleteEmployee(empId);
			verify(employeeRepository, times(2)).delete(employee);

		}
		
		
		@Test
		public void NotFoundExceptionDeleteEmployee() {

			long EmployeeId = 1000;
			Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
				employeeService.deleteEmployee(EmployeeId);
			});
			String expectedMessage = EMPLOYEE_NOT_FOUND_CONST + EmployeeId;
			String actualMessage = exception.getMessage();
			assertEquals(actualMessage, expectedMessage);
		}
		
		
		
		
}
