package br.com.maddytec.resource.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.maddytec.exception.NotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handlerNotFoundException(NotFoundException notFoundException) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), notFoundException.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> handlerBadCredentialsException(BadCredentialsException badCredentialsException) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED.value(), badCredentialsException.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handlerAccessDeniedException(AccessDeniedException accessDeniedException) {
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage(), new Date());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = new ArrayList<>(); 
				ex.getBindingResult().getAllErrors().forEach(error -> {
					errors.add(error.getDefaultMessage());
				});
				
		String defaultMessage = "Invalid field(s)";
		ApiErroList error = new ApiErroList(HttpStatus.BAD_REQUEST.value(), defaultMessage, new Date(), errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
