package digital.store.jdbc.tests;

import com.github.javafaker.Faker;
import digital.store.jdbc.model.AlbumDto;
import digital.store.jdbc.model.ArtistDto;
import digital.store.jdbc.model.enumpack.AlbumType;
import digital.store.jdbc.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.time.temporal.ChronoUnit;

@Owner("ObvintcevAE")
@DisplayName("Album: JDBC тесты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AlbumTest extends BaseTest {

    // ─── шаг 1: создаём артиста как предусловие для альбома ──────────────────
    private ArtistDto artist;
    private AlbumDto  album;

    @BeforeAll
    void setup() {
        artist = artistSteps.insertArtist(DataUtil.getArtistDto());
    }

    @Test
    @DisplayName("testA — шаг 1 берём artist → INSERT album → SELECT → данные совпадают")
    @Description("Бизнес-цепочка: artist (создан в @BeforeAll) → вставляем альбом, проверяем все поля")
    void testA_insertAndSelect() {
        album = albumSteps.insertAlbum(DataUtil.getAlbumDto(artist.getArtistId()));
        AlbumDto fromDb = albumSteps.selectAlbumById(album.getAlbumId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("альбом должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getAlbumId()).isEqualTo(album.getAlbumId());
        soft.assertThat(fromDb.getTitle()).isEqualTo(album.getTitle());
        soft.assertThat(fromDb.getAlbumType()).isEqualTo(album.getAlbumType());
        soft.assertThat(fromDb.getArtistId()).isEqualTo(artist.getArtistId());
        soft.assertThat(fromDb.getCreatedAt().toInstant().truncatedTo(ChronoUnit.SECONDS))
                .isEqualTo(album.getCreatedAt().toInstant().truncatedTo(ChronoUnit.SECONDS));
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE title/albumType → SELECT → изменения применились")
    @Description("Меняем название и тип альбома, проверяем обновление через SELECT")
    void testB_update() {
        AlbumDto toUpdate = AlbumDto.builder()
                .albumId(album.getAlbumId())
                .title("Updated_" + Faker.instance().elderScrolls().creature())
                .albumType(AlbumType.SINGLE)
                .createdAt(album.getCreatedAt())
                .artistId(album.getArtistId())
                .build();
        albumSteps.updateAlbum(toUpdate);
        AlbumDto fromDb = albumSteps.selectAlbumById(album.getAlbumId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getTitle()).isEqualTo(toUpdate.getTitle());
        soft.assertThat(fromDb.getAlbumType()).isEqualTo(AlbumType.SINGLE);
        soft.assertAll();
    }
}
