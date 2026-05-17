package digital.store.jdbc.tests;

import com.github.javafaker.Faker;
import digital.store.jdbc.model.*;
import digital.store.jdbc.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Owner("ObvintcevAE")
@DisplayName("Track: JDBC тесты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TrackTest extends BaseTest {

    // ─── предусловия: 4 сущности для FK-цепочки artist→album + genre + mediaType
    private ArtistDto    artist;
    private AlbumDto     album;
    private GenreDto     genre;
    private MediaTypeDto mediaType;
    private TrackDto     track;

    @BeforeAll
    void setup() {
        artist    = artistSteps.insertArtist(DataUtil.getArtistDto());
        album     = albumSteps.insertAlbum(DataUtil.getAlbumDto(artist.getArtistId()));
        genre     = genreSteps.insertGenre(DataUtil.getGenreDto());
        mediaType = mediaTypeSteps.insertMediaType(DataUtil.getMediaTypeDto());
    }

    @Test
    @DisplayName("testA — artist→album + genre + mediaType → INSERT track → SELECT → данные совпадают")
    @Description("Бизнес-цепочка из 4 зависимостей: album(artist), genre, mediaType → вставляем трек, проверяем все поля")
    void testA_insertAndSelect() {
        track = trackSteps.insertTrack(
                DataUtil.getTrackDto(album.getAlbumId(), genre.getGenreId(), mediaType.getMediaTypeId())
        );
        TrackDto fromDb = trackSteps.selectTrackById(track.getTrackId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("трек должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getTrackId()).isEqualTo(track.getTrackId());
        soft.assertThat(fromDb.getName()).isEqualTo(track.getName());
        soft.assertThat(fromDb.getAuthor()).isEqualTo(track.getAuthor());
        soft.assertThat(fromDb.getMilliseconds()).isEqualTo(track.getMilliseconds());
        soft.assertThat(fromDb.getBytes()).isEqualTo(track.getBytes());
        soft.assertThat(fromDb.getUnitPrice().compareTo(track.getUnitPrice())).isZero();
        soft.assertThat(fromDb.getAlbumId()).isEqualTo(album.getAlbumId());
        soft.assertThat(fromDb.getGenreId()).isEqualTo(genre.getGenreId());
        soft.assertThat(fromDb.getMediaTypeId()).isEqualTo(mediaType.getMediaTypeId());
        soft.assertThat(fromDb.getCreatedAt().toInstant().truncatedTo(ChronoUnit.SECONDS))
                .isEqualTo(track.getCreatedAt().toInstant().truncatedTo(ChronoUnit.SECONDS));
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE name/author/unitPrice → SELECT → изменения применились")
    @Description("Меняем название, автора и цену трека, проверяем обновление через SELECT")
    void testB_update() {
        TrackDto toUpdate = TrackDto.builder()
                .trackId(track.getTrackId())
                .name("Updated_" + Faker.instance().lordOfTheRings().location())
                .author(Faker.instance().artist().name())
                .createdAt(track.getCreatedAt())
                .milliseconds(Faker.instance().number().numberBetween(120000, 300000))
                .bytes(Faker.instance().number().numberBetween(2000000, 8000000))
                .unitPrice(BigDecimal.ONE)
                .albumId(track.getAlbumId())
                .genreId(track.getGenreId())
                .mediaTypeId(track.getMediaTypeId())
                .build();
        trackSteps.updateTrack(toUpdate);
        TrackDto fromDb = trackSteps.selectTrackById(track.getTrackId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getName()).isEqualTo(toUpdate.getName());
        soft.assertThat(fromDb.getAuthor()).isEqualTo(toUpdate.getAuthor());
        soft.assertThat(fromDb.getUnitPrice().compareTo(BigDecimal.ONE)).isZero();
        soft.assertAll();
    }
}
