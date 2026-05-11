package digital.store.api.model;

import digital.store.api.model.enumpack.AlbumType;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumDTO extends SomeObjectWithId {

    private UUID id;
    private String title;
    private AlbumType albumType;
    @EqualsAndHashCode.Exclude
    private ZonedDateTime createdAt;
    private ArtistDTO artist;

}
