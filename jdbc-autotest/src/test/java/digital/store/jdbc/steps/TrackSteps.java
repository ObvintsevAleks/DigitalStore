package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.TrackDto;
import io.qameta.allure.Step;

import java.util.UUID;

public class TrackSteps {

    private final DbConnection db;

    public TrackSteps(DbConnection db) {
        this.db = db;
    }

    @Step("Вставляем трек в БД: name={track.name}, albumId={track.albumId}")
    public TrackDto insertTrack(TrackDto track) {
        db.executeUpdate(
                "INSERT INTO track (track_id, name, author, created_at, milliseconds, bytes, unit_price, album_id, genre_id, media_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                track.getTrackId(),
                track.getName(),
                track.getAuthor(),
                track.getCreatedAt(),
                track.getMilliseconds(),
                track.getBytes(),
                track.getUnitPrice(),
                track.getAlbumId(),
                track.getGenreId(),
                track.getMediaTypeId()
        );
        return track;
    }

    @Step("Получаем трек из БД по id={id}")
    public TrackDto selectTrackById(UUID id) {
        return db.executeQuery(
                "SELECT track_id, name, author, created_at, milliseconds, bytes, unit_price, album_id, genre_id, media_type_id FROM track WHERE track_id = ?",
                rs -> {
                    if (rs.next()) {
                        return TrackDto.builder()
                                .trackId(rs.getObject("track_id", UUID.class))
                                .name(rs.getString("name"))
                                .author(rs.getString("author"))
                                .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class).toZonedDateTime())
                                .milliseconds(rs.getInt("milliseconds"))
                                .bytes(rs.getInt("bytes"))
                                .unitPrice(rs.getBigDecimal("unit_price"))
                                .albumId(rs.getObject("album_id", UUID.class))
                                .genreId(rs.getObject("genre_id", UUID.class))
                                .mediaTypeId(rs.getObject("media_type_id", UUID.class))
                                .build();
                    }
                    return null;
                },
                id
        );
    }

    @Step("Обновляем трек в БД: name={track.name}")
    public TrackDto updateTrack(TrackDto track) {
        db.executeUpdate(
                "UPDATE track SET name = ?, author = ?, created_at = ?, milliseconds = ?, bytes = ?, unit_price = ?, album_id = ?, genre_id = ?, media_type_id = ? WHERE track_id = ?",
                track.getName(),
                track.getAuthor(),
                track.getCreatedAt(),
                track.getMilliseconds(),
                track.getBytes(),
                track.getUnitPrice(),
                track.getAlbumId(),
                track.getGenreId(),
                track.getMediaTypeId(),
                track.getTrackId()
        );
        return track;
    }
}
