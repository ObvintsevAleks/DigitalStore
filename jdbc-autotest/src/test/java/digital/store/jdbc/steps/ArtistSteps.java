package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.ArtistDto;
import io.qameta.allure.Step;

import java.util.UUID;

public class ArtistSteps {

    private final DbConnection db;

    public ArtistSteps(DbConnection db) {
        this.db = db;
    }

    @Step("Вставляем артиста в БД: name={artist.name}, pseudonym={artist.pseudonym}")
    public ArtistDto insertArtist(ArtistDto artist) {
        db.executeUpdate(
                "INSERT INTO artist (artist_id, name, surname, pseudonym, birth_date) VALUES (?, ?, ?, ?, ?)",
                artist.getArtistId(),
                artist.getName(),
                artist.getSurname(),
                artist.getPseudonym(),
                artist.getBirthDate()
        );
        return artist;
    }

    @Step("Получаем артиста из БД по id={id}")
    public ArtistDto selectArtistById(UUID id) {
        return db.executeQuery(
                "SELECT artist_id, name, surname, pseudonym, birth_date FROM artist WHERE artist_id = ?",
                rs -> {
                    if (rs.next()) {
                        return ArtistDto.builder()
                                .artistId(rs.getObject("artist_id", UUID.class))
                                .name(rs.getString("name"))
                                .surname(rs.getString("surname"))
                                .pseudonym(rs.getString("pseudonym"))
                                .birthDate(rs.getDate("birth_date").toLocalDate())
                                .build();
                    }
                    return null;
                },
                id
        );
    }

    @Step("Обновляем артиста в БД: name={artist.name}, pseudonym={artist.pseudonym}")
    public ArtistDto updateArtist(ArtistDto artist) {
        db.executeUpdate(
                "UPDATE artist SET name = ?, surname = ?, pseudonym = ?, birth_date = ? WHERE artist_id = ?",
                artist.getName(),
                artist.getSurname(),
                artist.getPseudonym(),
                artist.getBirthDate(),
                artist.getArtistId()
        );
        return artist;
    }
}
