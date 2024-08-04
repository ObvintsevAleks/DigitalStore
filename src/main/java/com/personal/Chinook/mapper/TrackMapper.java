package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.DTO.MediaTypeSaveDTO;
import com.personal.Chinook.DTO.TrackDTO;
import com.personal.Chinook.DTO.TrackSaveDTO;
import com.personal.Chinook.models.MediaType;
import com.personal.Chinook.models.Track;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MediaTypeMapper.class})
public interface TrackMapper {



//    @Mapping(target = "mediaType", expression = "java(track.getMediaType())")
//    @Mapping(target = "album", expression = "java(track.getAlbum())")
//    @Mapping(target = "genre", expression = "java(track.getGenre())")
    TrackDTO toTrackDTO(Track track);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "playlistList", ignore = true)
    @Mapping(target = "invoiceLineList", ignore = true)
    @Mapping(target = "mediaType", expression = "java(MediaTypeMapperImpl.toMediaTypeDTO(trackSaveDTO.getMediaType()) )")
    @Mapping(target = "album", expression = "java(AlbumMapperImpl.toAlbumDto(trackSaveDTO.getAlbum()))")
    @Mapping(target = "genre", expression = "java(GenreMapperImpl.toGenreDto(trackSaveDTO.getGenre()))")
    Track toTrack(TrackSaveDTO trackSaveDTO);

    List<TrackDTO> toTrackDTOs(List<Track> tracks);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "playlistList", ignore = true)
    @Mapping(target = "invoiceLineList", ignore = true)
    @Mapping(target = "mediaType", expression = "java(MediaTypeMapperImpl.toMediaTypeDTO(trackDTO.getMediaType()) )")
    @Mapping(target = "album", expression = "java(AlbumMapperImpl.toAlbumDto(trackDTO.getAlbum()))")
    @Mapping(target = "genre", expression = "java(GenreMapperImpl.toGenreDto(trackDTO.getGenre()))")
    void updateTrack(@MappingTarget Track track, TrackDTO trackDTO);

}
