package digital.store.jdbctemplate.steps;

import digital.store.jdbctemplate.model.EmployeeDto;
import digital.store.jdbctemplate.model.enumpack.Position;
import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.UUID;

public class EmployeeSteps {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<EmployeeDto> EMPLOYEE_MAPPER = (rs, rowNum) -> EmployeeDto.builder()
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

    public EmployeeSteps(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Step("[JdbcTemplate шаг] Вставляем сотрудника: name={employee.name}, position={employee.position}")
    public EmployeeDto insertEmployee(EmployeeDto employee) {
        jdbcTemplate.update(
                "INSERT INTO employee (employee_id, name, surname, position, birth_date, hire_date, address, city, state, country, postal_code, phone, fax, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                employee.getEmployeeId(),
                employee.getName(),
                employee.getSurname(),
                employee.getPosition().name(),
                Date.valueOf(employee.getBirthDate()),
                Date.valueOf(employee.getHireDate()),
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

    @Step("[JdbcTemplate шаг] Получаем сотрудника по id={id}")
    public EmployeeDto selectEmployeeById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT employee_id, name, surname, position, birth_date, hire_date, address, city, state, country, postal_code, phone, fax, email FROM employee WHERE employee_id = ?",
                EMPLOYEE_MAPPER,
                id
        );
    }

    @Step("[JdbcTemplate шаг] Обновляем сотрудника: position={employee.position}, email={employee.email}")
    public EmployeeDto updateEmployee(EmployeeDto employee) {
        jdbcTemplate.update(
                "UPDATE employee SET name = ?, surname = ?, position = ?, birth_date = ?, hire_date = ?, address = ?, city = ?, state = ?, country = ?, postal_code = ?, phone = ?, fax = ?, email = ? WHERE employee_id = ?",
                employee.getName(),
                employee.getSurname(),
                employee.getPosition().name(),
                Date.valueOf(employee.getBirthDate()),
                Date.valueOf(employee.getHireDate()),
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
