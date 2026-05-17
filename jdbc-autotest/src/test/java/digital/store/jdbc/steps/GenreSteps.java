package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.GenreDto;
import digital.store.jdbc.model.enumpack.GenreDirection;
import io.qameta.allure.Step;

import java.util.UUID;

public class GenreSteps {

    private final DbConnection db;

    public GenreSteps(DbConnection db) {
        this.db = db;
    }

    @Step("Вставляем жанр в БД: name={genre.name}, direction={genre.genreDirection}")
    public GenreDto insertGenre(GenreDto genre) {
        db.executeUpdate(
                "INSERT INTO genre (genre_id, name, created_at, genre_direction) VALUES (?, ?, ?, ?)",
                genre.getGenreId(),
                genre.getName(),
                genre.getCreatedAt(),
                genre.getGenreDirection().name()
        );
        return genre;
    }

    @Step("Получаем жанр из БД по id={id}")
    public GenreDto selectGenreById(UUID id) {
        return db.executeQuery(
                "SELECT genre_id, name, created_at, genre_direction FROM genre WHERE genre_id = ?",
                rs -> {
                    if (rs.next()) {
                        return GenreDto.builder()
                                .genreId(rs.getObject("genre_id", UUID.class))
                                .name(rs.getString("name"))
                                .createdAt(rs.getDate("created_at").toLocalDate())
                                .genreDirection(GenreDirection.valueOf(rs.getString("genre_direction")))
                                .build();
                    }
                    return null;
                },
                id
        );
    }

    @Step("Обновляем жанр в БД: name={genre.name}, direction={genre.genreDirection}")
    public GenreDto updateGenre(GenreDto genre) {
        db.executeUpdate(
                "UPDATE genre SET name = ?, created_at = ?, genre_direction = ? WHERE genre_id = ?",
                genre.getName(),
                genre.getCreatedAt(),
                genre.getGenreDirection().name(),
                genre.getGenreId()
        );
        return genre;
    }
}
