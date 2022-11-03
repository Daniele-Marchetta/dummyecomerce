package it.ecommerce.exceptions;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler{

	private static final Logger logger = LogManager.getLogger(GlobalException.class);

	@ExceptionHandler
	public ResponseEntity<ErrorObject> HandleResourceNotFoundException(ResourceNotFoundException ex) {
		logger.error("Resource not Found !! {}", () -> HttpStatus.NOT_FOUND.value());
		ErrorObject e = new ErrorObject();
		e.setStatusCode(HttpStatus.NOT_FOUND.value());
		e.setMessage(ex.getMessage());
		e.setTimestamp(System.currentTimeMillis());

		return new ResponseEntity<ErrorObject>(e, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleNoDataFoundException(NoDataFoundException ex) {
		logger.warn("No data Found !! {}", () -> HttpStatus.NO_CONTENT.value());

		return new ResponseEntity<ErrorObject>(HttpStatus.NO_CONTENT);

	}

	@ExceptionHandler
	public ResponseEntity<ErrorObject> handleDatabaseException(DatabaseException ex) {
		logger.error("Database exception , Conflict !! {}", () -> HttpStatus.CONFLICT.value());

		ErrorObject e = new ErrorObject();
		e.setStatusCode(HttpStatus.CONFLICT.value());
		e.setMessage(ex.getMessage());
		e.setTimestamp(System.currentTimeMillis());

		return new ResponseEntity<ErrorObject>(e, HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorObject> FilestorageException(FileStorageException ex) {
		logger.error("File storage exception !! {}", () -> HttpStatus.BAD_REQUEST);

		ErrorObject e = new ErrorObject();
		e.setStatusCode(HttpStatus.BAD_REQUEST.value());
		e.setMessage(ex.getMessage());
		e.setTimestamp(System.currentTimeMillis());

		return new ResponseEntity<ErrorObject>(e, HttpStatus.BAD_REQUEST);

	}
	
	
	 @Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("Not valid method argument !! {}", () -> HttpStatus.BAD_REQUEST.value());
	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", new Date());
	        body.put("status", status.value());

	        List<String> errors = ex.getBindingResult()
	                .getAllErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());

	        body.put("errors", errors);
	        return new ResponseEntity<>(body, headers, status);
	}

	 

}
