package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.InvoiceLineDto;
import io.qameta.allure.Step;

import java.util.UUID;

public class InvoiceLineSteps {

    private final DbConnection db;

    public InvoiceLineSteps(DbConnection db) {
        this.db = db;
    }

    @Step("Вставляем позицию заказа в БД: invoiceId={invoiceLine.invoiceId}, trackId={invoiceLine.trackId}")
    public InvoiceLineDto insertInvoiceLine(InvoiceLineDto invoiceLine) {
        db.executeUpdate(
                "INSERT INTO invoice_line (invoice_line_id, unit_price, quantity, invoice_id, track_id) VALUES (?, ?, ?, ?, ?)",
                invoiceLine.getInvoiceLineId(),
                invoiceLine.getUnitPrice(),
                invoiceLine.getQuantity(),
                invoiceLine.getInvoiceId(),
                invoiceLine.getTrackId()
        );
        return invoiceLine;
    }

    @Step("Получаем позицию заказа из БД по id={id}")
    public InvoiceLineDto selectInvoiceLineById(UUID id) {
        return db.executeQuery(
                "SELECT invoice_line_id, unit_price, quantity, invoice_id, track_id FROM invoice_line WHERE invoice_line_id = ?",
                rs -> {
                    if (rs.next()) {
                        return InvoiceLineDto.builder()
                                .invoiceLineId(rs.getObject("invoice_line_id", UUID.class))
                                .unitPrice(rs.getBigDecimal("unit_price"))
                                .quantity(rs.getInt("quantity"))
                                .invoiceId(rs.getObject("invoice_id", UUID.class))
                                .trackId(rs.getObject("track_id", UUID.class))
                                .build();
                    }
                    return null;
                },
                id
        );
    }

    @Step("Обновляем позицию заказа в БД: quantity={invoiceLine.quantity}, unitPrice={invoiceLine.unitPrice}")
    public InvoiceLineDto updateInvoiceLine(InvoiceLineDto invoiceLine) {
        db.executeUpdate(
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
