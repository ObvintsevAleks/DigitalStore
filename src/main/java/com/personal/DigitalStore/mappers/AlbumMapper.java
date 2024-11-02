package com.personal.DigitalStore.mappers;

import com.personal.DigitalStore.dto.AlbumDTO;
import com.personal.DigitalStore.dto.AlbumSaveDto;
import com.personal.DigitalStore.models.Album;
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
