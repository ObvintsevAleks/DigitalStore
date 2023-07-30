package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.GenreDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.models.Genre;
import com.personal.Chinook.repositories.IRepositoryGenre;
import com.personal.Chinook.services.common_query_functions.IDBCrud;
import com.personal.Chinook.services.common_query_functions.INameQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service("GenreService")
public class GenreService implements IDBCrud<Genre, GenreDTO>, INameQuery<Genre> {

    private final IRepositoryGenre repoGenre;

    @Autowired
    public GenreService(@Qualifier("GenreRepo") IRepositoryGenre repoGenre) {
        this.repoGenre = repoGenre;
    }

    @Override
    public List<Genre> getAll() {
        return repoGenre.findAll();
    }

    @Override
    public Optional<Genre> getById(Integer genreId) {
        if (genreId == null)
            throw new InvalidFieldException("ERROR, found empty fields");

        return repoGenre.findById(genreId);
    }

    @Override
    public Genre persist(GenreDTO genreDTO) {
        if (
                genreDTO.getId() == null ||
                genreDTO.getName() == null ||
                genreDTO.getName().isEmpty() ||
                genreDTO.getName().isBlank()
        )
            throw new InvalidFieldException("ERROR, found empty fields");

        if (genreDTO.getId() < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        if ( !(Pattern.matches("[a-zA-Z -]+", genreDTO.getName())) )
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        if (genreDTO.getName().length() > 120)
            throw new InvalidFieldException("ERROR, name exceeds character limit (120)");

        return repoGenre.save(
                new Genre(
                        genreDTO.getId(),
                        genreDTO.getName().trim()
                )
        );
    }

    @Override
    public void update(GenreDTO genreDTO) {
        if ( !(repoGenre.existsById(genreDTO.getId())) )
            throw new NotFoundInDBException("ERROR, genre not found in database");

        persist(genreDTO);
    }

    @Override
    public void deleteById(Integer id) {
        if ( !(repoGenre.existsById(id)) )
            throw new NotFoundInDBException("ERROR, genre not found in database");

        repoGenre.deleteById(id);
    }

    @Override
    public List<Genre> getByName(String name) {
        if (name == null || name.isBlank())
            throw new InvalidFieldException("ERROR, found empty fields");

        if ( !(Pattern.matches("[a-zA-Z -]+", name)) )
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        return repoGenre.searchByName(name.trim());
    }
}
