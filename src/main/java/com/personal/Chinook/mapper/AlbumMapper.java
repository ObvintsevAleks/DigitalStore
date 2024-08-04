package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.DTO.AlbumSaveDto;
import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.DTO.ArtistSaveDTO;
import com.personal.Chinook.models.Album;
import com.personal.Chinook.models.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumMapper {

    AlbumDTO toAlbumDTO(Album album);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "tracks", ignore = true)
    Album toAlbum(AlbumSaveDto albumSaveDto);

    List<AlbumDTO> toAlbumDTOs(List<Album> albums);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "tracks", ignore = true)
    void updateAlbum(@MappingTarget Album album, AlbumDTO albumDTO);

}
