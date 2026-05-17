package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.InvoiceDto;
import io.qameta.allure.Step;

import java.util.UUID;

public class InvoiceSteps {

    private final DbConnection db;

    public InvoiceSteps(DbConnection db) {
        this.db = db;
    }

    @Step("[JDBC шаг] Вставляем заказ в БД: customerId={invoice.customerId}, total={invoice.total}")
    public InvoiceDto insertInvoice(InvoiceDto invoice) {
        db.executeUpdate(
                "INSERT INTO invoice (invoice_id, invoice_date, billing_address, billing_city, billing_state, billing_country, billing_postal_code, total, customer_id, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                invoice.getInvoiceId(),
                invoice.getInvoiceDate(),
                invoice.getBillingAddress(),
                invoice.getBillingCity(),
                invoice.getBillingState(),
                invoice.getBillingCountry(),
                invoice.getBillingPostalCode(),
                invoice.getTotal(),
                invoice.getCustomerId(),
                invoice.getEmployeeId()
        );
        return invoice;
    }

    @Step("[JDBC шаг] Получаем заказ из БД по id={id}")
    public InvoiceDto selectInvoiceById(UUID id) {
        return db.executeQuery(
                "SELECT invoice_id, invoice_date, billing_address, billing_city, billing_state, billing_country, billing_postal_code, total, customer_id, employee_id FROM invoice WHERE invoice_id = ?",
                rs -> {
                    if (rs.next()) {
                        return InvoiceDto.builder()
                                .invoiceId(rs.getObject("invoice_id", UUID.class))
                                .invoiceDate(rs.getObject("invoice_date", java.time.OffsetDateTime.class).toZonedDateTime())
                                .billingAddress(rs.getString("billing_address"))
                                .billingCity(rs.getString("billing_city"))
                                .billingState(rs.getString("billing_state"))
                                .billingCountry(rs.getString("billing_country"))
                                .billingPostalCode(rs.getString("billing_postal_code"))
                                .total(rs.getBigDecimal("total"))
                                .customerId(rs.getObject("customer_id", UUID.class))
                                .employeeId(rs.getObject("employee_id", UUID.class))
                                .build();
                    }
                    return null;
                },
                id
        );
    }

    @Step("[JDBC шаг] Обновляем заказ в БД: total={invoice.total}, billingAddress={invoice.billingAddress}")
    public InvoiceDto updateInvoice(InvoiceDto invoice) {
        db.executeUpdate(
                "UPDATE invoice SET invoice_date = ?, billing_address = ?, billing_city = ?, billing_state = ?, billing_country = ?, billing_postal_code = ?, total = ?, customer_id = ?, employee_id = ? WHERE invoice_id = ?",
                invoice.getInvoiceDate(),
                invoice.getBillingAddress(),
                invoice.getBillingCity(),
                invoice.getBillingState(),
                invoice.getBillingCountry(),
                invoice.getBillingPostalCode(),
                invoice.getTotal(),
                invoice.getCustomerId(),
                invoice.getEmployeeId(),
                invoice.getInvoiceId()
        );
        return invoice;
    }
}
