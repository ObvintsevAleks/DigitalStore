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
@Tag(name = "Artists controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;

    @ApiGet
    @GetMapping("/searchid/{artistId}")
    public ArtistDTO getArtistById(@PathVariable("artistId") UUID artistId) {
        log.info("Request to display a artist with ID {}", artistId);
        return service.getArtistById(artistId);
    }

    @ApiUpdate
    @PutMapping("/update/{artistId}")
    public ResponseEntity<?> updateArtistById(@RequestBody ArtistDTO artistDTO) {
        return new ResponseEntity<>(service.updateArtist(artistDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/remove/{artistId}")
    public ResponseEntity<?> deleteArtistById(@PathVariable("artistId") UUID artistId) {
        return new ResponseEntity<>(service.deleteArtistById(artistId), HttpStatus.NO_CONTENT);
    }


    @PostMapping("/create")
    @ApiCreate
    public ResponseEntity<?> createArtist(@RequestBody ArtistSaveDTO artistSaveDTO) {
        return new ResponseEntity<>(service.createArtist(artistSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @GetMapping("/searchname/{artistName}")
    public ResponseEntity<?> getArtistByName(@PathVariable("artistName") String artistName) {
        return new ResponseEntity<>(service.getArtistsByName(artistName), HttpStatus.OK);
    }


}
