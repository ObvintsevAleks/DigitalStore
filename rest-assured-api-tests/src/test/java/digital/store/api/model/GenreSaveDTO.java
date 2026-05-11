package digital.store.api.model;

import digital.store.api.model.enumpack.GenreDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreSaveDTO {

    private String name;
    private LocalDate createdAt;
    private GenreDirection genreDirection;

}
