package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.InvoiceDto;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.OffsetDateTime;
import java.util.UUID;

public class InvoiceSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<InvoiceDto> INVOICE_MAPPER = (rs, rowNum) -> InvoiceDto.builder()
            .invoiceId(rs.getObject("invoice_id", UUID.class))
            .invoiceDate(rs.getObject("invoice_date", OffsetDateTime.class).toZonedDateTime())
            .billingAddress(rs.getString("billing_address"))
            .billingCity(rs.getString("billing_city"))
            .billingState(rs.getString("billing_state"))
            .billingCountry(rs.getString("billing_country"))
            .billingPostalCode(rs.getString("billing_postal_code"))
            .total(rs.getBigDecimal("total"))
            .customerId(rs.getObject("customer_id", UUID.class))
            .employeeId(rs.getObject("employee_id", UUID.class))
            .build();

    public InvoiceSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем заказ: customerId={invoice.customerId}, total={invoice.total}")
    public InvoiceDto insertInvoice(InvoiceDto invoice) {
        jdbcTemplate.update(
                "INSERT INTO invoice (invoice_id, invoice_date, billing_address, billing_city, billing_state, billing_country, billing_postal_code, total, customer_id, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                invoice.getInvoiceId(),
                invoice.getInvoiceDate().toOffsetDateTime(),
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

    @Step("[JdbcTemplate шаг] Получаем заказ по id={id}")
    public InvoiceDto selectInvoiceById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT invoice_id, invoice_date, billing_address, billing_city, billing_state, billing_country, billing_postal_code, total, customer_id, employee_id FROM invoice WHERE invoice_id = ?",
                INVOICE_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем заказ: total={invoice.total}, billingAddress={invoice.billingAddress}")
    public InvoiceDto updateInvoice(InvoiceDto invoice) {
        jdbcTemplate.update(
                "UPDATE invoice SET invoice_date = ?, billing_address = ?, billing_city = ?, billing_state = ?, billing_country = ?, billing_postal_code = ?, total = ?, customer_id = ?, employee_id = ? WHERE invoice_id = ?",
                invoice.getInvoiceDate().toOffsetDateTime(),
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
