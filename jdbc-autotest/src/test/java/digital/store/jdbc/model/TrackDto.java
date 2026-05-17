package digital.store.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackDto {

    private UUID trackId;
    private String name;
    private String author;
    private ZonedDateTime createdAt;
    private Integer milliseconds;
    private Integer bytes;
    private BigDecimal unitPrice;
    private UUID albumId;
    private UUID genreId;
    private UUID mediaTypeId;

}
