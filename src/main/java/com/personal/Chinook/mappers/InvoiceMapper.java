package com.personal.Chinook.mappers;

import com.personal.Chinook.dto.InvoiceDTO;
import com.personal.Chinook.dto.InvoiceSaveDTO;
import com.personal.Chinook.models.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvoiceMapper {

    InvoiceDTO toInvoiceDTO(Invoice invoice);

    List<InvoiceDTO> toInvoiceDTOs(List<Invoice> invoices);

    @Mapping(target = "id", ignore = true)
    Invoice toInvoice(InvoiceSaveDTO invoiceSaveDTO);

    @Mapping(target = "id", ignore = true)
    void updateInvoice(@MappingTarget Invoice invoice, InvoiceDTO invoiceDTO);

}
