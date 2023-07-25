package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.models.Album;
import com.personal.Chinook.repositories.IRepositoryAlbum;
import com.personal.Chinook.services.custom_functions.INameQuery;
import com.personal.Chinook.services.db_functions.IDBCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service("AlbumService")
public class AlbumService implements IDBCrud<Album, AlbumDTO> , INameQuery<Album> {

    private final IRepositoryAlbum repoAlbum;

    @Autowired
    public AlbumService(@Qualifier("AlbumRepo") IRepositoryAlbum repo) {
        this.repoAlbum = repo;
    }

    @Override
    public List<Album> getAll() {
        return null;
    }

    @Override
    public Optional<Album> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void persist(AlbumDTO albumDTO) {

    }

    @Override
    public void update(AlbumDTO albumDTO) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Album> getByName(String albumName) {
        return null;
    }

    /*@Override
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

     */
}
