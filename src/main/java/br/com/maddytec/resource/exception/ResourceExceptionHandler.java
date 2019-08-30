package br.com.maddytec.resource.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.maddytec.exception.NotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handlerNotFoundException(NotFoundException notFoundException) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), notFoundException.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
}
