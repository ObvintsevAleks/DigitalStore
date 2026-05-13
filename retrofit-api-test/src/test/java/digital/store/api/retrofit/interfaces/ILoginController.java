package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.security.LoginPojo;
import digital.store.api.retrofit.model.security.Token;
import io.qameta.allure.Step;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ILoginController {

    @POST("/api/login")
    Call<Token> loginUser(@Body LoginPojo loginPojo);


}
