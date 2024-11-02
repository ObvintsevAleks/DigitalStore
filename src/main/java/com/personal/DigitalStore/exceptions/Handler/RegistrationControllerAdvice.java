package com.personal.DigitalStore.exceptions.Handler;

import com.personal.DigitalStore.controllers.RegistrationController;
import com.personal.DigitalStore.exceptions.Payload.ApiExceptionResponse;
import com.personal.DigitalStore.exceptions.custom.RegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackageClasses = RegistrationController.class)
public class RegistrationControllerAdvice {

    @ExceptionHandler(RegistrationException.class)
    ResponseEntity<ApiExceptionResponse> handleRegistrationException(RegistrationException exception) {

        final ApiExceptionResponse response = new ApiExceptionResponse(exception.getErrorMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
