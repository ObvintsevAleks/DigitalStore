package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.DTO.ArtistSaveDTO;
import com.personal.Chinook.services.entity_services.ArtistService;
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
@Tag(name = "artists")
@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;

    @ApiGet
    @GetMapping("/{id}")
    public ArtistDTO getArtist(@PathVariable("id") UUID artistId) {
        log.info("Request to display a artist with ID {}", artistId);
        return service.getArtistById(artistId);
    }

    @ApiUpdate
    @PutMapping
    public ResponseEntity<?> updateArtist(@RequestBody ArtistDTO artistDTO) {
        return new ResponseEntity<>(service.updateArtist(artistDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable("id") UUID artistId) {
        return new ResponseEntity<>(service.deleteArtistById(artistId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiCreate
    public ResponseEntity<?> createArtist(@RequestBody ArtistSaveDTO artistSaveDTO) {
        return new ResponseEntity<>(service.createArtist(artistSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @GetMapping("/{byName}")
    public ResponseEntity<?> getArtistByName(@PathVariable("byName") String artistName) {
        return new ResponseEntity<>(service.getArtistsByName(artistName), HttpStatus.OK);
    }

}
