package digital.store.jdbc.steps;

import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.EmployeeDto;
import digital.store.jdbc.model.enumpack.Position;
import io.qameta.allure.Step;

import java.util.UUID;

public class EmployeeSteps {

    private final DbConnection db;

    public EmployeeSteps(DbConnection db) {
        this.db = db;
    }

    @Step("[JDBC шаг] Вставляем сотрудника в БД: name={employee.name}, position={employee.position}")
    public EmployeeDto insertEmployee(EmployeeDto employee) {
        db.executeUpdate(
                "INSERT INTO employee (employee_id, name, surname, position, birth_date, hire_date, address, city, state, country, postal_code, phone, fax, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                employee.getEmployeeId(),
                employee.getName(),
                employee.getSurname(),
                employee.getPosition().name(),
                employee.getBirthDate(),
                employee.getHireDate(),
                employee.getAddress(),
                employee.getCity(),
                employee.getState(),
                employee.getCountry(),
                employee.getPostalCode(),
                employee.getPhone(),
                employee.getFax(),
                employee.getEmail()
        );
        return employee;
    }

    @Step("[JDBC шаг] Получаем сотрудника из БД по id={id}")
    public EmployeeDto selectEmployeeById(UUID id) {
        return db.executeQuery(
                "SELECT employee_id, name, surname, position, birth_date, hire_date, address, city, state, country, postal_code, phone, fax, email FROM employee WHERE employee_id = ?",
                rs -> {
                    if (rs.next()) {
                        return EmployeeDto.builder()
                                .employeeId(rs.getObject("employee_id", UUID.class))
                                .name(rs.getString("name"))
                                .surname(rs.getString("surname"))
                                .position(Position.valueOf(rs.getString("position")))
                                .birthDate(rs.getDate("birth_date").toLocalDate())
                                .hireDate(rs.getDate("hire_date").toLocalDate())
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

    @Step("[JDBC шаг] Обновляем сотрудника в БД: name={employee.name}, position={employee.position}")
    public EmployeeDto updateEmployee(EmployeeDto employee) {
        db.executeUpdate(
                "UPDATE employee SET name = ?, surname = ?, position = ?, birth_date = ?, hire_date = ?, address = ?, city = ?, state = ?, country = ?, postal_code = ?, phone = ?, fax = ?, email = ? WHERE employee_id = ?",
                employee.getName(),
                employee.getSurname(),
                employee.getPosition().name(),
                employee.getBirthDate(),
                employee.getHireDate(),
                employee.getAddress(),
                employee.getCity(),
                employee.getState(),
                employee.getCountry(),
                employee.getPostalCode(),
                employee.getPhone(),
                employee.getFax(),
                employee.getEmail(),
                employee.getEmployeeId()
        );
        return employee;
    }
}
