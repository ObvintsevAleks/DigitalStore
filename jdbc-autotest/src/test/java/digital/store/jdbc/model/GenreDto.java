package digital.store.jdbc.model;

import digital.store.jdbc.model.enumpack.GenreDirection;
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
public class GenreDto {

    private UUID genreId;
    private String name;
    private LocalDate createdAt;
    private GenreDirection genreDirection;

}
