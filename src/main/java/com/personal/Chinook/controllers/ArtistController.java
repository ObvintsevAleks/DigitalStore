package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.services.ArtistService;
import lombok.SneakyThrows;
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
        Artist savedArtist = service.save(artistDTO);

        return new ResponseEntity<>(savedArtist, HttpStatus.CREATED);
    }

    @GetMapping("/listall")
    @SneakyThrows
    public List<Artist> getAll() {
        /*HttpHeaders customHeader = new HttpHeaders();
        customHeader.add("custom-header", "customer-value");
        return new ResponseEntity<>(service.getAll(), customHeader, 200); */

        //throw new ApiRequestException("couldn't find list custom");

        return service.getAll();
    }

    @GetMapping("/get/{artistId}")
    public ResponseEntity<?> getById(@PathVariable("artistId") Integer artistId) {
        Optional<Artist> responseArtist = service.getById(artistId);

        return new ResponseEntity<>(responseArtist, HttpStatus.OK);
    }
}
