package com.personal.Chinook.controllers;

import com.personal.Chinook.dto.ArtistDTO;
import com.personal.Chinook.dto.ArtistSaveDTO;
import com.personal.Chinook.services.ArtistService;
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
@Tag(name = "artist-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;


    @ApiGet
    @Operation(summary = "Получить артиста по идентификатору")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = ArtistDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getArtist(@PathVariable("id") UUID artistId) {
        log.info("Request to display a artist with ID {}", artistId);
        return new ResponseEntity<>(service.getArtistById(artistId), HttpStatus.OK);
    }


    @ApiUpdate
    @Operation(summary = "Обновление артиста")
    @ApiResponse(responseCode = "200", description = "В случае успешного обновления сущности",
            content = @Content(schema = @Schema(implementation = ArtistDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateArtist(@RequestBody ArtistDTO artistDTO) {
        return new ResponseEntity<>(service.updateArtist(artistDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удаление артиста")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable("id") UUID artistId) {
        return new ResponseEntity<>(service.deleteArtistById(artistId), HttpStatus.NO_CONTENT);
    }

    @ApiCreate
    @Operation(summary = "Создание артиста")
    @ApiResponse(responseCode = "201", description = "В случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = ArtistDTO.class)))
    @PostMapping
    public ResponseEntity<?> createArtist(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = ArtistSaveDTO.class))
            @Valid
            @RequestBody
            ArtistSaveDTO artistSaveDTO
    ) {
        return new ResponseEntity<>(service.createArtist(artistSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @Operation(summary = "Получить список артистов по имени")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(allOf = ArtistDTO.class)))
    @GetMapping("/artists-by-name/{name}")
    public ResponseEntity<?> getArtistByName(@PathVariable("name") String artistName) {
        return new ResponseEntity<>(service.getArtistsByName(artistName), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить список артистов по псевдониму")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(allOf = ArtistDTO.class)))
    @GetMapping("/artists-by-pseudonym/{pseudonym}")
    public ResponseEntity<?> getArtistByPseudonym(@PathVariable("pseudonym") String pseudonym) {
        return new ResponseEntity<>(service.getArtistsByPseudonym(pseudonym), HttpStatus.OK);
    }


}
