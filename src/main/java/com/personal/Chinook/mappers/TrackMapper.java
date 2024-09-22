package com.personal.Chinook.mappers;

import com.personal.Chinook.dto.TrackDTO;
import com.personal.Chinook.dto.TrackSaveDTO;
import com.personal.Chinook.models.Track;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackMapper {

    TrackDTO toTrackDTO(Track track);

    @Mapping(target = "id", ignore = true)
    Track toTrack(TrackSaveDTO trackSaveDTO);

    List<TrackDTO> toTrackDTOs(List<Track> tracks);

    @Mapping(target = "id", ignore = true)
    void updateTrack(@MappingTarget Track track, TrackDTO trackDTO);

}
