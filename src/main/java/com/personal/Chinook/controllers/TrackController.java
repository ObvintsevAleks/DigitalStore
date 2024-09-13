package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.DTO.MediaTypeSaveDTO;
import com.personal.Chinook.DTO.TrackDTO;
import com.personal.Chinook.DTO.TrackSaveDTO;
import com.personal.Chinook.services.entity_services.TrackService;
import com.personal.Chinook.utils.swagger.ApiCreate;
import com.personal.Chinook.utils.swagger.ApiDelete;
import com.personal.Chinook.utils.swagger.ApiGet;
import com.personal.Chinook.utils.swagger.ApiUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @Operation(summary = "Получить аудиозапись по идентификатору", description = "Получить аудиозапись по идентификатору", tags = {"track-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = TrackDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getTrackById(@PathVariable("id") UUID trackId) {
        log.info("Request to display a album with ID {}", trackId);
        return new ResponseEntity<>(service.getTrackById(trackId), HttpStatus.OK);
    }

    @ApiUpdate
    @Operation(summary = "Обновить аудиозапись", description = "Обновить аудиозапись", tags = {"track-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = TrackDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateTrackById(@RequestBody TrackDTO trackDTO) {
        return new ResponseEntity<>(service.updateTrack(trackDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удалить аудиозапись", description = "Удалить аудиозапись", tags = {"track-controller"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrackById(@PathVariable("id") UUID trackId) {
        return new ResponseEntity<>(service.deleteTrackById(trackId), HttpStatus.NO_CONTENT);
    }

    @ApiCreate
    @Operation(summary = "Создать аудиозапись", description = "Создать аудиозапись", tags = {"track-controller"})
    @ApiResponse(responseCode = "201", description = " случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = TrackDTO.class)))
    @PostMapping
    public ResponseEntity<?> createTrack(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = TrackSaveDTO.class))
            @Valid @RequestBody TrackSaveDTO trackSaveDTO) {
        return new ResponseEntity<>(service.createTrack(trackSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @Operation(summary = "Получить все аудиозаписи по идентификатору альбома", description = "Получить все аудиозаписи по идентификатору альбома", tags = {"track-controller"})
    @GetMapping("/all-tracks-by-album/{id}")
    public ResponseEntity<?> getAllTracksByAlbumId(@PathVariable("id") UUID albumId) {
        log.info("Request to display a album with ID {}", albumId);
        return new ResponseEntity<>(service.getAllTracksByAlbumId(albumId), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить все аудиозаписи по идентификатору жанра", description = "Получить все аудиозаписи по идентификатору жанра", tags = {"track-controller"})
    @GetMapping("/all-tracks-by-genre/{id}")
    public ResponseEntity<?> getAllTracksByGenreId(@PathVariable("id") UUID genreId) {
        log.info("Request to display a album with ID {}", genreId);
        return new ResponseEntity<>(service.getAllTracksByGenreId(genreId), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить все аудиозаписи по идентификатору медиа-типа", description = "Получить все аудиозаписи по идентификатору медиа-типа", tags = {"track-controller"})
    @GetMapping("/all-tracks-by-media-type/{id}")
    public ResponseEntity<?> getAllTracksByMediaTypeId(@PathVariable("id") UUID mediaTypeId) {
        log.info("Request to display a album with ID {}", mediaTypeId);
        return new ResponseEntity<>(service.getAllTracksByMediaTypeId(mediaTypeId), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить все аудиозаписи по идентификатору артиста", description = "Получить все аудиозаписи по идентификатору артиста", tags = {"track-controller"})
    @GetMapping("/all-tracks-by-artist-id/{id}")
    public ResponseEntity<?> getAllTracksyArtistId(@PathVariable("id") UUID artistId) {
        log.info("Request to display a album with ID {}", artistId);
        return new ResponseEntity<>(service.getAllTracksByArtistId(artistId), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить все аудиозаписи по идентификатору артиста", description = "Получить все аудиозаписи по идентификатору артиста", tags = {"track-controller"})
    @GetMapping("/all-tracks-by-artist-pseudonym/{pseudonym}")
    public ResponseEntity<?> getAllTracksyArtistId(@PathVariable("pseudonym") String artistPseudonym) {
        log.info("Request to display a album with ID {}", artistPseudonym);
        return new ResponseEntity<>(service.getAllTracksByArtistPseudonym(artistPseudonym), HttpStatus.OK);
    }

}
