package com.personal.Chinook.exceptions.Handler;

import com.personal.Chinook.exceptions.custom.ApiRequestException;
import com.personal.Chinook.exceptions.Payload.ErrorResponse;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiRequestExceptionHandler {

    @ExceptionHandler({ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestExceptions(ApiRequestException rEx) {
        // EL MAE DEL VIDEO ME DECIA QUE HAGA ESTO
        // PERO NO SIGUE EL PRINCIPIO OPEN/CLOSED, pero pues, estoy conciente que era solo para explicar
        //
        // HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        //int statusCode = 400;

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
