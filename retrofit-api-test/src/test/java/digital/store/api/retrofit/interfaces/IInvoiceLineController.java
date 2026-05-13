package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.InvoiceLineDTO;
import digital.store.api.retrofit.model.InvoiceLineSaveDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface IInvoiceLineController {

    @GET("/api/invoice-lines/invoice-lines-by-track/{id}")
    Call<List<InvoiceLineDTO>> getInvoiceLineByTrack(@Path("id") UUID id);

    @GET("/api/invoice-lines/invoice-line-by-invoice/{id}")
    Call<List<InvoiceLineDTO>> getInvoiceLineByInvoice(@Path("id") UUID id);

    @GET("/api/invoice-lines/{id}")
    Call<InvoiceLineDTO> getInvoiceLine(@Path("id") UUID id);

    @POST("/api/invoice-lines")
    Call<InvoiceLineDTO> createInvoiceLine(@Body InvoiceLineSaveDTO invoiceLineSaveDTO);

    @PUT("/api/invoice-lines")
    Call<InvoiceLineDTO> updateInvoiceLine(@Body InvoiceLineDTO invoiceLineDTO);

    @DELETE("/api/invoice-lines/{id}")
    Call<Void> deleteInvoiceLine(@Path("id") UUID id);

}
