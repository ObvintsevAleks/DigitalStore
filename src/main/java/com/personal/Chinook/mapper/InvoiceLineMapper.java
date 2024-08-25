package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.InvoiceLineDTO;
import com.personal.Chinook.DTO.InvoiceLineSaveDTO;
import com.personal.Chinook.models.InvoiceLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvoiceLineMapper {

    InvoiceLineDTO toInvoiceLineDTO(InvoiceLine invoiceLine);

    List<InvoiceLineDTO> toInvoiceLineDTOs(List<InvoiceLine> invoiceLines);

    @Mapping(target = "id", ignore = true)  //bc we dont want to override id
    InvoiceLine toInvoiceLine(InvoiceLineSaveDTO invoiceLineSaveDTO);

    @Mapping(target = "id", ignore = true)  //bc we dont want to override id
    void updateInvoiceLine(@MappingTarget InvoiceLine invoiceLine, InvoiceLineDTO invoiceLineDTO);

}
