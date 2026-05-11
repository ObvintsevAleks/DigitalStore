package digital.store.api.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaTypeDTO extends SomeObjectWithId {

    private UUID id;
    private String name;
    private LocalDate createdAt;

}
