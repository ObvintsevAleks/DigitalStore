package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.TrackDTO;
import digital.store.api.retrofit.model.TrackSaveDTO;
import io.qameta.allure.Step;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface ITrackController {

    @Step("")
    @GET("/api/tracks/tracks-by-artist-pseudonym/{pseudonym}")
    Call<List<TrackDTO>> getTrackByArtistPseudonym(@Path("pseudonym") String pseudonym);

    @Step("")
    @GET("/api/tracks/tracks-by-artist-id/{id}")
    Call<List<TrackDTO>> getTrackByArtistId(@Path("id") UUID id);

    @Step("")
    @GET("/api/tracks/all-tracks-by-media-type/{id}")
    Call<List<TrackDTO>> getTrackByMediaTypeId(@Path("id") UUID id);

    @Step("")
    @GET("/api/tracks/all-tracks-by-genre/{id}")
    Call<List<TrackDTO>> getTrackByGenreId(@Path("id") UUID id);

    @Step("")
    @GET("/api/tracks/all-tracks-by-album/{id}")
    Call<List<TrackDTO>> getTrackByAlumId(@Path("id") UUID id);

    @Step("")
    @GET("/api/tracks/{id}")
    Call<TrackDTO> getTrack(@Path("id") UUID id);

    @Step("")
    @POST("/api/tracks")
    Call<TrackDTO> createTrack(@Body TrackSaveDTO trackSaveDTO);

    @Step("")
    @PUT("/api/tracks")
    Call<TrackDTO> updateTrack(@Body TrackDTO trackDTO);

    @Step("")
    @DELETE("/api/tracks/{id}")
    Call<Void> deleteTrack(@Path("id") UUID id);

}
