package digital.store.jdbc.tests;

import com.github.javafaker.Faker;
import digital.store.jdbc.model.EmployeeDto;
import digital.store.jdbc.model.enumpack.Position;
import digital.store.jdbc.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

@Owner("ObvintcevAE")
@DisplayName("Employee: JDBC тесты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class EmployeeTest extends BaseTest {

    private EmployeeDto employee;

    @Test
    @DisplayName("testA — INSERT → SELECT → данные совпадают")
    @Description("Создаём сотрудника через JDBC и проверяем корректность всех полей в БД")
    void testA_insertAndSelect() {
        employee = employeeSteps.insertEmployee(DataUtil.getEmployeeDto());
        EmployeeDto fromDb = employeeSteps.selectEmployeeById(employee.getEmployeeId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("сотрудник должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        soft.assertThat(fromDb.getName()).isEqualTo(employee.getName());
        soft.assertThat(fromDb.getSurname()).isEqualTo(employee.getSurname());
        soft.assertThat(fromDb.getPosition()).isEqualTo(employee.getPosition());
        soft.assertThat(fromDb.getBirthDate()).isEqualTo(employee.getBirthDate());
        soft.assertThat(fromDb.getHireDate()).isEqualTo(employee.getHireDate());
        soft.assertThat(fromDb.getEmail()).isEqualTo(employee.getEmail());
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE position/phone/email → SELECT → изменения применились")
    @Description("Меняем должность и контакты сотрудника, проверяем обновление через SELECT")
    void testB_update() {
        EmployeeDto toUpdate = EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .position(Position.MANAGER)
                .birthDate(employee.getBirthDate())
                .hireDate(employee.getHireDate())
                .address(employee.getAddress())
                .city(employee.getCity())
                .state(employee.getState())
                .country(employee.getCountry())
                .postalCode(employee.getPostalCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(employee.getFax())
                .email(Faker.instance().internet().emailAddress())
                .build();
        employeeSteps.updateEmployee(toUpdate);
        EmployeeDto fromDb = employeeSteps.selectEmployeeById(employee.getEmployeeId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getPosition()).isEqualTo(Position.MANAGER);
        soft.assertThat(fromDb.getPhone()).isEqualTo(toUpdate.getPhone());
        soft.assertThat(fromDb.getEmail()).isEqualTo(toUpdate.getEmail());
        soft.assertAll();
    }
}
