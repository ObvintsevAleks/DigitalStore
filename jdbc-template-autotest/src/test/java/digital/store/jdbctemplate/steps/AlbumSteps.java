package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.AlbumDto;
import digital.store.jdbctemplate.model.enumpack.AlbumType;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.OffsetDateTime;
import java.util.UUID;

public class AlbumSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<AlbumDto> ALBUM_MAPPER = (rs, rowNum) -> AlbumDto.builder()
            .albumId(rs.getObject("album_id", UUID.class))
            .title(rs.getString("title"))
            .albumType(AlbumType.valueOf(rs.getString("album_type")))
            .createdAt(rs.getObject("created_at", OffsetDateTime.class).toZonedDateTime())
            .artistId(rs.getObject("artist_id", UUID.class))
            .build();

    public AlbumSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем альбом: title={album.title}, artistId={album.artistId}")
    public AlbumDto insertAlbum(AlbumDto album) {
        jdbcTemplate.update(
                "INSERT INTO album (album_id, title, album_type, created_at, artist_id) VALUES (?, ?, ?, ?, ?)",
                album.getAlbumId(),
                album.getTitle(),
                album.getAlbumType().name(),
                album.getCreatedAt().toOffsetDateTime(),
                album.getArtistId()
        );
        return album;
    }

    @Step("[JdbcTemplate шаг] Получаем альбом по id={id}")
    public AlbumDto selectAlbumById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT album_id, title, album_type, created_at, artist_id FROM album WHERE album_id = ?",
                ALBUM_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем альбом: title={album.title}, albumType={album.albumType}")
    public AlbumDto updateAlbum(AlbumDto album) {
        jdbcTemplate.update(
                "UPDATE album SET title = ?, album_type = ?, created_at = ?, artist_id = ? WHERE album_id = ?",
                album.getTitle(),
                album.getAlbumType().name(),
                album.getCreatedAt().toOffsetDateTime(),
                album.getArtistId(),
                album.getAlbumId()
        );
        return album;
    }
}
