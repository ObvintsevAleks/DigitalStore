package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.ArtistDTO;
import com.personal.DigitalStore.dto.ArtistSaveDTO;
import com.personal.DigitalStore.exceptions.custom.InvalidFieldException;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.ArtistMapper;
import com.personal.DigitalStore.models.Album;
import com.personal.DigitalStore.models.Artist;
import com.personal.DigitalStore.models.enumpack.AlbumType;
import com.personal.DigitalStore.repositories.AlbumRepository;
import com.personal.DigitalStore.repositories.ArtistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private ArtistMapper artistMapper;

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private ArtistService artistService;

    private static final UUID ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final LocalDate BIRTH_DATE = LocalDate.of(1985, 10, 7);

    private Artist artist() {
        return new Artist(ID, "John", "Doe", "JD", BIRTH_DATE, Collections.emptyList());
    }

    private ArtistDTO artistDTO() {
        return new ArtistDTO(ID, "John", "Doe", "JD", BIRTH_DATE);
    }

    private ArtistSaveDTO artistSaveDTO() {
        return new ArtistSaveDTO("John", "Doe", "JD", BIRTH_DATE);
    }

    @Nested
    @DisplayName("createArtist")
    class CreateArtist {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO артиста")
        void success() {
            ArtistSaveDTO saveDTO = artistSaveDTO();
            Artist artist = artist();
            ArtistDTO expected = artistDTO();

            when(artistMapper.toArtist(saveDTO)).thenReturn(artist);
            when(artistMapper.toArtistDTO(artist)).thenReturn(expected);

            ArtistDTO result = artistService.createArtist(saveDTO);

            assertThat(result).isEqualTo(expected);
            verify(artistRepository).save(artist);
        }
    }

    @Nested
    @DisplayName("getArtistById")
    class GetArtistById {

        @Test
        @DisplayName("возвращает DTO когда артист найден")
        void found() {
            Artist artist = artist();
            ArtistDTO expected = artistDTO();

            when(artistRepository.findById(ID)).thenReturn(Optional.of(artist));
            when(artistMapper.toArtistDTO(artist)).thenReturn(expected);

            ArtistDTO result = artistService.getArtistById(ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда артист не найден")
        void notFound() {
            when(artistRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> artistService.getArtistById(ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(ID.toString());
        }
    }

    @Nested
    @DisplayName("updateArtist")
    class UpdateArtist {

        @Test
        @DisplayName("обновляет артиста и сохраняет изменения")
        void updatesWhenDataChanged() {
            Artist artist = artist();
            ArtistDTO originalDTO = artistDTO();
            ArtistDTO updatedDTO = new ArtistDTO(ID, "Jane", "Doe", "JD", BIRTH_DATE);

            when(artistRepository.findById(ID)).thenReturn(Optional.of(artist));
            // первый вызов — сравнение, второй — после обновления
            when(artistMapper.toArtistDTO(artist)).thenReturn(originalDTO, updatedDTO);

            ArtistDTO result = artistService.updateArtist(updatedDTO);

            verify(artistMapper).updateArtist(artist, updatedDTO);
            verify(artistRepository).save(artist);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            Artist artist = artist();
            ArtistDTO dto = artistDTO();

            when(artistRepository.findById(ID)).thenReturn(Optional.of(artist));
            when(artistMapper.toArtistDTO(artist)).thenReturn(dto);

            ArtistDTO result = artistService.updateArtist(dto);

            verify(artistRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если артист не найден")
        void notFound() {
            when(artistRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> artistService.updateArtist(artistDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteArtistById")
    class DeleteArtistById {

        @Test
        @DisplayName("успешно удаляет артиста без связанных альбомов")
        void successWhenNoAlbums() {
            Artist artist = artist();
            ArtistDTO expected = artistDTO();

            when(artistRepository.findById(ID)).thenReturn(Optional.of(artist));
            when(albumRepository.searchByArtistId(ID)).thenReturn(Optional.of(Collections.emptyList()));
            when(artistMapper.toArtistDTO(artist)).thenReturn(expected);

            ArtistDTO result = artistService.deleteArtistById(ID);

            verify(artistRepository).deleteById(ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает InvalidFieldException при наличии связанных альбомов")
        void throwsWhenHasAlbums() {
            Artist artist = artist();
            Album linkedAlbum = new Album(UUID.randomUUID(), "Linked Album", AlbumType.SINGLE, null, artist);

            when(artistRepository.findById(ID)).thenReturn(Optional.of(artist));
            when(albumRepository.searchByArtistId(ID)).thenReturn(Optional.of(List.of(linkedAlbum)));

            assertThatThrownBy(() -> artistService.deleteArtistById(ID))
                    .isInstanceOf(InvalidFieldException.class)
                    .hasMessageContaining(ID.toString());

            verify(artistRepository, never()).deleteById(any());
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если артист не найден")
        void notFound() {
            when(artistRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> artistService.deleteArtistById(ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getArtistsByName")
    class GetArtistsByName {

        @Test
        @DisplayName("возвращает список DTO артистов по имени")
        void found() {
            List<Artist> artists = List.of(artist());
            ArtistDTO dto = artistDTO();

            when(artistRepository.searchByName("John")).thenReturn(Optional.of(artists));
            when(artistMapper.toArtistDTOs(artists)).thenReturn(List.of(dto));

            List<ArtistDTO> result = artistService.getArtistsByName("John");

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если ничего не найдено")
        void notFound() {
            when(artistRepository.searchByName("Unknown")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> artistService.getArtistsByName("Unknown"))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining("Unknown");
        }
    }

    @Nested
    @DisplayName("getArtistsByPseudonym")
    class GetArtistsByPseudonym {

        @Test
        @DisplayName("возвращает список DTO артистов по псевдониму")
        void found() {
            List<Artist> artists = List.of(artist());
            ArtistDTO dto = artistDTO();

            when(artistRepository.searchByPseudonym("JD")).thenReturn(Optional.of(artists));
            when(artistMapper.toArtistDTOs(artists)).thenReturn(List.of(dto));

            List<ArtistDTO> result = artistService.getArtistsByPseudonym("JD");

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если ничего не найдено")
        void notFound() {
            when(artistRepository.searchByPseudonym("Unknown")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> artistService.getArtistsByPseudonym("Unknown"))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining("Unknown");
        }
    }
}
