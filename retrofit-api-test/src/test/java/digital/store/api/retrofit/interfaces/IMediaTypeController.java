package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.MediaTypeDTO;
import digital.store.api.retrofit.model.MediaTypeSaveDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface IMediaTypeController {

    @GET("/api/media-types/all")
    Call<List<MediaTypeDTO>> getMediaTypeAll();

    @GET("/api/media-types/{id}")
    Call<MediaTypeDTO> getMediaType(@Path("id") UUID id);

    @POST("/api/media-types")
    Call<MediaTypeDTO> createMediaType(@Body MediaTypeSaveDTO mediaTypeSaveDTO);

    @PUT("/api/media-types")
    Call<MediaTypeDTO> updateMediaType(@Body MediaTypeDTO mediaTypeDTO);

    @DELETE("/api/media-types/{id}")
    Call<Void> deleteMediaType(@Path("id") UUID id);

}
