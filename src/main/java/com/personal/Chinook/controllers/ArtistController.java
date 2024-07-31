package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.services.entity_services.ArtistService;
import com.personal.Chinook.utils.swagger.ApiCreate;
import com.personal.Chinook.utils.swagger.ApiDelete;
import com.personal.Chinook.utils.swagger.ApiGet;
import com.personal.Chinook.utils.swagger.ApiUpdate;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Tag(name = "Artists controller")
@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;

    @Autowired
    public ArtistController(@Qualifier("ArtistService") ArtistService service) {

        this.service = service;
    }

    @PostMapping("/create")
    @ApiCreate
    public ResponseEntity<?> createArtist(@RequestBody ArtistDTO artistDTO) {
        Artist responseArtist = service.persist(artistDTO);

        return new ResponseEntity<>(responseArtist, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @ApiUpdate
    public ResponseEntity<?> updateArtist(@RequestBody ArtistDTO artistDTO) {
        service.update(artistDTO);

        return new ResponseEntity<>("The artist has been successfully updated!", HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/all")
    public ResponseEntity<?> getAllArtists() {
        List<Artist> responseArtistList = service.getAll();

        return new ResponseEntity<>(responseArtistList, HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/allcount")
    public ResponseEntity<?> getArtistCount() {

        return new ResponseEntity<>(service.getAll().size(), HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/searchid/{artistId}")
    public ResponseEntity<?> getArtistById(@PathVariable("artistId") Integer artistId) {
        Optional<Artist> responseArtist = service.getById(artistId);

        return new ResponseEntity<>(responseArtist, HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/searchname/{artistName}")
    public ResponseEntity<?> getArtistByName(@PathVariable("artistName") String artistName) {
        List<Artist> responseArtistList = service.getByName(artistName);

        return new ResponseEntity<>(responseArtistList, HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/remove/{artistId}")
    public ResponseEntity<?> deleteArtist(@PathVariable("artistId") Integer artistId) {
        service.deleteById(artistId);
        return new ResponseEntity<>("The artist was successfully deleted", HttpStatus.NO_CONTENT);
    }
}
