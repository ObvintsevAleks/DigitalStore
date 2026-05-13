package digital.store.api.retrofit.model.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationPojo {

    private String name;
    private String email;
    private String username;
    private String password;

}
