package digital.store.api.retrofit.model;

import digital.store.api.retrofit.model.enumpack.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSaveDTO {

    private String firstName;
    private String lastName;
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
