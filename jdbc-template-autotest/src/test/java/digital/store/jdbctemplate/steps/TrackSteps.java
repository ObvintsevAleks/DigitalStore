package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.TrackDto;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.OffsetDateTime;
import java.util.UUID;

public class TrackSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<TrackDto> TRACK_MAPPER = (rs, rowNum) -> TrackDto.builder()
            .trackId(rs.getObject("track_id", UUID.class))
            .name(rs.getString("name"))
            .author(rs.getString("author"))
            .createdAt(rs.getObject("created_at", OffsetDateTime.class).toZonedDateTime())
            .milliseconds(rs.getInt("milliseconds"))
            .bytes(rs.getInt("bytes"))
            .unitPrice(rs.getBigDecimal("unit_price"))
            .albumId(rs.getObject("album_id", UUID.class))
            .genreId(rs.getObject("genre_id", UUID.class))
            .mediaTypeId(rs.getObject("media_type_id", UUID.class))
            .build();

    public TrackSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем трек: name={track.name}, albumId={track.albumId}")
    public TrackDto insertTrack(TrackDto track) {
        jdbcTemplate.update(
                "INSERT INTO track (track_id, name, author, created_at, milliseconds, bytes, unit_price, album_id, genre_id, media_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                track.getTrackId(),
                track.getName(),
                track.getAuthor(),
                track.getCreatedAt().toOffsetDateTime(),
                track.getMilliseconds(),
                track.getBytes(),
                track.getUnitPrice(),
                track.getAlbumId(),
                track.getGenreId(),
                track.getMediaTypeId()
        );
        return track;
    }

    @Step("[JdbcTemplate шаг] Получаем трек по id={id}")
    public TrackDto selectTrackById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT track_id, name, author, created_at, milliseconds, bytes, unit_price, album_id, genre_id, media_type_id FROM track WHERE track_id = ?",
                TRACK_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем трек: name={track.name}, unitPrice={track.unitPrice}")
    public TrackDto updateTrack(TrackDto track) {
        jdbcTemplate.update(
                "UPDATE track SET name = ?, author = ?, created_at = ?, milliseconds = ?, bytes = ?, unit_price = ?, album_id = ?, genre_id = ?, media_type_id = ? WHERE track_id = ?",
                track.getName(),
                track.getAuthor(),
                track.getCreatedAt().toOffsetDateTime(),
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
