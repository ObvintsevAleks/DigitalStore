package digital.store.api.retrofit.model;

import digital.store.api.retrofit.model.enumpack.GenreDirection;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO extends SomeObjectWithId {

    private UUID id;
    private String name;
    private LocalDate createdAt;
    private GenreDirection genreDirection;

}
