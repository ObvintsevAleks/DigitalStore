package digital.store.jdbctemplate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {

    private UUID artistId;
    private String name;
    private String surname;
    private String pseudonym;
    private LocalDate birthDate;

}
