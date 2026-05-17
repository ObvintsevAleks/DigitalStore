package digital.store.jdbctemplate.model;

import digital.store.jdbctemplate.model.enumpack.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private UUID employeeId;
    private String name;
    private String surname;
    private Position position;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String fax;
    private String email;

}
