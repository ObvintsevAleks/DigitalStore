package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.ArtistDTO;
import digital.store.api.retrofit.model.ArtistSaveDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface IArtistController {

    @GET("/api/artists/artists-by-name/{name}")
    Call<List<ArtistDTO>> getArtistByName(@Path("name") String name);

    @GET("/api/artists/artists-by-pseudonym/{pseudonym}")
    Call<List<ArtistDTO>> getArtistByPseudonym(@Path("pseudonym") String pseudonym);

    @GET("/api/artists/{id}")
    Call<ArtistDTO> getArtist(@Path("id") UUID id);

    @POST("/api/artists")
    Call<ArtistDTO> createArtist(@Body ArtistSaveDTO artistSaveDTO);

    @PUT("/api/artists")
    Call<ArtistDTO> updateArtist(@Body ArtistDTO artistDTO);

    @DELETE("/api/artists/{id}")
    Call<Void> deleteArtist(@Path("id") UUID id);

}
