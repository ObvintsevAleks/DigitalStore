package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.AlbumDTO;
import com.personal.DigitalStore.dto.ArtistDTO;
import com.personal.DigitalStore.dto.GenreDTO;
import com.personal.DigitalStore.dto.MediaTypeDTO;
import com.personal.DigitalStore.dto.TrackDTO;
import com.personal.DigitalStore.dto.TrackSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.TrackMapper;
import com.personal.DigitalStore.models.Album;
import com.personal.DigitalStore.models.Artist;
import com.personal.DigitalStore.models.Genre;
import com.personal.DigitalStore.models.MediaType;
import com.personal.DigitalStore.models.Track;
import com.personal.DigitalStore.models.enumpack.AlbumType;
import com.personal.DigitalStore.models.enumpack.GenreDirection;
import com.personal.DigitalStore.repositories.TrackRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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

@DisplayName("Unit тест — TrackService")
@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private AlbumService albumService;

    @Mock
    private TrackMapper trackMapper;

    @InjectMocks
    private TrackService trackService;

    private static final UUID TRACK_ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final UUID ALBUM_ID = UUID.fromString("1a1b1c1d-1111-2222-3333-444444444444");
    private static final UUID ARTIST_ID = UUID.fromString("2b2b2b2b-2222-3333-4444-555555555555");
    private static final UUID GENRE_ID = UUID.fromString("3c3c3c3c-3333-4444-5555-666666666666");
    private static final UUID MEDIA_TYPE_ID = UUID.fromString("4d4d4d4d-4444-5555-6666-777777777777");
    private static final ZonedDateTime NOW = ZonedDateTime.parse("2019-08-06T16:30:00Z");
    private static final LocalDate DATE = LocalDate.of(2020, 1, 1);

    private Artist artist() {
        return new Artist(ARTIST_ID, "John", "Doe", "JD", DATE, Collections.emptyList());
    }

    private ArtistDTO artistDTO() {
        return new ArtistDTO(ARTIST_ID, "John", "Doe", "JD", DATE);
    }

    private Album album() {
        return new Album(ALBUM_ID, "Rock Album", AlbumType.ALBUM, NOW, artist());
    }

    private AlbumDTO albumDTO() {
        return new AlbumDTO(ALBUM_ID, "Rock Album", AlbumType.ALBUM, NOW, artistDTO());
    }

    private Genre genre() {
        return new Genre(GENRE_ID, "Rock", DATE, GenreDirection.POPULAR);
    }

    private GenreDTO genreDTO() {
        return new GenreDTO(GENRE_ID, "Rock", DATE, GenreDirection.POPULAR);
    }

    private MediaType mediaType() {
        return MediaType.builder().id(MEDIA_TYPE_ID).name("MP3").createdAt(DATE).build();
    }

    private MediaTypeDTO mediaTypeDTO() {
        return new MediaTypeDTO(MEDIA_TYPE_ID, "MP3", DATE);
    }

    private Track track() {
        return Track.builder()
                .id(TRACK_ID)
                .name("Test Track")
                .author("John")
                .createdAt(NOW)
                .milliseconds(300000)
                .bytes(30000)
                .unitPrice(BigDecimal.valueOf(2.41))
                .album(album())
                .genre(genre())
                .mediaType(mediaType())
                .build();
    }

    private TrackDTO trackDTO() {
        return new TrackDTO(TRACK_ID, "Test Track", "John", NOW, 300000, 30000,
                BigDecimal.valueOf(2.41), albumDTO(), mediaTypeDTO(), genreDTO());
    }

    private TrackSaveDTO trackSaveDTO() {
        return new TrackSaveDTO("Test Track", "John", NOW, 300000, 30000,
                BigDecimal.valueOf(2.41), albumDTO(), mediaTypeDTO(), genreDTO());
    }

    @Nested
    @DisplayName("createTrack")
    class CreateTrack {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO трека")
        void success() {
            TrackSaveDTO saveDTO = trackSaveDTO();
            Track track = track();
            TrackDTO expected = trackDTO();

            when(trackMapper.toTrack(saveDTO)).thenReturn(track);
            when(trackMapper.toTrackDTO(track)).thenReturn(expected);

            TrackDTO result = trackService.createTrack(saveDTO);

            assertThat(result).isEqualTo(expected);
            verify(trackRepository).save(track);
        }
    }

    @Nested
    @DisplayName("getTrackById")
    class GetTrackById {

        @Test
        @DisplayName("возвращает DTO когда трек найден")
        void found() {
            Track track = track();
            TrackDTO expected = trackDTO();

            when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.of(track));
            when(trackMapper.toTrackDTO(track)).thenReturn(expected);

            TrackDTO result = trackService.getTrackById(TRACK_ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда трек не найден")
        void notFound() {
            when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> trackService.getTrackById(TRACK_ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(TRACK_ID.toString());
        }
    }

    @Nested
    @DisplayName("updateTrack")
    class UpdateTrack {

        @Test
        @DisplayName("обновляет трек и сохраняет изменения")
        void updatesWhenDataChanged() {
            Track track = track();
            TrackDTO originalDTO = trackDTO();
            TrackDTO updatedDTO = new TrackDTO(TRACK_ID, "Updated Track", "John", NOW, 300000, 30000,
                    BigDecimal.valueOf(3.99), albumDTO(), mediaTypeDTO(), genreDTO());

            when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.of(track));
            // первый вызов — сравнение, второй — после обновления
            when(trackMapper.toTrackDTO(track)).thenReturn(originalDTO, updatedDTO);

            TrackDTO result = trackService.updateTrack(updatedDTO);

            verify(trackMapper).updateTrack(track, updatedDTO);
            verify(trackRepository).save(track);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            Track track = track();
            TrackDTO dto = trackDTO();

            when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.of(track));
            when(trackMapper.toTrackDTO(track)).thenReturn(dto);

            TrackDTO result = trackService.updateTrack(dto);

            verify(trackRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если трек не найден")
        void notFound() {
            when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> trackService.updateTrack(trackDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteTrackById")
    class DeleteTrackById {

        @Test
        @DisplayName("успешно удаляет трек")
        void success() {
            Track track = track();
            TrackDTO expected = trackDTO();

            when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.of(track));
            when(trackMapper.toTrackDTO(track)).thenReturn(expected);

            TrackDTO result = trackService.deleteTrackById(TRACK_ID);

            verify(trackRepository).deleteById(TRACK_ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если трек не найден")
        void notFound() {
            when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> trackService.deleteTrackById(TRACK_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getAllTracksByAlbumId")
    class GetAllTracksByAlbumId {

        @Test
        @DisplayName("возвращает список треков по id альбома")
        void success() {
            List<Track> tracks = List.of(track());
            TrackDTO dto = trackDTO();

            when(trackRepository.searchByAlbumId(ALBUM_ID)).thenReturn(tracks);
            when(trackMapper.toTrackDTOs(tracks)).thenReturn(List.of(dto));

            List<TrackDTO> result = trackService.getAllTracksByAlbumId(ALBUM_ID);

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("возвращает пустой список если треков нет")
        void empty() {
            when(trackRepository.searchByAlbumId(ALBUM_ID)).thenReturn(Collections.emptyList());
            when(trackMapper.toTrackDTOs(Collections.emptyList())).thenReturn(Collections.emptyList());

            List<TrackDTO> result = trackService.getAllTracksByAlbumId(ALBUM_ID);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("getAllTracksByGenreId")
    class GetAllTracksByGenreId {

        @Test
        @DisplayName("возвращает список треков по id жанра")
        void success() {
            List<Track> tracks = List.of(track());
            TrackDTO dto = trackDTO();

            when(trackRepository.searchByGenreId(GENRE_ID)).thenReturn(tracks);
            when(trackMapper.toTrackDTOs(tracks)).thenReturn(List.of(dto));

            List<TrackDTO> result = trackService.getAllTracksByGenreId(GENRE_ID);

            assertThat(result).containsExactly(dto);
        }
    }

    @Nested
    @DisplayName("getAllTracksByMediaTypeId")
    class GetAllTracksByMediaTypeId {

        @Test
        @DisplayName("возвращает список треков по id медиа-типа")
        void success() {
            List<Track> tracks = List.of(track());
            TrackDTO dto = trackDTO();

            when(trackRepository.searchByMediaTypeId(MEDIA_TYPE_ID)).thenReturn(tracks);
            when(trackMapper.toTrackDTOs(tracks)).thenReturn(List.of(dto));

            List<TrackDTO> result = trackService.getAllTracksByMediaTypeId(MEDIA_TYPE_ID);

            assertThat(result).containsExactly(dto);
        }
    }

    @Nested
    @DisplayName("getAllTracksByArtistId")
    class GetAllTracksByArtistId {

        @Test
        @DisplayName("возвращает треки всех альбомов артиста по id")
        void success() {
            AlbumDTO albumDto = albumDTO();
            List<Track> tracks = List.of(track());
            TrackDTO dto = trackDTO();

            when(albumService.getAllAlbumsByArtistId(ARTIST_ID)).thenReturn(List.of(albumDto));
            when(trackRepository.searchByAlbumId(ALBUM_ID)).thenReturn(tracks);
            when(trackMapper.toTrackDTOs(any())).thenReturn(List.of(dto));

            List<TrackDTO> result = trackService.getAllTracksByArtistId(ARTIST_ID);

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("возвращает пустой список если у артиста нет альбомов")
        void noAlbums() {
            when(albumService.getAllAlbumsByArtistId(ARTIST_ID)).thenReturn(Collections.emptyList());
            when(trackMapper.toTrackDTOs(any())).thenReturn(Collections.emptyList());

            List<TrackDTO> result = trackService.getAllTracksByArtistId(ARTIST_ID);

            assertThat(result).isEmpty();
            verify(trackRepository, never()).searchByAlbumId(any());
        }
    }

    @Nested
    @DisplayName("getAllTracksByArtistPseudonym")
    class GetAllTracksByArtistPseudonym {

        @Test
        @DisplayName("возвращает треки через albumService по псевдониму артиста")
        void success() {
            AlbumDTO albumDto = albumDTO();
            List<Track> tracks = List.of(track());
            TrackDTO dto = trackDTO();

            when(albumService.getAlbumsByArtistPseudonym("JD")).thenReturn(List.of(albumDto));
            when(trackRepository.searchByAlbumId(ALBUM_ID)).thenReturn(tracks);
            when(trackMapper.toTrackDTOs(any())).thenReturn(List.of(dto));

            List<TrackDTO> result = trackService.getAllTracksByArtistPseudonym("JD");

            assertThat(result).containsExactly(dto);
        }
    }
}
