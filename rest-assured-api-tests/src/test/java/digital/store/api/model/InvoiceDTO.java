package digital.store.api.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDTO extends SomeObjectWithId {

    private UUID id;
    @EqualsAndHashCode.Exclude
    private ZonedDateTime invoiceDate;
    private String billingAddress;
    private String billingCity;
    private String billingState;
    private String billingCountry;
    private String billingPostalCode;

    private BigDecimal total;
    private CustomerDTO customer;
    private EmployeeDTO employee;

    @EqualsAndHashCode.Include
    private BigDecimal total() {
        return this.total.stripTrailingZeros();
    }

}
