package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.services.entity_services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService service;

    @Autowired
    public ArtistController(@Qualifier("ArtistService") ArtistService service) {

        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> insertArtist(@RequestBody ArtistDTO artistDTO) {
        service.persist(artistDTO);

        return new ResponseEntity<>("The artist was successfully saved!", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateArtist(@RequestBody ArtistDTO artistDTO) {
        service.update(artistDTO);

        return new ResponseEntity<>("The artist has been updated!", HttpStatus.OK);
    }

    @GetMapping("/listall")
    public ResponseEntity<?> getAll() {
        List<Artist> responseArtistList = service.getAll();

        return new ResponseEntity<>(responseArtistList, HttpStatus.OK);
    }

    @GetMapping("/allcount")
    public ResponseEntity<?> getCount() {
        return new ResponseEntity<>(service.getAll().size(), HttpStatus.OK);
    }

    @GetMapping("/searchid/{artistId}")
    public ResponseEntity<?> getById(@PathVariable("artistId") Integer artistId) {
        Optional<Artist> responseArtist = service.getById(artistId);

        return new ResponseEntity<>(responseArtist, HttpStatus.OK);
    }

    @GetMapping("/searchname/{artistName}")
    public ResponseEntity<?> getByName(@PathVariable("artistName") String artistName) {
        List<Artist> responseArtistList = service.getByName(artistName);

        return new ResponseEntity<>(responseArtistList, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{artistId}")
    public ResponseEntity<?> deleteById(@PathVariable("artistId") Integer artistId) {
        service.deleteById(artistId);
        return new ResponseEntity<>("The artist was successfully deleted", HttpStatus.NO_CONTENT);
    }
}
