package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.models.MediaType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaTypeMapper {

    MediaTypeDTO toMediaTypeDTO(MediaType mediaType);

    MediaType toMediaType(MediaTypeDTO mediaTypeDTO);

    List<MediaTypeDTO> toMediaTypeDTOs(List<MediaType> mediaTypes);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "tracks", ignore = true)
    void updateMediaType(@MappingTarget MediaType genre, MediaTypeDTO genreDTO);

}
