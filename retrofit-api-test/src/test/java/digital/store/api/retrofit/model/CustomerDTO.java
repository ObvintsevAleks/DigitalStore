package digital.store.api.retrofit.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO extends SomeObjectWithId {

    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String fax;
    private String email;

}
