package digital.store.api.retrofit.tests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import digital.store.api.retrofit.interfaces.*;
import digital.store.api.retrofit.util.ICheckResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class BaseTest implements ICheckResponse {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8181")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();


    public ILoginController loginController = retrofit.create(ILoginController.class);
    public IRegisterController registerController = retrofit.create(IRegisterController.class);
    public IAlbumController albumController;
    public IArtistController artistController;
    public ICustomerController customerController;
    public IEmployeeController employeeController;
    public IGenreController genreController;
    public IInvoiceController invoiceController;
    public IInvoiceLineController invoiceLineController;
    public IMediaTypeController mediaTypeController;
    public ITrackController trackController;


    private Retrofit retrofitAuth(String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(
                interceptor -> {
                    Request originalRequest = interceptor.request();
                    Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                            "Bearer " + token);
                    Request newRequest = builder.build();
                    return interceptor.proceed(newRequest);
                }
        ).build();

        return new Retrofit.Builder()
                .baseUrl("http://localhost:8181/")
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }


    public IAlbumController createAlbumController(String token) {
        return retrofitAuth(token).create(IAlbumController.class);
    }

    public IArtistController createArtistController(String token) {
        return retrofitAuth(token).create(IArtistController.class);
    }

    public ICustomerController createCustomerController(String token) {
        return retrofitAuth(token).create(ICustomerController.class);
    }

    public IEmployeeController createEmployeeController(String token) {
        return retrofitAuth(token).create(IEmployeeController.class);
    }

    public IGenreController createGenreController(String token) {
        return retrofitAuth(token).create(IGenreController.class);
    }

    public IInvoiceController createInvoiceController(String token) {
        return retrofitAuth(token).create(IInvoiceController.class);
    }

    public IInvoiceLineController createInvoiceLineController(String token) {
        return retrofitAuth(token).create(IInvoiceLineController.class);
    }

    public IMediaTypeController createMediaTypeController(String token) {
        return retrofitAuth(token).create(IMediaTypeController.class);
    }

    public ITrackController createTrackController(String token) {
        return retrofitAuth(token).create(ITrackController.class);
    }


//    private final IArtistController artistController = retrofit.create(IArtistController.class);
//    private final ICustomerController customerController = retrofit.create(ICustomerController.class);
//    private final IEmployeeController employeeController = retrofit.create(IEmployeeController.class);
//    private final IGenreController genreController = retrofit.create(IGenreController.class);
//    private final IInvoiceController invoiceController = retrofit.create(IInvoiceController.class);
//    private final IInvoiceLineController invoiceLineController = retrofit.create(IInvoiceLineController.class);
//    private final IMediaTypeController mediaTypeController = retrofit.create(IMediaTypeController.class);
//    private final ITrackController trackController = retrofit.create(ITrackController.class);

}
