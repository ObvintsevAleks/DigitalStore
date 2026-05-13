package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.security.RegistrationPojo;
import digital.store.api.retrofit.model.security.SuccessRegisterMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IRegisterController {

    @POST("/api/register")
    Call<SuccessRegisterMessage> createUser(@Body RegistrationPojo registrationPojo);

}
