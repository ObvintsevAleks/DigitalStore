package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
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


@Slf4j
@Tag(name = "Artists controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;

    @ApiGet
    @GetMapping("/searchid/{artistId}")
    public ArtistDTO getArtistById(@PathVariable("artistId") Integer artistId) throws NotFoundInDBException {
        log.info("Request to display a artist with ID {}", artistId);
        return service.getArtistById(artistId);
    }

    @ApiUpdate
    @PutMapping("/update/{artistId}")
    public ResponseEntity<?> updateArtistById(@PathVariable("artistId") Integer id, @RequestBody ArtistDTO artistDTO) throws NotFoundInDBException {
        return new ResponseEntity<>(service.updateArtist(id, artistDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/remove/{artistId}")
    public ResponseEntity<?> deleteArtistById(@PathVariable("artistId") Integer artistId) throws NotFoundInDBException {
        return new ResponseEntity<>(service.deleteArtistById(artistId), HttpStatus.NO_CONTENT);
    }


    @PostMapping("/create")
    @ApiCreate
    public ResponseEntity<?> createArtist(@RequestBody ArtistDTO artistDTO) {
        return new ResponseEntity<>(service.createArtist(artistDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @GetMapping("/searchname/{artistName}")
    public ResponseEntity<?> getArtistByName(@PathVariable("artistName") String artistName) throws NotFoundInDBException {
        return new ResponseEntity<>(service.getArtistsByName(artistName), HttpStatus.OK);
    }


}
