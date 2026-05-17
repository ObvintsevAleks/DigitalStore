package digital.store.jdbc.tests;

import com.github.javafaker.Faker;
import digital.store.jdbc.model.CustomerDto;
import digital.store.jdbc.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

@Owner("ObvintcevAE")
@DisplayName("Customer: JDBC тесты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CustomerTest extends BaseTest {

    private CustomerDto customer;

    @Test
    @DisplayName("testA — INSERT → SELECT → данные совпадают")
    @Description("Создаём клиента через JDBC и проверяем корректность всех полей в БД")
    void testA_insertAndSelect() {
        customer = customerSteps.insertCustomer(DataUtil.getCustomerDto());
        CustomerDto fromDb = customerSteps.selectCustomerById(customer.getCustomerId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("клиент должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getCustomerId()).isEqualTo(customer.getCustomerId());
        soft.assertThat(fromDb.getName()).isEqualTo(customer.getName());
        soft.assertThat(fromDb.getSurname()).isEqualTo(customer.getSurname());
        soft.assertThat(fromDb.getBirthDate()).isEqualTo(customer.getBirthDate());
        soft.assertThat(fromDb.getAddress()).isEqualTo(customer.getAddress());
        soft.assertThat(fromDb.getCity()).isEqualTo(customer.getCity());
        soft.assertThat(fromDb.getEmail()).isEqualTo(customer.getEmail());
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE phone/email/city → SELECT → изменения применились")
    @Description("Меняем контактные данные клиента, проверяем обновление через SELECT")
    void testB_update() {
        CustomerDto toUpdate = CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .birthDate(customer.getBirthDate())
                .address(customer.getAddress())
                .city(Faker.instance().address().city())
                .state(customer.getState())
                .country(customer.getCountry())
                .postalCode(customer.getPostalCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(customer.getFax())
                .email(Faker.instance().internet().emailAddress())
                .build();
        customerSteps.updateCustomer(toUpdate);
        CustomerDto fromDb = customerSteps.selectCustomerById(customer.getCustomerId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getPhone()).isEqualTo(toUpdate.getPhone());
        soft.assertThat(fromDb.getEmail()).isEqualTo(toUpdate.getEmail());
        soft.assertThat(fromDb.getCity()).isEqualTo(toUpdate.getCity());
        soft.assertAll();
    }
}
