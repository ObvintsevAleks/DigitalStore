package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.models.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistMapper {

    ArtistDTO artistToArtistDto(Artist artist);

    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    Artist artistDtoToArtist(ArtistDTO artist);

}
