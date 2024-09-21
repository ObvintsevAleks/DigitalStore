package com.personal.Chinook.security.mapper;

import com.personal.Chinook.security.dto.AuthenticatedUserDto;
import com.personal.Chinook.security.dto.RegistrationRequest;
import com.personal.Chinook.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
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
