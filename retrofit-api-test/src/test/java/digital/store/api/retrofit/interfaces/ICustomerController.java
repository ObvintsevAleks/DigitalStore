package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.CustomerDTO;
import digital.store.api.retrofit.model.CustomerSaveDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface ICustomerController {

    @GET("/api/customers/firstname/{name}")
    Call<List<CustomerDTO>> getCustomerByFirstname(@Path("name") String name);

    @GET("/api/customers/lastname/{name}")
    Call<List<CustomerDTO>> getCustomerByLastname(@Path("name") String name);

    @GET("/api/customers/{id}")
    Call<CustomerDTO> getCustomer(@Path("id") UUID id);

    @POST("/api/customers")
    Call<CustomerDTO> createCustomer(@Body CustomerSaveDTO customerSaveDTO);

    @PUT("/api/customers")
    Call<CustomerDTO> updateCustomer(@Body CustomerDTO customerDTO);

    @DELETE("/api/customers/{id}")
    Call<Void> deleteCustomer(@Path("id") UUID id);

}
