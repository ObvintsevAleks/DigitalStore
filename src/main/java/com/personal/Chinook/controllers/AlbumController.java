package com.personal.Chinook.controllers;


import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.DTO.AlbumSaveDto;
import com.personal.Chinook.services.entity_services.AlbumService;
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

import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "album-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService service;
    @ApiGet
    @Operation(summary = "Получить альбом по идентификатору", description = "Получить альбом по идентификатору", tags = {"album-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = AlbumDTO.class)))
    @GetMapping("/{id}")
    public AlbumDTO getAlbumById(@PathVariable("id") UUID albumId) {
        log.info("Request to display a album with ID {}", albumId);
        return service.getAlbumById(albumId);
    }

    @ApiGet
    @Operation(summary = "Получить список альбомов по идентификатору артиста", description = "Получить список альбомов по идентификатору артиста", tags = {"album-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(allOf = AlbumDTO.class)))
    @GetMapping("/albums-by-artist-id/{artistId}")
    public List<AlbumDTO> getAllAlbumsByArtistId(@PathVariable("artistId") UUID artistId) {
        log.info("Request to display all albums by artistID {}", artistId);
        return service.getAllAlbumsByArtistId(artistId);
    }

    @ApiUpdate
    @Operation(summary = "Обновить альбом", description = "Обновить альбом", tags = {"album-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = AlbumDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateAlbumById(@RequestBody AlbumDTO albumDTO) {
        return new ResponseEntity<>(service.updateAlbum(albumDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удалить альбом", description = "Удалить альбом", tags = {"album-controller"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbumById(@PathVariable("id") UUID albumId) {
        return new ResponseEntity<>(service.deleteAlbumById(albumId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @Operation(summary = "Создать альбом", description = "Создать альбом", tags = {"album-controller"})
    @ApiResponse(responseCode = "201", description = " случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = AlbumDTO.class)))
    @ApiCreate
    public ResponseEntity<?> createAlbum(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = AlbumSaveDto.class))
            @Valid
            @RequestBody AlbumSaveDto albumSaveDto
    ) {
        return new ResponseEntity<>(service.createAlbum(albumSaveDto), HttpStatus.CREATED);
    }

    @ApiGet
    @Operation(summary = "Получить список альбомов по заголовку", description = "Получить список альбомов по заголовку", tags = {"album-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(allOf = AlbumDTO.class)))
    @GetMapping("/albums-by-title/{title}")
    public ResponseEntity<?> getAlbumByTitle(@PathVariable("title") String albumTitle) {
        return new ResponseEntity<>(service.getAlbumsByTitle(albumTitle), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить список альбомов по псевдониму артиста", description = "Получить список альбомов по псевдониму артиста", tags = {"album-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(allOf = AlbumDTO.class)))
    @GetMapping("/albums-by-artist-pseudonym/{pseudonym}")
    public ResponseEntity<?> getAlbumByArtistPseudonym(@PathVariable("pseudonym") String artistPseudonym) {
        return new ResponseEntity<>(service.getAlbumsByArtistPseudonym(artistPseudonym), HttpStatus.OK);
    }

}
