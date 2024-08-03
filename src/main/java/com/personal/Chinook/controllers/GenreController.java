package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.GenreDTO;
import com.personal.Chinook.services.entity_services.GenreService;
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

@Slf4j
@Tag(name = "Genre controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService service;

    @ApiGet
    @GetMapping("/all")
    public ResponseEntity<?> getAllGenres() {
        log.info("Request to display all genres");
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/searchid/{genreId}")
    public ResponseEntity<?> getGenreById(@PathVariable("genreId") Integer id) {
        log.info("Request to display a artist with ID {}", id);
        return new ResponseEntity<>(service.getGenreById(id), HttpStatus.OK);
    }

    @ApiCreate
    @PostMapping("/create")
    public ResponseEntity<?> createGenre(@RequestBody GenreDTO genreDTO) {
        return new ResponseEntity<>(service.createGenre(genreDTO), HttpStatus.CREATED);
    }

    @ApiUpdate
    @PutMapping("/update/{genreId}")
    public ResponseEntity<?> updateGenre(@PathVariable("genreId") Integer id,
                                         @RequestBody GenreDTO genreDTO) {
        return new ResponseEntity<>(service.updateGenre(id, genreDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/remove/{genreId}")
    public ResponseEntity<?> deleteGenre(@PathVariable("genreId") Integer id) {
        return new ResponseEntity<>(service.deleteGenreById(id), HttpStatus.NO_CONTENT);
    }
}
