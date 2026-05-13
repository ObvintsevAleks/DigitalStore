package digital.store.api.retrofit.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackDTO extends SomeObjectWithId {

    private UUID id;
    private String name;
    private String author;
    @EqualsAndHashCode.Exclude
    private ZonedDateTime createdAt;
    private Integer milliseconds;
    private Integer bytes;

    private BigDecimal unitPrice;

    private AlbumDTO album;
    private MediaTypeDTO mediaType;
    private GenreDTO genre;

    @EqualsAndHashCode.Include
    private BigDecimal unitPrice() {
        return this.unitPrice.stripTrailingZeros();
    }

}
