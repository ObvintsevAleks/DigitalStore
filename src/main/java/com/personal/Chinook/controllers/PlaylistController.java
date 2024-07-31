package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.DTO.PlaylistDTO;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.models.Playlist;
import com.personal.Chinook.services.entity_services.PlaylistService;
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

@Tag(name = "Playlist controller")
@RestController
@RequestMapping("/playlists")
public class PlaylistController {


    private final PlaylistService service;

    @Autowired
    public PlaylistController(@Qualifier("PlaylistService") PlaylistService service) {

        this.service = service;
    }

    @PostMapping("/create")
    @ApiCreate
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistDTO playlistDto) {
        Playlist responsePlaylist = service.persist(playlistDto);

        return new ResponseEntity<>(responsePlaylist, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @ApiUpdate
    public ResponseEntity<?> updateArtist(@RequestBody PlaylistDTO playlistDto) {
        service.update(playlistDto);

        return new ResponseEntity<>("The playlist has been successfully updated!", HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/all")
    public ResponseEntity<?> getAllArtists() {
        List<Playlist> responsePlaylistList = service.getAll();

        return new ResponseEntity<>(responsePlaylistList, HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/searchid/{playlistId}")
    public ResponseEntity<?> getArtistById(@PathVariable("playlistId") Integer playlistId) {
        Optional<Playlist> responseArtist = service.getById(playlistId);

        return new ResponseEntity<>(responseArtist, HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/searchname/{playlistName}")
    public ResponseEntity<?> getArtistByName(@PathVariable("playlistName") String artistName) {
        List<Playlist> responseArtistList = service.getByName(artistName);

        return new ResponseEntity<>(responseArtistList, HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/remove/{playlistId}")
    public ResponseEntity<?> deleteArtist(@PathVariable("playlistId") Integer artistId) {
        service.deleteById(artistId);
        return new ResponseEntity<>("The playlist was successfully deleted", HttpStatus.NO_CONTENT);
    }

}
