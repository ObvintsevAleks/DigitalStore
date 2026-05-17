package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.AlbumDTO;
import com.personal.DigitalStore.dto.AlbumSaveDto;
import com.personal.DigitalStore.dto.ArtistDTO;
import com.personal.DigitalStore.exceptions.custom.InvalidFieldException;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.AlbumMapper;
import com.personal.DigitalStore.models.Album;
import com.personal.DigitalStore.models.Artist;
import com.personal.DigitalStore.models.Track;
import com.personal.DigitalStore.models.enumpack.AlbumType;
import com.personal.DigitalStore.repositories.AlbumRepository;
import com.personal.DigitalStore.repositories.TrackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Unit тест — AlbumService")
@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private AlbumMapper albumMapper;

    @Mock
    private ArtistService artistService;

    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private AlbumService albumService;

    private static final UUID ALBUM_ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final UUID ARTIST_ID = UUID.fromString("1a1b1c1d-1111-2222-3333-444444444444");
    private static final ZonedDateTime NOW = ZonedDateTime.parse("2019-08-06T16:30:00Z");
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 1, 1);

    private Artist artist() {
        return new Artist(ARTIST_ID, "John", "Doe", "JD", BIRTH_DATE, Collections.emptyList());
    }

    private ArtistDTO artistDTO() {
        return new ArtistDTO(ARTIST_ID, "John", "Doe", "JD", BIRTH_DATE);
    }

    private Album album() {
        return new Album(ALBUM_ID, "Rock Album", AlbumType.ALBUM, NOW, artist());
    }

    private AlbumDTO albumDTO() {
        return new AlbumDTO(ALBUM_ID, "Rock Album", AlbumType.ALBUM, NOW, artistDTO());
    }

    private AlbumSaveDto albumSaveDto() {
        return new AlbumSaveDto("Rock Album", AlbumType.ALBUM, NOW, artistDTO());
    }

    @Nested
    @DisplayName("createAlbum")
    class CreateAlbum {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO альбома")
        void success() {
            AlbumSaveDto saveDto = albumSaveDto();
            Album album = album();
            AlbumDTO expected = albumDTO();

            when(albumMapper.toAlbum(saveDto)).thenReturn(album);
            when(albumMapper.toAlbumDTO(album)).thenReturn(expected);

            AlbumDTO result = albumService.createAlbum(saveDto);

            assertThat(result).isEqualTo(expected);
            verify(albumRepository).save(album);
        }
    }

    @Nested
    @DisplayName("getAlbumById")
    class GetAlbumById {

        @Test
        @DisplayName("возвращает DTO когда альбом найден")
        void found() {
            Album album = album();
            AlbumDTO expected = albumDTO();

            when(albumRepository.findById(ALBUM_ID)).thenReturn(Optional.of(album));
            when(albumMapper.toAlbumDTO(album)).thenReturn(expected);

            AlbumDTO result = albumService.getAlbumById(ALBUM_ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда альбом не найден")
        void notFound() {
            when(albumRepository.findById(ALBUM_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> albumService.getAlbumById(ALBUM_ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(ALBUM_ID.toString());
        }
    }

    @Nested
    @DisplayName("updateAlbum")
    class UpdateAlbum {

        @Test
        @DisplayName("обновляет альбом и сохраняет изменения")
        void updatesWhenDataChanged() {
            Album album = album();
            AlbumDTO originalDTO = albumDTO();
            AlbumDTO updatedDTO = new AlbumDTO(ALBUM_ID, "Updated Album", AlbumType.SINGLE, NOW, artistDTO());

            when(albumRepository.findById(ALBUM_ID)).thenReturn(Optional.of(album));
            // первый вызов — сравнение, второй — после обновления
            when(albumMapper.toAlbumDTO(album)).thenReturn(originalDTO, updatedDTO);

            AlbumDTO result = albumService.updateAlbum(updatedDTO);

            verify(albumMapper).updateAlbum(album, updatedDTO);
            verify(albumRepository).save(album);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            Album album = album();
            AlbumDTO dto = albumDTO();

            when(albumRepository.findById(ALBUM_ID)).thenReturn(Optional.of(album));
            when(albumMapper.toAlbumDTO(album)).thenReturn(dto);

            AlbumDTO result = albumService.updateAlbum(dto);

            verify(albumRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если альбом не найден")
        void notFound() {
            when(albumRepository.findById(ALBUM_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> albumService.updateAlbum(albumDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteAlbumById")
    class DeleteAlbumById {

        @Test
        @DisplayName("успешно удаляет альбом без треков")
        void successWhenNoTracks() {
            Album album = album();
            AlbumDTO expected = albumDTO();

            when(albumRepository.findById(ALBUM_ID)).thenReturn(Optional.of(album));
            when(trackRepository.searchByAlbumId(ALBUM_ID)).thenReturn(Collections.emptyList());
            when(albumMapper.toAlbumDTO(album)).thenReturn(expected);

            AlbumDTO result = albumService.deleteAlbumById(ALBUM_ID);

            verify(albumRepository).deleteById(ALBUM_ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает InvalidFieldException при наличии связанных треков")
        void throwsWhenHasTracks() {
            Album album = album();
            Track track = Track.builder().id(UUID.randomUUID()).name("Test Track").album(album).build();

            when(albumRepository.findById(ALBUM_ID)).thenReturn(Optional.of(album));
            when(trackRepository.searchByAlbumId(ALBUM_ID)).thenReturn(List.of(track));

            assertThatThrownBy(() -> albumService.deleteAlbumById(ALBUM_ID))
                    .isInstanceOf(InvalidFieldException.class)
                    .hasMessageContaining(ALBUM_ID.toString());

            verify(albumRepository, never()).deleteById(any());
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если альбом не найден")
        void notFound() {
            when(albumRepository.findById(ALBUM_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> albumService.deleteAlbumById(ALBUM_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getAllAlbumsByArtistId")
    class GetAllAlbumsByArtistId {

        @Test
        @DisplayName("возвращает список альбомов по id артиста")
        void found() {
            List<Album> albums = List.of(album());
            AlbumDTO dto = albumDTO();

            when(albumRepository.searchByArtistId(ARTIST_ID)).thenReturn(Optional.of(albums));
            when(albumMapper.toAlbumDTOs(albums)).thenReturn(List.of(dto));

            List<AlbumDTO> result = albumService.getAllAlbumsByArtistId(ARTIST_ID);

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если альбомы не найдены")
        void notFound() {
            when(albumRepository.searchByArtistId(ARTIST_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> albumService.getAllAlbumsByArtistId(ARTIST_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getAlbumsByTitle")
    class GetAlbumsByTitle {

        @Test
        @DisplayName("возвращает список альбомов по заголовку")
        void found() {
            List<Album> albums = List.of(album());
            AlbumDTO dto = albumDTO();

            when(albumRepository.searchByTitle("Rock")).thenReturn(Optional.of(albums));
            when(albumMapper.toAlbumDTOs(albums)).thenReturn(List.of(dto));

            List<AlbumDTO> result = albumService.getAlbumsByTitle("Rock");

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если альбомы не найдены")
        void notFound() {
            when(albumRepository.searchByTitle("Nonexistent")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> albumService.getAlbumsByTitle("Nonexistent"))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getAlbumsByArtistPseudonym")
    class GetAlbumsByArtistPseudonym {

        @Test
        @DisplayName("возвращает альбомы через artistService по псевдониму")
        void success() {
            ArtistDTO artist = artistDTO();
            List<Album> albums = List.of(album());
            AlbumDTO dto = albumDTO();

            when(artistService.getArtistsByPseudonym("JD")).thenReturn(List.of(artist));
            when(albumRepository.searchByArtistId(ARTIST_ID)).thenReturn(Optional.of(albums));
            when(albumMapper.toAlbumDTOs(any())).thenReturn(List.of(dto));

            List<AlbumDTO> result = albumService.getAlbumsByArtistPseudonym("JD");

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("возвращает пустой список если у артиста нет альбомов")
        void noAlbumsForArtist() {
            ArtistDTO artist = artistDTO();

            when(artistService.getArtistsByPseudonym("JD")).thenReturn(List.of(artist));
            when(albumRepository.searchByArtistId(ARTIST_ID)).thenReturn(Optional.of(Collections.emptyList()));
            when(albumMapper.toAlbumDTOs(any())).thenReturn(Collections.emptyList());

            List<AlbumDTO> result = albumService.getAlbumsByArtistPseudonym("JD");

            assertThat(result).isEmpty();
        }
    }
}
