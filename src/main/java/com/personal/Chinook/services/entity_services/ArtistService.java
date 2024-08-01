package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.models.Artist;

public interface ArtistService {

    Artist getArtistById(Integer id);

    Artist createArtist(Artist artist);

    Artist updateArtist(Integer artistId, Artist artist);

    Artist deleteArtist(Integer id);


}
