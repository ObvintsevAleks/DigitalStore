package digital.store.jdbctemplate.model;

import digital.store.jdbctemplate.model.enumpack.GenreDirection;
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
