package com.personal.Chinook.mappers;

import com.personal.Chinook.dto.MediaTypeDTO;
import com.personal.Chinook.dto.MediaTypeSaveDTO;
import com.personal.Chinook.models.MediaType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaTypeMapper {

    MediaTypeDTO toMediaTypeDTO(MediaType mediaType);

    @Mapping(target = "id", ignore = true)
    MediaType toMediaType(MediaTypeSaveDTO mediaTypeDTO);

    List<MediaTypeDTO> toMediaTypeDTOs(List<MediaType> mediaTypes);

    @Mapping(target = "id", ignore = true)
    void updateMediaType(@MappingTarget MediaType mediaType, MediaTypeDTO mediaTypeDTO);

}
