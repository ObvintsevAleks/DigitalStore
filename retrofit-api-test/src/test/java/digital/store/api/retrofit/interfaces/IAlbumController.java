package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.AlbumDTO;
import digital.store.api.retrofit.model.AlbumSaveDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface IAlbumController {

    @GET("/api/albums/albums-by-artist-id/{artistId}")
    Call<List<AlbumDTO>> getAlbumByArtistId(@Path("artistId") UUID artistId);

    @GET("/api/albums/albums-by-artist-pseudonym/{pseudonym}")
    Call<List<AlbumDTO>> getAlbumByArtistPseudonym(@Path("pseudonym") String pseudonym);

    @GET("/api/albums/albums-by-title/{title}")
    Call<List<AlbumDTO>> getAlbumByTitle(@Path("title") String title);

    @GET("/api/albums/{id}")
    Call<AlbumDTO> getAlbum(@Path("id") UUID id);

    @POST("/api/albums")
    Call<AlbumDTO> createAlbum(@Body AlbumSaveDto albumSaveDto);

    @PUT("/api/albums")
    Call<AlbumDTO> updateAlbum(@Body AlbumDTO albumDTO);

    @DELETE("/api/albums/{id}")
    Call<Void> deleteAlbum(@Path("id") UUID id);

}
