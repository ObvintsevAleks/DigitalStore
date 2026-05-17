package digital.store.jdbctemplate.tests;

import com.github.javafaker.Faker;
import digital.store.jdbctemplate.model.ArtistDto;
import digital.store.jdbctemplate.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

@Owner("ObvintcevAE")
@DisplayName("Artist: JdbcTemplate тесты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ArtistTest extends BaseTest {

    private ArtistDto artist;

    @Test
    @DisplayName("testA — INSERT → SELECT → данные совпадают")
    @Description("Создаём артиста через JdbcTemplate и проверяем корректность всех полей в БД")
    void testA_insertAndSelect() {
        artist = artistSteps.insertArtist(DataUtil.getArtistDto());
        ArtistDto fromDb = artistSteps.selectArtistById(artist.getArtistId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("артист должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getArtistId()).isEqualTo(artist.getArtistId());
        soft.assertThat(fromDb.getName()).isEqualTo(artist.getName());
        soft.assertThat(fromDb.getSurname()).isEqualTo(artist.getSurname());
        soft.assertThat(fromDb.getPseudonym()).isEqualTo(artist.getPseudonym());
        soft.assertThat(fromDb.getBirthDate()).isEqualTo(artist.getBirthDate());
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE name/surname/pseudonym → SELECT → изменения применились")
    @Description("Меняем имя, фамилию и псевдоним артиста, проверяем обновление через SELECT")
    void testB_update() {
        ArtistDto toUpdate = ArtistDto.builder()
                .artistId(artist.getArtistId())
                .name(Faker.instance().artist().name())
                .surname(Faker.instance().name().lastName())
                .pseudonym("Updated_" + artist.getPseudonym())
                .birthDate(artist.getBirthDate())
                .build();
        artistSteps.updateArtist(toUpdate);
        ArtistDto fromDb = artistSteps.selectArtistById(artist.getArtistId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getName()).isEqualTo(toUpdate.getName());
        soft.assertThat(fromDb.getSurname()).isEqualTo(toUpdate.getSurname());
        soft.assertThat(fromDb.getPseudonym()).isEqualTo(toUpdate.getPseudonym());
        soft.assertAll();
    }
}
