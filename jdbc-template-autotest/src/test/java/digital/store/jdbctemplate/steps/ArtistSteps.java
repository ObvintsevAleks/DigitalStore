package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.ArtistDto;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.UUID;

public class ArtistSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ArtistDto> ARTIST_MAPPER = (rs, rowNum) -> ArtistDto.builder()
            .artistId(rs.getObject("artist_id", UUID.class))
            .name(rs.getString("name"))
            .surname(rs.getString("surname"))
            .pseudonym(rs.getString("pseudonym"))
            .birthDate(rs.getDate("birth_date").toLocalDate())
            .build();

    public ArtistSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем артиста: name={artist.name}, pseudonym={artist.pseudonym}")
    public ArtistDto insertArtist(ArtistDto artist) {
        jdbcTemplate.update(
                "INSERT INTO artist (artist_id, name, surname, pseudonym, birth_date) VALUES (?, ?, ?, ?, ?)",
                artist.getArtistId(),
                artist.getName(),
                artist.getSurname(),
                artist.getPseudonym(),
                Date.valueOf(artist.getBirthDate())
        );
        return artist;
    }

    @Step("[JdbcTemplate шаг] Получаем артиста по id={id}")
    public ArtistDto selectArtistById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT artist_id, name, surname, pseudonym, birth_date FROM artist WHERE artist_id = ?",
                ARTIST_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем артиста: name={artist.name}, pseudonym={artist.pseudonym}")
    public ArtistDto updateArtist(ArtistDto artist) {
        jdbcTemplate.update(
                "UPDATE artist SET name = ?, surname = ?, pseudonym = ?, birth_date = ? WHERE artist_id = ?",
                artist.getName(),
                artist.getSurname(),
                artist.getPseudonym(),
                Date.valueOf(artist.getBirthDate()),
                artist.getArtistId()
        );
        return artist;
    }
}
