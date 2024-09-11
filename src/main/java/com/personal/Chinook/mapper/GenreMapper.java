package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.GenreDTO;
import com.personal.Chinook.DTO.GenreSaveDTO;
import com.personal.Chinook.models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {

    GenreDTO toGenreDTO(Genre genre);

    @Mapping(target = "id", ignore = true)
    Genre toGenre(GenreSaveDTO artist);

    List<GenreDTO> toGenreDTOs(List<Genre> genres);

    @Mapping(target = "id", ignore = true)
    void updateGenre(@MappingTarget Genre genre, GenreDTO genreDTO);

}
