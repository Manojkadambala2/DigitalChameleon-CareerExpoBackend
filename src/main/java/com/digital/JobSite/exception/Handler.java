package com.digital.JobSite.exception;

import com.digital.JobSite.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setError(true);
        response.setMessage(ex.getMessage());
        return response;
    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setError(true);
        response.setMessage(ex.getMessage());
        return response;
    }

}
