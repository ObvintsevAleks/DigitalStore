package digital.store.api.retrofit.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceLineDTO extends SomeObjectWithId {

    private UUID id;
    private BigDecimal unitPrice;
    private Integer quantity;
    private InvoiceDTO invoice;
    private TrackDTO track;

    @EqualsAndHashCode.Include
    private BigDecimal unitPrice() {
        return this.unitPrice.stripTrailingZeros();
    }

}
