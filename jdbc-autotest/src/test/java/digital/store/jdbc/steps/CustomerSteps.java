package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.CustomerDto;
import io.qameta.allure.Step;

import java.util.UUID;

public class CustomerSteps {

    private final DbConnection db;

    public CustomerSteps(DbConnection db) {
        this.db = db;
    }

    @Step("Вставляем клиента в БД: name={customer.name}, email={customer.email}")
    public CustomerDto insertCustomer(CustomerDto customer) {
        db.executeUpdate(
                "INSERT INTO customer (customer_id, name, surname, birth_date, address, city, state, country, postal_code, phone, fax, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                customer.getCustomerId(),
                customer.getName(),
                customer.getSurname(),
                customer.getBirthDate(),
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

    @Step("Получаем клиента из БД по id={id}")
    public CustomerDto selectCustomerById(UUID id) {
        return db.executeQuery(
                "SELECT customer_id, name, surname, birth_date, address, city, state, country, postal_code, phone, fax, email FROM customer WHERE customer_id = ?",
                rs -> {
                    if (rs.next()) {
                        return CustomerDto.builder()
                                .customerId(rs.getObject("customer_id", UUID.class))
                                .name(rs.getString("name"))
                                .surname(rs.getString("surname"))
                                .birthDate(rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null)
                                .address(rs.getString("address"))
                                .city(rs.getString("city"))
                                .state(rs.getString("state"))
                                .country(rs.getString("country"))
                                .postalCode(rs.getString("postal_code"))
                                .phone(rs.getString("phone"))
                                .fax(rs.getString("fax"))
                                .email(rs.getString("email"))
                                .build();
                    }
                    return null;
                },
                id
        );
    }

    @Step("Обновляем клиента в БД: name={customer.name}, email={customer.email}")
    public CustomerDto updateCustomer(CustomerDto customer) {
        db.executeUpdate(
                "UPDATE customer SET name = ?, surname = ?, birth_date = ?, address = ?, city = ?, state = ?, country = ?, postal_code = ?, phone = ?, fax = ?, email = ? WHERE customer_id = ?",
                customer.getName(),
                customer.getSurname(),
                customer.getBirthDate(),
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
