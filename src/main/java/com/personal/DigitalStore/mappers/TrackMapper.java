package com.personal.DigitalStore.mappers;

import com.personal.DigitalStore.dto.TrackDTO;
import com.personal.DigitalStore.dto.TrackSaveDTO;
import com.personal.DigitalStore.models.Track;
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
