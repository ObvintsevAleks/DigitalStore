package digital.store.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceLineDto {

    private UUID invoiceLineId;
    private BigDecimal unitPrice;
    private Integer quantity;
    private UUID invoiceId;
    private UUID trackId;

}
