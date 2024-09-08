package com.personal.Chinook.controllers;


import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.DTO.AlbumSaveDto;
import com.personal.Chinook.services.entity_services.AlbumService;
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
    @GetMapping("/{id}")
    public AlbumDTO getAlbumById(@PathVariable("id") UUID albumId) {
        log.info("Request to display a album with ID {}", albumId);
        return service.getAlbumById(albumId);
    }

    @ApiGet
    @GetMapping("/{artistId}")
    public List<AlbumDTO> getAllAlbumsByArtistId(@PathVariable("artistId") UUID artistId) {
        log.info("Request to display all albums by artistID {}", artistId);
        return service.getAllAlbumsByArtistId(artistId);
    }

    @ApiUpdate
    @PutMapping
    public ResponseEntity<?> updateAlbumById(@RequestBody AlbumDTO albumDTO) {
        return new ResponseEntity<>(service.updateAlbum(albumDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbumById(@PathVariable("id") UUID albumId) {
        return new ResponseEntity<>(service.deleteAlbumById(albumId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiCreate
    public ResponseEntity<?> createAlbum(@RequestBody AlbumSaveDto albumSaveDto) {
        return new ResponseEntity<>(service.createAlbum(albumSaveDto), HttpStatus.CREATED);
    }

    @ApiGet
    @GetMapping("/{byTitle}")
    public ResponseEntity<?> getAlbumByTitle(@PathVariable("byTitle") String albumTitle) {
        return new ResponseEntity<>(service.getArtistsByTitle(albumTitle), HttpStatus.OK);
    }

}
