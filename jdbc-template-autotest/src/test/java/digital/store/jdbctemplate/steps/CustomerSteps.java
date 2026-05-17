package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.CustomerDto;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.UUID;

public class CustomerSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<CustomerDto> CUSTOMER_MAPPER = (rs, rowNum) -> CustomerDto.builder()
            .customerId(rs.getObject("customer_id", UUID.class))
            .name(rs.getString("name"))
            .surname(rs.getString("surname"))
            .birthDate(rs.getDate("birth_date").toLocalDate())
            .address(rs.getString("address"))
            .city(rs.getString("city"))
            .state(rs.getString("state"))
            .country(rs.getString("country"))
            .postalCode(rs.getString("postal_code"))
            .phone(rs.getString("phone"))
            .fax(rs.getString("fax"))
            .email(rs.getString("email"))
            .build();

    public CustomerSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем клиента: name={customer.name}, surname={customer.surname}")
    public CustomerDto insertCustomer(CustomerDto customer) {
        jdbcTemplate.update(
                "INSERT INTO customer (customer_id, name, surname, birth_date, address, city, state, country, postal_code, phone, fax, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                customer.getCustomerId(),
                customer.getName(),
                customer.getSurname(),
                Date.valueOf(customer.getBirthDate()),
                customer.getAddress(),
                customer.getCity(),
                customer.getState(),
                customer.getCountry(),
                customer.getPostalCode(),
                customer.getPhone(),
                customer.getFax(),
                customer.getEmail()
        );
        return customer;
    }

    @Step("[JdbcTemplate шаг] Получаем клиента по id={id}")
    public CustomerDto selectCustomerById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT customer_id, name, surname, birth_date, address, city, state, country, postal_code, phone, fax, email FROM customer WHERE customer_id = ?",
                CUSTOMER_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем клиента: phone={customer.phone}, email={customer.email}")
    public CustomerDto updateCustomer(CustomerDto customer) {
        jdbcTemplate.update(
                "UPDATE customer SET name = ?, surname = ?, birth_date = ?, address = ?, city = ?, state = ?, country = ?, postal_code = ?, phone = ?, fax = ?, email = ? WHERE customer_id = ?",
                customer.getName(),
                customer.getSurname(),
                Date.valueOf(customer.getBirthDate()),
                customer.getAddress(),
                customer.getCity(),
                customer.getState(),
                customer.getCountry(),
                customer.getPostalCode(),
                customer.getPhone(),
                customer.getFax(),
                customer.getEmail(),
                customer.getCustomerId()
        );
        return customer;
    }
}
