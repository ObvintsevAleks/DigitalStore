package digital.store.api.retrofit.interfaces;

import digital.store.api.retrofit.model.InvoiceDTO;
import digital.store.api.retrofit.model.InvoiceSaveDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;


public interface IInvoiceController {

    @GET("/api/invoices/invoices-by-employee/{id}")
    Call<List<InvoiceDTO>> getInvoiceByEmployeeId(@Path("id") UUID id);

    @GET("/api/invoices/invoices-by-customer/{id}")
    Call<List<InvoiceDTO>> getInvoiceByCustomerId(@Path("id") UUID id);

    @GET("/api/invoices/{id}")
    Call<InvoiceDTO> getInvoice(@Path("id") UUID id);

    @POST("/api/invoices")
    Call<InvoiceDTO> createInvoice(@Body InvoiceSaveDTO invoiceSaveDTO);

    @PUT("/api/invoices")
    Call<InvoiceDTO> updateInvoice(@Body InvoiceDTO invoiceDTO);

    @DELETE("/api/invoices/{id}")
    Call<Void> deleteInvoice(@Path("id") UUID id);

}
