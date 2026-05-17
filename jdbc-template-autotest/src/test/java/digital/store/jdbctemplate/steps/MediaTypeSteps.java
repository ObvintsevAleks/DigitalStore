package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.MediaTypeDto;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.UUID;

public class MediaTypeSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<MediaTypeDto> MEDIA_TYPE_MAPPER = (rs, rowNum) -> MediaTypeDto.builder()
            .mediaTypeId(rs.getObject("media_type_id", UUID.class))
            .name(rs.getString("name"))
            .createdAt(rs.getDate("created_at").toLocalDate())
            .build();

    public MediaTypeSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем медиа-тип: name={mediaType.name}")
    public MediaTypeDto insertMediaType(MediaTypeDto mediaType) {
        jdbcTemplate.update(
                "INSERT INTO media_type (media_type_id, name, created_at) VALUES (?, ?, ?)",
                mediaType.getMediaTypeId(),
                mediaType.getName(),
                Date.valueOf(mediaType.getCreatedAt())
        );
        return mediaType;
    }

    @Step("[JdbcTemplate шаг] Получаем медиа-тип по id={id}")
    public MediaTypeDto selectMediaTypeById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT media_type_id, name, created_at FROM media_type WHERE media_type_id = ?",
                MEDIA_TYPE_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем медиа-тип: name={mediaType.name}")
    public MediaTypeDto updateMediaType(MediaTypeDto mediaType) {
        jdbcTemplate.update(
                "UPDATE media_type SET name = ?, created_at = ? WHERE media_type_id = ?",
                mediaType.getName(),
                Date.valueOf(mediaType.getCreatedAt()),
                mediaType.getMediaTypeId()
        );
        return mediaType;
    }
}
