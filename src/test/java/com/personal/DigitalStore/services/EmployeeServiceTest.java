package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.EmployeeDTO;
import com.personal.DigitalStore.dto.EmployeeSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.EmployeeMapper;
import com.personal.DigitalStore.models.Employee;
import com.personal.DigitalStore.models.enumpack.Position;
import com.personal.DigitalStore.repositories.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Unit тест — EmployeeService")
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private static final UUID ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final LocalDate BIRTH_DATE = LocalDate.of(1985, 3, 20);
    private static final LocalDate HIRE_DATE = LocalDate.of(2010, 6, 1);

    private Employee employee() {
        return new Employee(ID, "Anna", "Sokolova", Position.MANAGER, BIRTH_DATE, HIRE_DATE,
                "Marx St. 5", "Novosibirsk", "Novosibirsk region", "Russia",
                "630000", "+73831234567", null, "anna@store.com");
    }

    private EmployeeDTO employeeDTO() {
        return new EmployeeDTO(ID, "Anna", "Sokolova", "MANAGER", BIRTH_DATE, HIRE_DATE,
                "Marx St. 5", "Novosibirsk", "Novosibirsk region", "Russia",
                "630000", "+73831234567", null, "anna@store.com");
    }

    private EmployeeSaveDTO employeeSaveDTO() {
        return new EmployeeSaveDTO("Anna", "Sokolova", "MANAGER", BIRTH_DATE, HIRE_DATE,
                "Marx St. 5", "Novosibirsk", "Novosibirsk region", "Russia",
                "630000", "+73831234567", null, "anna@store.com");
    }

    @Nested
    @DisplayName("createEmployee")
    class CreateEmployee {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO сотрудника")
        void success() {
            EmployeeSaveDTO saveDTO = employeeSaveDTO();
            Employee employee = employee();
            EmployeeDTO expected = employeeDTO();

            when(employeeMapper.toEmployee(saveDTO)).thenReturn(employee);
            when(employeeMapper.toEmployeeDTO(employee)).thenReturn(expected);

            EmployeeDTO result = employeeService.createEmployee(saveDTO);

            assertThat(result).isEqualTo(expected);
            verify(employeeRepository).save(employee);
        }
    }

    @Nested
    @DisplayName("getEmployeeById")
    class GetEmployeeById {

        @Test
        @DisplayName("возвращает DTO когда сотрудник найден")
        void found() {
            Employee employee = employee();
            EmployeeDTO expected = employeeDTO();

            when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));
            when(employeeMapper.toEmployeeDTO(employee)).thenReturn(expected);

            EmployeeDTO result = employeeService.getEmployeeById(ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда сотрудник не найден")
        void notFound() {
            when(employeeRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> employeeService.getEmployeeById(ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(ID.toString());
        }
    }

    @Nested
    @DisplayName("updateEmployee")
    class UpdateEmployee {

        @Test
        @DisplayName("обновляет сотрудника и сохраняет изменения")
        void updatesWhenDataChanged() {
            Employee employee = employee();
            EmployeeDTO originalDTO = employeeDTO();
            EmployeeDTO updatedDTO = new EmployeeDTO(ID, "Elena", "Sokolova", "DIRECTOR", BIRTH_DATE, HIRE_DATE,
                    "Marx St. 5", "Novosibirsk", "Novosibirsk region", "Russia",
                    "630000", "+73831234567", null, "elena@store.com");

            when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));
            // первый вызов — сравнение, второй — после обновления
            when(employeeMapper.toEmployeeDTO(employee)).thenReturn(originalDTO, updatedDTO);

            EmployeeDTO result = employeeService.updateEmployee(updatedDTO);

            verify(employeeMapper).updateEmployee(employee, updatedDTO);
            verify(employeeRepository).save(employee);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            Employee employee = employee();
            EmployeeDTO dto = employeeDTO();

            when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));
            when(employeeMapper.toEmployeeDTO(employee)).thenReturn(dto);

            EmployeeDTO result = employeeService.updateEmployee(dto);

            verify(employeeRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если сотрудник не найден")
        void notFound() {
            when(employeeRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> employeeService.updateEmployee(employeeDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteEmployeeById")
    class DeleteEmployeeById {

        @Test
        @DisplayName("успешно удаляет сотрудника")
        void success() {
            Employee employee = employee();
            EmployeeDTO expected = employeeDTO();

            when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));
            when(employeeMapper.toEmployeeDTO(employee)).thenReturn(expected);

            EmployeeDTO result = employeeService.deleteEmployeeById(ID);

            verify(employeeRepository).deleteById(ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если сотрудник не найден")
        void notFound() {
            when(employeeRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> employeeService.deleteEmployeeById(ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getEmployeeListByFirstname")
    class GetEmployeeListByFirstname {

        @Test
        @DisplayName("возвращает список сотрудников по имени")
        void found() {
            List<Employee> employees = List.of(employee());
            EmployeeDTO dto = employeeDTO();

            when(employeeRepository.searchByFirstname("Anna")).thenReturn(Optional.of(employees));
            when(employeeMapper.toEmployeeDTOs(employees)).thenReturn(List.of(dto));

            List<EmployeeDTO> result = employeeService.getEmployeeListByFirstname("Anna");

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если ничего не найдено")
        void notFound() {
            when(employeeRepository.searchByFirstname("Unknown")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> employeeService.getEmployeeListByFirstname("Unknown"))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining("Unknown");
        }
    }

    @Nested
    @DisplayName("getEmployeeListByLastname")
    class GetEmployeeListByLastname {

        @Test
        @DisplayName("возвращает список сотрудников по фамилии")
        void found() {
            List<Employee> employees = List.of(employee());
            EmployeeDTO dto = employeeDTO();

            when(employeeRepository.searchByLastname("Sokolova")).thenReturn(Optional.of(employees));
            when(employeeMapper.toEmployeeDTOs(employees)).thenReturn(List.of(dto));

            List<EmployeeDTO> result = employeeService.getEmployeeListByLastname("Sokolova");

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если ничего не найдено")
        void notFound() {
            when(employeeRepository.searchByLastname("Unknown")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> employeeService.getEmployeeListByLastname("Unknown"))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining("Unknown");
        }
    }
}
