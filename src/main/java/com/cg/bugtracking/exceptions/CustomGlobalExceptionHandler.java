package com.cg.bugtracking.exceptions;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.bugtracking.payload.BaseErrorResponse;
import com.cg.bugtracking.payload.ValidationErrorResponse;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final String BAD_REQUEST = "BAD_REQUEST";
	private static Logger logger=LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);
// when a resource like employee or project is not found exception handler
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> customHandleNotFound(Exception ex, WebRequest request) {

		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(-1);
logger.error("Exception: "+ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

	}
	
// cant delete employee exception handler
	@ExceptionHandler(CantDeleteEmployee.class)
	public ResponseEntity<?> cantDeleteEmployee(Exception ex, WebRequest request) {

		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(-1);
		logger.error("Exception: "+ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	}

	//cant add employee exception handler
	@ExceptionHandler(CantAddEmployee.class)
	public ResponseEntity<?> cantAddEmployee(Exception ex, WebRequest request) {

		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(-1);
		logger.error("Exception: "+ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	}

	// constraint violation exception handler
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ValidationErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
			WebRequest request) {
		//get all errors
		List<String> details = ex.getConstraintViolations().parallelStream().map(e -> e.getMessage())
				.collect(Collectors.toList());

		ValidationErrorResponse validationErrResp = new ValidationErrorResponse();
		validationErrResp.setTimestamp(LocalDateTime.now());
		validationErrResp.setMessage(BAD_REQUEST);
		validationErrResp.setStatusCode(HttpStatus.BAD_REQUEST.value());
		validationErrResp.setDetails(details);
		logger.error("Exception: "+details);
		return new ResponseEntity<>(validationErrResp, HttpStatus.BAD_REQUEST);
	}

	//operation fails exception handler
	@ExceptionHandler(OperationFailedException.class)
	public final ResponseEntity<?> handleOperationFailedViolation(OperationFailedException ex, WebRequest request) {

		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		logger.error("Exception: "+ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
// Request body Validation exception handler 
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// Get all errors
		List<String> details = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		ValidationErrorResponse validationErrResp = new ValidationErrorResponse();
		validationErrResp.setTimestamp(LocalDateTime.now());
		validationErrResp.setMessage(BAD_REQUEST);
		validationErrResp.setStatusCode(HttpStatus.BAD_REQUEST.value());
		validationErrResp.setDetails(details);
		logger.error("Exception: "+details);
		return new ResponseEntity<>(validationErrResp, HttpStatus.BAD_REQUEST);

	}

	// Commmon exception handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> customHandleForServerError(Exception ex, WebRequest request) {

		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		logger.error("Exception: "+ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
@ExceptionHandler(LoginOperationException.class)
	public ResponseEntity<?> customHandleNotFound1(Exception ex, WebRequest request) {
		
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(-1);
		
		logger.error("Exception :: "+ex.getMessage());
		
		return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);

	}
}
