package digital.store.api.model;

import digital.store.api.model.enumpack.Position;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO extends SomeObjectWithId {

    private UUID id;
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
