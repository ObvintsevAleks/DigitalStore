package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.GenreDTO;
import com.personal.DigitalStore.dto.GenreSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.GenreMapper;
import com.personal.DigitalStore.models.Genre;
import com.personal.DigitalStore.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Transactional
    public GenreDTO createGenre(GenreSaveDTO genreSaveDTO) {
        Genre genre = genreMapper.toGenre(genreSaveDTO);
        genreRepository.save(genre);
        return genreMapper.toGenreDTO(genre);
    }

    @Transactional(readOnly = true)
    public GenreDTO getGenreById(UUID id) throws NotFoundInDBException {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден Жанр по id = "+ id));
        return genreMapper.toGenreDTO(genre);
    }

    @Transactional(readOnly = true)
    public List<GenreDTO> getAll() {
        List<Genre> genres = genreRepository.findAll();
        return genreMapper.toGenreDTOs(genres);
    }

    @Transactional
    public GenreDTO updateGenre(GenreDTO genreDTO) throws NotFoundInDBException {
        Genre genreEntity = genreRepository.findById(genreDTO.getId()).orElseThrow(() -> new NotFoundInDBException("Не найден Жанр по id = "+ genreDTO.getId()));
        if (genreMapper.toGenreDTO(genreEntity).equals(genreDTO)) {
            return genreMapper.toGenreDTO(genreEntity);
        }
        genreMapper.updateGenre(genreEntity, genreDTO);
        genreRepository.save(genreEntity);
        return genreMapper.toGenreDTO(genreEntity);
    }

    @Transactional
    public GenreDTO deleteGenreById(UUID id) throws NotFoundInDBException {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден Жанр по id = "+ id));
        genreRepository.deleteById(id);
        return genreMapper.toGenreDTO(genre);
    }

}
