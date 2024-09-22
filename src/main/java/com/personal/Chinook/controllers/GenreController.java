package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.GenreDTO;
import com.personal.Chinook.DTO.GenreSaveDTO;
import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.DTO.MediaTypeSaveDTO;
import com.personal.Chinook.services.entity_services.GenreService;
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
@Tag(name = "genre-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService service;

    @ApiGet
    @Operation(summary = "Получить все жанры")
    @GetMapping("/all")
    public ResponseEntity<?> getAllGenres() {
        log.info("Request to display all genres");
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить жанр по идентификатору")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = GenreDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getGenre(@PathVariable("id") UUID id) {
        log.info("Request to display a artist with ID {}", id);
        return new ResponseEntity<>(service.getGenreById(id), HttpStatus.OK);
    }

    @ApiCreate
    @Operation(summary = "Создать жанр")
    @ApiResponse(responseCode = "201", description = " случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = GenreDTO.class)))
    @PostMapping
    public ResponseEntity<?> createGenre(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = GenreSaveDTO.class))
            @Valid @RequestBody GenreSaveDTO genreDTO) {
        return new ResponseEntity<>(service.createGenre(genreDTO), HttpStatus.CREATED);
    }

    @ApiUpdate
    @Operation(summary = "Обновить жанр")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = GenreDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateGenre(@RequestBody GenreDTO genreDTO) {
        return new ResponseEntity<>(service.updateGenre(genreDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удалить жанр")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(service.deleteGenreById(id), HttpStatus.NO_CONTENT);
    }

}
