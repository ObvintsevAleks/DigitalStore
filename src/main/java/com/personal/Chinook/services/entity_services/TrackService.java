package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.DTO.TrackDTO;
import com.personal.Chinook.DTO.TrackSaveDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.TrackMapper;
import com.personal.Chinook.models.Track;
import com.personal.Chinook.repositories.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final AlbumService albumService;
    private final TrackMapper trackMapper;

    @Transactional(readOnly = true)
    public TrackDTO getTrackById(UUID id) throws NotFoundInDBException {
        Track track = trackRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найдена аудиозапись id = "+ id));
        return trackMapper.toTrackDTO(track);
    }

    @Transactional
    public TrackDTO createTrack(TrackSaveDTO trackSaveDTO) {
        Track track = trackMapper.toTrack(trackSaveDTO);
        trackRepository.save(track);
        return trackMapper.toTrackDTO(track);
    }

    @Transactional
    public TrackDTO updateTrack(TrackDTO trackDTO) throws NotFoundInDBException {
        Track trackEntity = trackRepository.findById(trackDTO.getId()).orElseThrow(() -> new NotFoundInDBException("Не найдена аудиозапись id = "+ trackDTO.getId()));
        if (trackMapper.toTrackDTO(trackEntity).equals(trackDTO)) {
            return trackMapper.toTrackDTO(trackEntity);
        }
        trackMapper.updateTrack(trackEntity, trackDTO);
        trackRepository.save(trackEntity);
        return trackMapper.toTrackDTO(trackEntity);
    }

    @Transactional
    public TrackDTO deleteTrackById(UUID id) throws NotFoundInDBException {
        Track track = trackRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найдена аудиозапись id = "+ id));
        trackRepository.deleteById(id);
        return trackMapper.toTrackDTO(track);
    }

    @Transactional(readOnly = true)
    public List<TrackDTO> getAllTracksByAlbumId(UUID id) throws NotFoundInDBException {
        List<Track> tracks = trackRepository.searchByAlbumId(id);
        return trackMapper.toTrackDTOs(tracks);
    }

    @Transactional(readOnly = true)
    public List<TrackDTO> getAllTracksByGenreId(UUID id) throws NotFoundInDBException {
        List<Track> tracks = trackRepository.searchByGenreId(id);
        return trackMapper.toTrackDTOs(tracks);
    }

    @Transactional(readOnly = true)
    public List<TrackDTO> getAllTracksByMediaTypeId(UUID id) throws NotFoundInDBException {
        List<Track> tracks = trackRepository.searchByMediaTypeId(id);
        return trackMapper.toTrackDTOs(tracks);
    }

    @Transactional(readOnly = true)
    public List<TrackDTO> getAllTracksByArtistId(UUID artistId) throws NotFoundInDBException {
        List<AlbumDTO> albums = albumService.getAllAlbumsByArtistId(artistId);
        List<Track> lists = new ArrayList<>();
        albums.forEach(album -> lists.addAll(trackRepository.searchByAlbumId(album.getId())));
        return trackMapper.toTrackDTOs(lists);
    }

    @Transactional(readOnly = true)
    public List<TrackDTO> getAllTracksByArtistPseudonym(String artistPseudonym) throws NotFoundInDBException {
        List<AlbumDTO> albums = albumService.getAlbumsByArtistPseudonym(artistPseudonym);
        List<Track> lists = new ArrayList<>();
        albums.forEach(album -> lists.addAll(trackRepository.searchByAlbumId(album.getId())));
        return trackMapper.toTrackDTOs(lists);
    }

}
