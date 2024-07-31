package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.PlaylistDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.models.Playlist;
import com.personal.Chinook.repositories.IRepositoryPlaylist;
import com.personal.Chinook.services.common_query_functions.IDBCrud;
import com.personal.Chinook.services.common_query_functions.INameQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service("PlaylistService")
public class PlaylistService implements IDBCrud<Playlist, PlaylistDTO>, INameQuery<Playlist> {

    private final IRepositoryPlaylist repoPlaylist;

    @Autowired
    public PlaylistService(@Qualifier("PlaylistRepo") IRepositoryPlaylist repoPlaylist) {
        this.repoPlaylist = repoPlaylist;
    }

    @Override
    public List<Playlist> getAll() {
        return repoPlaylist.findAll();
    }

    @Override
    public Optional<Playlist> getById(Integer playlistId) {
        if (playlistId == null)
            throw new InvalidFieldException("ERROR, found empty fields");

        return repoPlaylist.findById(playlistId);
    }

    @Override
    public Playlist persist(PlaylistDTO playlistDTO) {
        if (
                playlistDTO.getId() == null ||
                        playlistDTO.getName() == null ||
                        playlistDTO.getName().isEmpty() ||
                        playlistDTO.getName().isBlank()
        )
            throw new InvalidFieldException("ERROR, found empty fields");

        if (playlistDTO.getId() < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        if (!(Pattern.matches("[a-zA-Z -]+", playlistDTO.getName())))
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        if (playlistDTO.getName().length() > 120)
            throw new InvalidFieldException("ERROR, name exceeds character limit (120)");

        return repoPlaylist.save(
                Playlist.builder()
                        .id(playlistDTO.getId())
                        .name(playlistDTO.getName().trim()).build());
    }

    @Override
    public void update(PlaylistDTO playlistDTO) {
        if (!repoPlaylist.existsById(playlistDTO.getId()))
            throw new NotFoundInDBException("ERROR, playlist does not exist in database");

        persist(playlistDTO);
    }

    @Override
    public void deleteById(Integer playlistId) {
        if (playlistId == null)
            throw new InvalidFieldException("ERROR, found empty fields");

        if (playlistId < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        if (!repoPlaylist.existsById(playlistId))
            throw new NotFoundInDBException("ERROR, artist not found in database");

        repoPlaylist.deleteById(playlistId);
    }

    @Override
    public List<Playlist> getByName(String playlistName) {
        if (playlistName == null || playlistName.isBlank())
            throw new InvalidFieldException("ERROR, found empty fields");

        if (!(Pattern.matches("[a-zA-Z -]+", playlistName)))
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        return repoPlaylist.searchByName(playlistName);
    }
}
