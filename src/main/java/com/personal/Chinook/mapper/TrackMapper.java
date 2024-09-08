package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.TrackDTO;
import com.personal.Chinook.DTO.TrackSaveDTO;
import com.personal.Chinook.models.Track;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackMapper {

    TrackDTO toTrackDTO(Track track);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "invoiceLines", ignore = true)
    Track toTrack(TrackSaveDTO trackSaveDTO);

    List<TrackDTO> toTrackDTOs(List<Track> tracks);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "invoiceLines", ignore = true)
    void updateTrack(@MappingTarget Track track, TrackDTO trackDTO);

}
