package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.InvoiceLineDto;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;

public class InvoiceLineSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<InvoiceLineDto> INVOICE_LINE_MAPPER = (rs, rowNum) -> InvoiceLineDto.builder()
            .invoiceLineId(rs.getObject("invoice_line_id", UUID.class))
            .unitPrice(rs.getBigDecimal("unit_price"))
            .quantity(rs.getInt("quantity"))
            .invoiceId(rs.getObject("invoice_id", UUID.class))
            .trackId(rs.getObject("track_id", UUID.class))
            .build();

    public InvoiceLineSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем позицию заказа: invoiceId={invoiceLine.invoiceId}, trackId={invoiceLine.trackId}")
    public InvoiceLineDto insertInvoiceLine(InvoiceLineDto invoiceLine) {
        jdbcTemplate.update(
                "INSERT INTO invoice_line (invoice_line_id, unit_price, quantity, invoice_id, track_id) VALUES (?, ?, ?, ?, ?)",
                invoiceLine.getInvoiceLineId(),
                invoiceLine.getUnitPrice(),
                invoiceLine.getQuantity(),
                invoiceLine.getInvoiceId(),
                invoiceLine.getTrackId()
        );
        return invoiceLine;
    }

    @Step("[JdbcTemplate шаг] Получаем позицию заказа по id={id}")
    public InvoiceLineDto selectInvoiceLineById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT invoice_line_id, unit_price, quantity, invoice_id, track_id FROM invoice_line WHERE invoice_line_id = ?",
                INVOICE_LINE_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем позицию заказа: quantity={invoiceLine.quantity}, unitPrice={invoiceLine.unitPrice}")
    public InvoiceLineDto updateInvoiceLine(InvoiceLineDto invoiceLine) {
        jdbcTemplate.update(
                "UPDATE invoice_line SET unit_price = ?, quantity = ?, invoice_id = ?, track_id = ? WHERE invoice_line_id = ?",
                invoiceLine.getUnitPrice(),
                invoiceLine.getQuantity(),
                invoiceLine.getInvoiceId(),
                invoiceLine.getTrackId(),
                invoiceLine.getInvoiceLineId()
        );
        return invoiceLine;
    }
}
