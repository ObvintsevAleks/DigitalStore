package digital.store.jdbc.model;

import digital.store.jdbc.model.enumpack.AlbumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {

    private UUID albumId;
    private String title;
    private AlbumType albumType;
    private ZonedDateTime createdAt;
    private UUID artistId;

}
