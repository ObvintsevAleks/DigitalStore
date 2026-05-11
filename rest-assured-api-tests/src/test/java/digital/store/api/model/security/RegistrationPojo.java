package digital.store.api.model.security;

import lombok.*;

@Data
@Builder
public class RegistrationPojo {

    private String name;
    private String email;
    private String username;
    private String password;

}
