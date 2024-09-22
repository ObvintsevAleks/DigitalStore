package com.personal.Chinook.dto.security;

import com.personal.Chinook.models.enumpack.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticatedUserDto {

    private String name;

    private String username;

    private String password;

    private UserRole userRole;

}
