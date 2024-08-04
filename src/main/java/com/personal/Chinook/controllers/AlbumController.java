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
@Tag(name = "Album controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {


    private final AlbumService service;


    @ApiGet
    @GetMapping("/searchid/{albumId}")
    public AlbumDTO getArtistById(@PathVariable("albumId") UUID albumId) {
        log.info("Request to display a album with ID {}", albumId);
        return service.getAlbumById(albumId);
    }

    @ApiGet
    @GetMapping("/searchByArtistId/{artistId}")
    public List<AlbumDTO> getAllAlbumsByArtistId(@PathVariable("artistId") UUID artistId) {
        log.info("Request to display all albums by artistID {}", artistId);
        return service.getAllAlbumsByArtistId(artistId);
    }

    @ApiUpdate
    @PutMapping("/update/{albumId}")
    public ResponseEntity<?> updateArtistById(@RequestBody AlbumDTO albumDTO) {
        return new ResponseEntity<>(service.updateAlbum(albumDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/remove/{artistId}")
    public ResponseEntity<?> deleteArtistById(@PathVariable("artistId") UUID albumId) {
        return new ResponseEntity<>(service.deleteAlbumById(albumId), HttpStatus.NO_CONTENT);
    }


    @PostMapping("/create")
    @ApiCreate
    public ResponseEntity<?> createArtist(@RequestBody AlbumSaveDto albumSaveDto) {
        return new ResponseEntity<>(service.createAlbum(albumSaveDto), HttpStatus.CREATED);
    }

    @ApiGet
    @GetMapping("/searchname/{albumTitle}")
    public ResponseEntity<?> getAlbumByTitle(@PathVariable("albumTitle") String albumTitle) {
        return new ResponseEntity<>(service.getArtistsByTitle(albumTitle), HttpStatus.OK);
    }


}
