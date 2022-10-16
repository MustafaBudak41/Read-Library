package com.RL.exception;

import com.RL.exception.message.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ReadLibraryExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiResponseError error){
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        ApiResponseError error = new ApiResponseError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request){
        ApiResponseError error = new ApiResponseError(HttpStatus.CONFLICT, ex.getMessage(), request.getDescription(false));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request){
        ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request){
        ApiResponseError error=new ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(),request.getServletPath());
        return buildResponseEntity(error);
    }
    @ExceptionHandler(RuntimeException.class)//beklenen disinda bir exception gelirse
    protected ResponseEntity<Object> handleGeneralException(RuntimeException ex,WebRequest request){
        ApiResponseError error=new ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(),request.getDescription(false));
        return buildResponseEntity(error);
    }
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,WebRequest request){
        ApiResponseError error=new ApiResponseError(HttpStatus.FORBIDDEN,ex.getMessage(),request.getDescription(false));
        return buildResponseEntity(error);
    }
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex,WebRequest request){
        ApiResponseError error=new ApiResponseError(HttpStatus.BAD_REQUEST,ex.getMessage(),request.getDescription(false));
        return buildResponseEntity(error);
    }
}
