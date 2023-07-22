package com.personal.Chinook.services;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.models.Artist;
import java.util.List;
import java.util.Optional;

public interface IArtistService {
    List<Artist> getAll();

    Optional<Artist> getById(Integer authorId);

    List<Artist> getByName(String artistName);

    Artist save(ArtistDTO artist);

    void delete(Integer artistId);
}
