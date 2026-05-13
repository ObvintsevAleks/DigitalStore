package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.EmployeeDTO;
import digital.store.api.retrofit.model.EmployeeSaveDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface IEmployeeController {

    @GET("/api/employees/firstname/{name}")
    Call<List<EmployeeDTO>> getEmployeeByFirstname(@Path("name") String name);

    @GET("/api/employees/lastname/{name}")
    Call<List<EmployeeDTO>> getEmployeeByLastname(@Path("name") String name);

    @GET("/api/employees/{id}")
    Call<EmployeeDTO> getEmployee(@Path("id") UUID id);

    @POST("/api/employees")
    Call<EmployeeDTO> createEmployee(@Body EmployeeSaveDTO employeeSaveDTO);

    @PUT("/api/employees")
    Call<EmployeeDTO> updateEmployee(@Body EmployeeDTO employeeDTO);

    @DELETE("/api/employees/{id}")
    Call<Void> deleteEmployee(@Path("id") UUID id);

}
