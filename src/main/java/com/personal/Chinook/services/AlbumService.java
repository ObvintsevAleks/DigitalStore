package com.personal.Chinook.services;

import com.personal.Chinook.models.Album;
import com.personal.Chinook.repositories.IRepositoryAlbum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService implements IAlbumService {

    private final IRepositoryAlbum repoAlbum;

    @Autowired
    public AlbumService(@Qualifier("AlbumRepository") IRepositoryAlbum repo) {
        this.repoAlbum = repo;
    }

    @Override
    public List<Album> getAll() {
        return repoAlbum.findAll();
    }

    @Override
    public Album get() {
        return null;
    }

    @Override
    public List<Album> getByID(Integer albumId) {
        return null;
    }

    @Override
    public List<Album> getByTitle(String albumTitle) {
        return null;
    }

    @Override
    public List<Album> getByArtistName(String authorName) {
        return null;
    }

    @Override
    public List<Album> getByArtistID(Integer authorId) {
        return null;
    }

    @Override
    public List<Album> getByTrackId(Integer trackId) {
        return null;
    }

    @Override
    public List<Album> getByTrackName(String trackName) {
        return null;
    }
}
