package digital.store.api.retrofit.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistDTO extends SomeObjectWithId {

    private UUID id;
    private String name;
    private String surname;
    private String pseudonym;
    private LocalDate birthDate;

}
