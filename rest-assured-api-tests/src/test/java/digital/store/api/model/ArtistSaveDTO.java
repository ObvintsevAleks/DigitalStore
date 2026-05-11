package digital.store.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistSaveDTO {

    private String name;
    private String surname;
    private String pseudonym;
    private LocalDate birthDate;
}
