package digital.store.jdbctemplate.tests;

import com.github.javafaker.Faker;
import digital.store.jdbctemplate.model.MediaTypeDto;
import digital.store.jdbctemplate.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

@Owner("ObvintcevAE")
@DisplayName("MediaType: JdbcTemplate тесты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MediaTypeTest extends BaseTest {

    private MediaTypeDto mediaType;

    @Test
    @DisplayName("testA — INSERT → SELECT → данные совпадают")
    @Description("Создаём медиа-тип через JdbcTemplate и проверяем корректность всех полей в БД")
    void testA_insertAndSelect() {
        mediaType = mediaTypeSteps.insertMediaType(DataUtil.getMediaTypeDto());
        MediaTypeDto fromDb = mediaTypeSteps.selectMediaTypeById(mediaType.getMediaTypeId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("медиа-тип должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getMediaTypeId()).isEqualTo(mediaType.getMediaTypeId());
        soft.assertThat(fromDb.getName()).isEqualTo(mediaType.getName());
        soft.assertThat(fromDb.getCreatedAt()).isEqualTo(mediaType.getCreatedAt());
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE name → SELECT → изменения применились")
    @Description("Меняем название медиа-типа, проверяем обновление через SELECT")
    void testB_update() {
        MediaTypeDto toUpdate = MediaTypeDto.builder()
                .mediaTypeId(mediaType.getMediaTypeId())
                .name("Updated_" + Faker.instance().company().buzzword() + "_" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8))
                .createdAt(mediaType.getCreatedAt())
                .build();
        mediaTypeSteps.updateMediaType(toUpdate);
        MediaTypeDto fromDb = mediaTypeSteps.selectMediaTypeById(mediaType.getMediaTypeId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getName()).isEqualTo(toUpdate.getName());
        soft.assertAll();
    }
}
