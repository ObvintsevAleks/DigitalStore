package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.MediaTypeDto;
import io.qameta.allure.Step;

import java.util.UUID;

public class MediaTypeSteps {

    private final DbConnection db;

    public MediaTypeSteps(DbConnection db) {
        this.db = db;
    }

    @Step("Вставляем медиа-тип в БД: name={mediaType.name}")
    public MediaTypeDto insertMediaType(MediaTypeDto mediaType) {
        db.executeUpdate(
                "INSERT INTO media_type (media_type_id, name, created_at) VALUES (?, ?, ?)",
                mediaType.getMediaTypeId(),
                mediaType.getName(),
                mediaType.getCreatedAt()
        );
        return mediaType;
    }

    @Step("Получаем медиа-тип из БД по id={id}")
    public MediaTypeDto selectMediaTypeById(UUID id) {
        return db.executeQuery(
                "SELECT media_type_id, name, created_at FROM media_type WHERE media_type_id = ?",
                rs -> {
                    if (rs.next()) {
                        return MediaTypeDto.builder()
                                .mediaTypeId(rs.getObject("media_type_id", UUID.class))
                                .name(rs.getString("name"))
                                .createdAt(rs.getDate("created_at").toLocalDate())
                                .build();
                    }
                    return null;
                },
                id
        );
    }

    @Step("Обновляем медиа-тип в БД: name={mediaType.name}")
    public MediaTypeDto updateMediaType(MediaTypeDto mediaType) {
        db.executeUpdate(
                "UPDATE media_type SET name = ?, created_at = ? WHERE media_type_id = ?",
                mediaType.getName(),
                mediaType.getCreatedAt(),
                mediaType.getMediaTypeId()
        );
        return mediaType;
    }
}
