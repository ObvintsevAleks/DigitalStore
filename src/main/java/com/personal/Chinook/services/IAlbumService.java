package com.personal.Chinook.services;

import com.personal.Chinook.models.Album;
import java.util.List;

public interface IAlbumService {
    List<Album> getAll();

    Album get();

    List<Album> getByID(Integer albumId);

    List<Album> getByTitle(String albumTitle);

    List<Album> getByArtistName(String authorName);

    List<Album> getByArtistID(Integer authorId);

    List<Album> getByTrackId(Integer trackId);

    List<Album> getByTrackName(String trackName);
}
