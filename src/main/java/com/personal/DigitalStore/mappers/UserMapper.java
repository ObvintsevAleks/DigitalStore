package com.personal.DigitalStore.mappers;

import com.personal.DigitalStore.dto.security.AuthenticatedUserDto;
import com.personal.DigitalStore.dto.security.RegistrationRequest;
import com.personal.DigitalStore.models.User;
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
