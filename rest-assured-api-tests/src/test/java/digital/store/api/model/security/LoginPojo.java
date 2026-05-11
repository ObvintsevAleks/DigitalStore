package digital.store.api.model.security;

import lombok.*;

@Data
@Builder
public class LoginPojo {

    private String username;
    private String password;

}