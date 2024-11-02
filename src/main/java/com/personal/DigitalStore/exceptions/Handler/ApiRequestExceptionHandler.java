package com.personal.DigitalStore.exceptions.Handler;

import com.personal.DigitalStore.exceptions.custom.ApiRequestException;
import com.personal.DigitalStore.exceptions.Payload.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiRequestExceptionHandler {

    @ExceptionHandler({ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestExceptions(ApiRequestException rEx) {

        //create payload containing ex details
        ErrorResponse errorResponse = new ErrorResponse(
                rEx.getMessage(),
                rEx.getHttpStatus(),
                rEx.getStatusCode(),
                rEx.getTimeStamp()
        );

        //response entity
        return new ResponseEntity<>(errorResponse, rEx.getHttpStatus());
    }
}
