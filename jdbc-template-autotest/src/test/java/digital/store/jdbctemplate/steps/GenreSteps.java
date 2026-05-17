package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.GenreDto;
import digital.store.jdbctemplate.model.enumpack.GenreDirection;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.UUID;

public class GenreSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<GenreDto> GENRE_MAPPER = (rs, rowNum) -> GenreDto.builder()
            .genreId(rs.getObject("genre_id", UUID.class))
            .name(rs.getString("name"))
            .createdAt(rs.getDate("created_at").toLocalDate())
            .genreDirection(GenreDirection.valueOf(rs.getString("genre_direction")))
            .build();

    public GenreSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем жанр: name={genre.name}, direction={genre.genreDirection}")
    public GenreDto insertGenre(GenreDto genre) {
        jdbcTemplate.update(
                "INSERT INTO genre (genre_id, name, created_at, genre_direction) VALUES (?, ?, ?, ?)",
                genre.getGenreId(),
                genre.getName(),
                Date.valueOf(genre.getCreatedAt()),
                genre.getGenreDirection().name()
        );
        return genre;
    }

    @Step("[JdbcTemplate шаг] Получаем жанр по id={id}")
    public GenreDto selectGenreById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT genre_id, name, created_at, genre_direction FROM genre WHERE genre_id = ?",
                GENRE_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем жанр: name={genre.name}, direction={genre.genreDirection}")
    public GenreDto updateGenre(GenreDto genre) {
        jdbcTemplate.update(
                "UPDATE genre SET name = ?, created_at = ?, genre_direction = ? WHERE genre_id = ?",
                genre.getName(),
                Date.valueOf(genre.getCreatedAt()),
                genre.getGenreDirection().name(),
                genre.getGenreId()
        );
        return genre;
    }
}
