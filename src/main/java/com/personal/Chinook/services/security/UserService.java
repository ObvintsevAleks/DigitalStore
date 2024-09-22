package com.personal.Chinook.services.security;

import com.personal.Chinook.dto.security.AuthenticatedUserDto;
import com.personal.Chinook.dto.security.RegistrationRequest;
import com.personal.Chinook.dto.security.RegistrationResponse;
import com.personal.Chinook.models.User;

public interface UserService {

    User findByUsername(String username);

    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

}
