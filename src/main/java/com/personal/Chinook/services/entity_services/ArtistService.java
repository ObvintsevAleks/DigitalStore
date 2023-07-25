package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.repositories.IRepositoryArtist;
import com.personal.Chinook.services.custom_functions.INameQuery;
import com.personal.Chinook.services.db_functions.IDBCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service("ArtistService")
public class ArtistService implements IDBCrud<Artist, ArtistDTO>, INameQuery<Artist> {

    private final IRepositoryArtist repoArtist;

    @Autowired
    public ArtistService(
            @Qualifier("ArtistRepo") IRepositoryArtist repoArtist
    ) {

        this.repoArtist = repoArtist;
    }

    @Override
    public List<Artist> getAll() {

        return repoArtist.findAll();
    }

    @Override
    public Optional<Artist> getById(Integer authorId) {
        if (authorId == null)
            throw new InvalidFieldException("ERROR, found empty fields");

        if (authorId < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        return repoArtist.findById(authorId);
    }

    @Override
    public void persist(ArtistDTO artistDTO) {
        if (
                artistDTO.getId() == null ||
                artistDTO.getName() == null ||
                artistDTO.getName().length() == 0
        )
            throw new InvalidFieldException("ERROR, found empty fields");

        if (artistDTO.getId() < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        if ( !(Pattern.matches("[a-zA-Z -]+", artistDTO.getName())) )
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        repoArtist.save(
                new Artist(
                        artistDTO.getId(),
                        artistDTO.getName()
                )
        );
    }

    @Override
    public void deleteById(Integer artistId) {
        if (artistId == null)
            throw new InvalidFieldException("ERROR, found empty fields");

        if (artistId < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        if (!repoArtist.existsById(artistId))
            throw new NotFoundInDBException("ERROR, artist not found in database");

        repoArtist.deleteById(artistId);
    }

    @Override
    public void update(ArtistDTO artistDTO) {
        if ( !repoArtist.existsById(artistDTO.getId()) )
            throw new NotFoundInDBException("ERROR, artist does not exist in database");

        this.persist(artistDTO);
    }

    @Override
    public List<Artist> getByName(String artistName) {
        if (artistName == null || artistName.length() == 0)
            throw new InvalidFieldException("ERROR, found empty fields");

        if ( !(Pattern.matches("[a-zA-Z -]+", artistName)) )
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        return repoArtist.searchByName(artistName);
    }
}
