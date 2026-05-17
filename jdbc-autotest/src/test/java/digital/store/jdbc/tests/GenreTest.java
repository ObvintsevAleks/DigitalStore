package digital.store.jdbc.tests;

import com.github.javafaker.Faker;
import digital.store.jdbc.model.GenreDto;
import digital.store.jdbc.model.enumpack.GenreDirection;
import digital.store.jdbc.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

@Owner("ObvintcevAE")
@DisplayName("Genre: JDBC тесты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class GenreTest extends BaseTest {

    private GenreDto genre;

    @Test
    @DisplayName("testA — INSERT → SELECT → данные совпадают")
    @Description("Создаём жанр через JDBC и проверяем корректность всех полей в БД")
    void testA_insertAndSelect() {
        genre = genreSteps.insertGenre(DataUtil.getGenreDto());
        GenreDto fromDb = genreSteps.selectGenreById(genre.getGenreId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("жанр должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getGenreId()).isEqualTo(genre.getGenreId());
        soft.assertThat(fromDb.getName()).isEqualTo(genre.getName());
        soft.assertThat(fromDb.getCreatedAt()).isEqualTo(genre.getCreatedAt());
        soft.assertThat(fromDb.getGenreDirection()).isEqualTo(genre.getGenreDirection());
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE name/direction → SELECT → изменения применились")
    @Description("Меняем название и направление жанра, проверяем обновление через SELECT")
    void testB_update() {
        GenreDto toUpdate = GenreDto.builder()
                .genreId(genre.getGenreId())
                .name("Updated_" + Faker.instance().music().genre() + "_" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8))
                .createdAt(genre.getCreatedAt())
                .genreDirection(GenreDirection.POPULAR)
                .build();
        genreSteps.updateGenre(toUpdate);
        GenreDto fromDb = genreSteps.selectGenreById(genre.getGenreId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getName()).isEqualTo(toUpdate.getName());
        soft.assertThat(fromDb.getGenreDirection()).isEqualTo(GenreDirection.POPULAR);
        soft.assertAll();
    }
}
