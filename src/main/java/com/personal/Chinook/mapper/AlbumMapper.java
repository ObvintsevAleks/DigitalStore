package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.DTO.AlbumSaveDto;
import com.personal.Chinook.models.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumMapper {

    AlbumDTO toAlbumDTO(Album album);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    Album toAlbum(AlbumSaveDto albumSaveDto);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    List<AlbumDTO> toAlbumDTOs(List<Album> albums);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    void updateAlbum(@MappingTarget Album album, AlbumDTO albumDTO);

}
