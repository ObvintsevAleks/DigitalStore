package digital.store.api.model;

import digital.store.api.model.enumpack.AlbumType;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumSaveDto {

    private String title;
    private AlbumType albumType;
    @EqualsAndHashCode.Exclude
    private ZonedDateTime createdAt;
    private ArtistDTO artist;

}
