package com.personal.Chinook.mappers;

import com.personal.Chinook.dto.security.AuthenticatedUserDto;
import com.personal.Chinook.dto.security.RegistrationRequest;
import com.personal.Chinook.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(RegistrationRequest registrationRequest);

    AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

    User convertToUser(AuthenticatedUserDto authenticatedUserDto);


}
