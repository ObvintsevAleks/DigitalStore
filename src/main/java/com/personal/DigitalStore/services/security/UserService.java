package com.personal.DigitalStore.services.security;

import com.personal.DigitalStore.dto.security.AuthenticatedUserDto;
import com.personal.DigitalStore.dto.security.RegistrationRequest;
import com.personal.DigitalStore.dto.security.RegistrationResponse;
import com.personal.DigitalStore.models.User;

public interface UserService {

    User findByUsername(String username);

    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

}
