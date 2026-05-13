package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.GenreDTO;
import digital.store.api.retrofit.model.GenreSaveDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface IGenreController {

    @GET("/api/genres/all")
    Call<List<GenreDTO>> getGenreAll();

    @GET("/api/genres/{id}")
    Call<GenreDTO> getGenre(@Path("id") UUID id);

    @POST("/api/genres")
    Call<GenreDTO> createGenre(@Body GenreSaveDTO genreSaveDTO);

    @PUT("/api/genres")
    Call<GenreDTO> updateGenre(@Body GenreDTO genreDTO);

    @DELETE("/api/genres/{id}")
    Call<Void> deleteGenre(@Path("id") UUID id);

}
