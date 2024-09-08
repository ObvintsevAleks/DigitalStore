package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.TrackDTO;
import com.personal.Chinook.DTO.TrackSaveDTO;
import com.personal.Chinook.services.entity_services.TrackService;
import com.personal.Chinook.utils.swagger.ApiCreate;
import com.personal.Chinook.utils.swagger.ApiDelete;
import com.personal.Chinook.utils.swagger.ApiGet;
import com.personal.Chinook.utils.swagger.ApiUpdate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Tag(name = "track-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService service;

    @ApiGet
    @GetMapping("/{id}")
    public ResponseEntity<?> getTrackById(@PathVariable("id") UUID trackId) {
        log.info("Request to display a album with ID {}", trackId);
        return new ResponseEntity<>(service.getTrackById(trackId), HttpStatus.OK);
    }

    @ApiUpdate
    @PutMapping
    public ResponseEntity<?> updateTrackById(@RequestBody TrackDTO trackDTO) {
        return new ResponseEntity<>(service.updateTrack(trackDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrackById(@PathVariable("id") UUID trackId) {
        return new ResponseEntity<>(service.deleteTrackById(trackId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiCreate
    public ResponseEntity<?> createTrack(@RequestBody TrackSaveDTO trackSaveDTO) {
        return new ResponseEntity<>(service.createTrack(trackSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @GetMapping("/{albumId}")
    public ResponseEntity<?> getAllTracksByAlbumId(@PathVariable("albumId") UUID albumId) {
        log.info("Request to display a album with ID {}", albumId);
        return new ResponseEntity<>(service.getAllTracksByAlbumId(albumId), HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/{genreId}")
    public ResponseEntity<?> getAllTracksByGenreId(@PathVariable("genreId") UUID genreId) {
        log.info("Request to display a album with ID {}", genreId);
        return new ResponseEntity<>(service.getAllTracksByGenreId(genreId), HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/{mediaTypeId}")
    public ResponseEntity<?> getAllTracksByMediaTypeId(@PathVariable("mediaTypeId") UUID mediaTypeId) {
        log.info("Request to display a album with ID {}", mediaTypeId);
        return new ResponseEntity<>(service.getAllTracksByMediaTypeId(mediaTypeId), HttpStatus.OK);
    }

}
