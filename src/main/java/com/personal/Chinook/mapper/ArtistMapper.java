package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.DTO.ArtistSaveDTO;
import com.personal.Chinook.models.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistMapper {

    ArtistDTO toArtistDTO(Artist artist);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "albums", ignore = true)
    Artist toArtist(ArtistSaveDTO artist);

    List<ArtistDTO> toArtistDTOs(List<Artist> artists);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "albums", ignore = true)
    void updateArtist(@MappingTarget Artist artist, ArtistDTO artistDTO);

}
