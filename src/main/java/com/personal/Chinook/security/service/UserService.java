package com.personal.Chinook.security.service;

import com.personal.Chinook.security.dto.AuthenticatedUserDto;
import com.personal.Chinook.security.dto.RegistrationRequest;
import com.personal.Chinook.security.dto.RegistrationResponse;
import com.personal.Chinook.security.model.User;

public interface UserService {

    User findByUsername(String username);

    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

}
