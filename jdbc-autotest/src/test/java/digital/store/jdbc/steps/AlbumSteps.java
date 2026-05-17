package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.AlbumDto;
import digital.store.jdbc.model.enumpack.AlbumType;
import io.qameta.allure.Step;

import java.util.UUID;

public class AlbumSteps {

    private final DbConnection db;

    public AlbumSteps(DbConnection db) {
        this.db = db;
    }

    @Step("Вставляем альбом в БД: title={album.title}, artistId={album.artistId}")
    public AlbumDto insertAlbum(AlbumDto album) {
        db.executeUpdate(
                "INSERT INTO album (album_id, title, album_type, created_at, artist_id) VALUES (?, ?, ?, ?, ?)",
                album.getAlbumId(),
                album.getTitle(),
                album.getAlbumType().name(),
                album.getCreatedAt(),
                album.getArtistId()
        );
        return album;
    }

    @Step("Получаем альбом из БД по id={id}")
    public AlbumDto selectAlbumById(UUID id) {
        return db.executeQuery(
                "SELECT album_id, title, album_type, created_at, artist_id FROM album WHERE album_id = ?",
                rs -> {
                    if (rs.next()) {
                        return AlbumDto.builder()
                                .albumId(rs.getObject("album_id", UUID.class))
                                .title(rs.getString("title"))
                                .albumType(AlbumType.valueOf(rs.getString("album_type")))
                                .createdAt(rs.getObject("created_at", java.time.OffsetDateTime.class).toZonedDateTime())
                                .artistId(rs.getObject("artist_id", UUID.class))
                                .build();
                    }
                    return null;
                },
                id
        );
    }

    @Step("Обновляем альбом в БД: title={album.title}, artistId={album.artistId}")
    public AlbumDto updateAlbum(AlbumDto album) {
        db.executeUpdate(
                "UPDATE album SET title = ?, album_type = ?, created_at = ?, artist_id = ? WHERE album_id = ?",
                album.getTitle(),
                album.getAlbumType().name(),
                album.getCreatedAt(),
                album.getArtistId(),
                album.getAlbumId()
        );
        return album;
    }
}
