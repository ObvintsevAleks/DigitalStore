package com.personal.Chinook.controllers;

import com.personal.Chinook.dto.security.RegistrationRequest;
import com.personal.Chinook.dto.security.RegistrationResponse;
import com.personal.Chinook.services.security.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "registration-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody RegistrationRequest registrationRequest) {
        final RegistrationResponse registrationResponse = userService.registration(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

}
