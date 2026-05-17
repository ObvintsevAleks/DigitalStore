package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.GenreDTO;
import com.personal.DigitalStore.dto.GenreSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.GenreMapper;
import com.personal.DigitalStore.models.Genre;
import com.personal.DigitalStore.models.enumpack.GenreDirection;
import com.personal.DigitalStore.repositories.GenreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Unit тест — GenreService")
@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreService genreService;

    private static final UUID ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final LocalDate DATE = LocalDate.of(2020, 1, 1);

    private Genre genre() {
        return new Genre(ID, "Cloud Rap", DATE, GenreDirection.POPULAR);
    }

    private GenreDTO genreDTO() {
        return new GenreDTO(ID, "Cloud Rap", DATE, GenreDirection.POPULAR);
    }

    private GenreSaveDTO genreSaveDTO() {
        return new GenreSaveDTO("Cloud Rap", DATE, GenreDirection.POPULAR);
    }

    @Nested
    @DisplayName("createGenre")
    class CreateGenre {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO жанра")
        void success() {
            GenreSaveDTO saveDTO = genreSaveDTO();
            Genre genre = genre();
            GenreDTO expected = genreDTO();

            when(genreMapper.toGenre(saveDTO)).thenReturn(genre);
            when(genreMapper.toGenreDTO(genre)).thenReturn(expected);

            GenreDTO result = genreService.createGenre(saveDTO);

            assertThat(result).isEqualTo(expected);
            verify(genreRepository).save(genre);
        }
    }

    @Nested
    @DisplayName("getGenreById")
    class GetGenreById {

        @Test
        @DisplayName("возвращает DTO когда жанр найден")
        void found() {
            Genre genre = genre();
            GenreDTO expected = genreDTO();

            when(genreRepository.findById(ID)).thenReturn(Optional.of(genre));
            when(genreMapper.toGenreDTO(genre)).thenReturn(expected);

            GenreDTO result = genreService.getGenreById(ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда жанр не найден")
        void notFound() {
            when(genreRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> genreService.getGenreById(ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(ID.toString());
        }
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {

        @Test
        @DisplayName("возвращает все жанры")
        void returnsAllGenres() {
            List<Genre> genres = List.of(genre());
            GenreDTO dto = genreDTO();

            when(genreRepository.findAll()).thenReturn(genres);
            when(genreMapper.toGenreDTOs(genres)).thenReturn(List.of(dto));

            List<GenreDTO> result = genreService.getAll();

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("возвращает пустой список если жанров нет")
        void returnsEmptyList() {
            when(genreRepository.findAll()).thenReturn(List.of());
            when(genreMapper.toGenreDTOs(List.of())).thenReturn(List.of());

            List<GenreDTO> result = genreService.getAll();

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("updateGenre")
    class UpdateGenre {

        @Test
        @DisplayName("обновляет жанр и сохраняет изменения")
        void updatesWhenDataChanged() {
            Genre genre = genre();
            GenreDTO originalDTO = genreDTO();
            GenreDTO updatedDTO = new GenreDTO(ID, "Hip-Hop", DATE, GenreDirection.POPULAR);

            when(genreRepository.findById(ID)).thenReturn(Optional.of(genre));
            // первый вызов — сравнение, второй — после обновления
            when(genreMapper.toGenreDTO(genre)).thenReturn(originalDTO, updatedDTO);

            GenreDTO result = genreService.updateGenre(updatedDTO);

            verify(genreMapper).updateGenre(genre, updatedDTO);
            verify(genreRepository).save(genre);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            Genre genre = genre();
            GenreDTO dto = genreDTO();

            when(genreRepository.findById(ID)).thenReturn(Optional.of(genre));
            when(genreMapper.toGenreDTO(genre)).thenReturn(dto);

            GenreDTO result = genreService.updateGenre(dto);

            verify(genreRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если жанр не найден")
        void notFound() {
            when(genreRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> genreService.updateGenre(genreDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteGenreById")
    class DeleteGenreById {

        @Test
        @DisplayName("успешно удаляет жанр")
        void success() {
            Genre genre = genre();
            GenreDTO expected = genreDTO();

            when(genreRepository.findById(ID)).thenReturn(Optional.of(genre));
            when(genreMapper.toGenreDTO(genre)).thenReturn(expected);

            GenreDTO result = genreService.deleteGenreById(ID);

            verify(genreRepository).deleteById(ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если жанр не найден")
        void notFound() {
            when(genreRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> genreService.deleteGenreById(ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }
}
