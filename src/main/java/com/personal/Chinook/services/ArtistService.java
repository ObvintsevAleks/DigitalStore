package com.personal.Chinook.services;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.repositories.IRepositoryArtist;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service("ArtistService")
public class ArtistService implements IArtistService {

    private final IRepositoryArtist repoArtist;

    @Autowired
    public ArtistService(@Qualifier("ArtistRepository") IRepositoryArtist repoArtist) {
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
    public List<Artist> getByName(String artistName) {
        if (artistName == null || artistName.length() == 0)
            throw new InvalidFieldException("ERROR, found empty fields");

        if ( !(Pattern.matches("[a-zA-Z -]+", artistName)) )
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        return repoArtist.searchByName(artistName);
    }

    @Override
    public Artist save(ArtistDTO artistDTO) {
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

        return repoArtist.save(
                new Artist(
                        artistDTO.getId(),
                        artistDTO.getName()
                )
        );
    }

    @Override
    public void delete(Integer artistId) {
        if (artistId == null)
            throw new InvalidFieldException("ERROR, found empty fields");

        if (artistId < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        if (!repoArtist.existsById(artistId))
            throw new NotFoundInDBException("ERROR, artist not found in database");

        repoArtist.deleteById(artistId);
    }
}
