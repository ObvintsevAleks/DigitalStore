package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.DTO.GenreDTO;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {

    GenreDTO toGenreDTO(Genre genre);

    Genre toGenre(GenreDTO artist);

    List<GenreDTO> toGenreDTOs(List<Genre> genres);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "tracks", ignore = true)
    void updateGenre(@MappingTarget Genre genre, GenreDTO genreDTO);

}
