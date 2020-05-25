package com.citi.cmb.gce.accountservices.exception;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

	@Autowired
	private Logger logger;

	@Autowired
	ExceptionProperties exceptionProperties;

	@ExceptionHandler(AccountServicesException.class)
	public ResponseEntity<ExceptionResponse> handleException(AccountServicesException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode(ex.getErrorCode());
		response.setErrorMessage(ex.getMessage());
		ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		return entity;
	}
	
	@ExceptionHandler(DataException.class)
	public ResponseEntity<ExceptionResponse> handleException(DataException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode(ASExceptionConstants.ERR007);
		response.setErrorMessage(exceptionProperties.getErrorMessage(ASExceptionConstants.ERR007));
		ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		return entity;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
		logger.error("Logging exception: "+ex);
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode(ASExceptionConstants.ERR007);
		response.setErrorMessage(exceptionProperties.getErrorMessage(ASExceptionConstants.ERR007));
		ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		return entity;
	}
}
