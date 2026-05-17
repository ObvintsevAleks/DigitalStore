package digital.store.jdbctemplate.tests;

import com.github.javafaker.Faker;
import digital.store.jdbctemplate.model.CustomerDto;
import digital.store.jdbctemplate.model.EmployeeDto;
import digital.store.jdbctemplate.model.InvoiceDto;
import digital.store.jdbctemplate.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

@Owner("ObvintcevAE")
@DisplayName("Invoice: JdbcTemplate тесты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class InvoiceTest extends BaseTest {

    // ─── предусловия: customer и employee для FK-цепочки
    private CustomerDto customer;
    private EmployeeDto employee;
    private InvoiceDto  invoice;

    @BeforeAll
    void setup() {
        // шаг 1: создаём клиента
        customer = customerSteps.insertCustomer(DataUtil.getCustomerDto());
        // шаг 2: создаём сотрудника
        employee = employeeSteps.insertEmployee(DataUtil.getEmployeeDto());
    }

    @Test
    @DisplayName("testA — customer + employee → INSERT invoice → SELECT → данные совпадают")
    @Description("Бизнес-цепочка: переиспользуем customer и employee → вставляем заказ, проверяем все поля")
    void testA_insertAndSelect() {
        // шаг 3: создаём заказ, связывая customer и employee
        invoice = invoiceSteps.insertInvoice(
                DataUtil.getInvoiceDto(customer.getCustomerId(), employee.getEmployeeId())
        );
        InvoiceDto fromDb = invoiceSteps.selectInvoiceById(invoice.getInvoiceId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("заказ должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getInvoiceId()).isEqualTo(invoice.getInvoiceId());
        soft.assertThat(fromDb.getBillingAddress()).isEqualTo(invoice.getBillingAddress());
        soft.assertThat(fromDb.getBillingCity()).isEqualTo(invoice.getBillingCity());
        soft.assertThat(fromDb.getBillingCountry()).isEqualTo(invoice.getBillingCountry());
        soft.assertThat(fromDb.getTotal().compareTo(invoice.getTotal())).isZero();
        soft.assertThat(fromDb.getCustomerId()).isEqualTo(customer.getCustomerId());
        soft.assertThat(fromDb.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE billingAddress/total → SELECT → изменения применились")
    @Description("Меняем адрес и сумму заказа, проверяем обновление через SELECT")
    void testB_update() {
        InvoiceDto toUpdate = InvoiceDto.builder()
                .invoiceId(invoice.getInvoiceId())
                .invoiceDate(invoice.getInvoiceDate())
                .billingAddress(Faker.instance().address().fullAddress())
                .billingCity(Faker.instance().address().city())
                .billingState(invoice.getBillingState())
                .billingCountry(invoice.getBillingCountry())
                .billingPostalCode(invoice.getBillingPostalCode())
                .total(new BigDecimal("25.99"))
                .customerId(invoice.getCustomerId())
                .employeeId(invoice.getEmployeeId())
                .build();
        invoiceSteps.updateInvoice(toUpdate);
        InvoiceDto fromDb = invoiceSteps.selectInvoiceById(invoice.getInvoiceId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getBillingAddress()).isEqualTo(toUpdate.getBillingAddress());
        soft.assertThat(fromDb.getTotal().compareTo(new BigDecimal("25.99"))).isZero();
        soft.assertAll();
    }
}
