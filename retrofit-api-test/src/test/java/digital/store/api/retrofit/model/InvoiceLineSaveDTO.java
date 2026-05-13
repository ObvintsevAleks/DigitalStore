package digital.store.api.retrofit.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceLineSaveDTO {

    private InvoiceDTO invoice;
    private TrackDTO track;
    private BigDecimal unitPrice;
    private Integer quantity;

    @EqualsAndHashCode.Include
    private BigDecimal unitPrice() {
        return this.unitPrice.stripTrailingZeros();
    }

}
