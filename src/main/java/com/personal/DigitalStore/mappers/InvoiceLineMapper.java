package com.personal.DigitalStore.mappers;

import com.personal.DigitalStore.dto.InvoiceLineDTO;
import com.personal.DigitalStore.dto.InvoiceLineSaveDTO;
import com.personal.DigitalStore.models.InvoiceLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvoiceLineMapper {

    InvoiceLineDTO toInvoiceLineDTO(InvoiceLine invoiceLine);

    List<InvoiceLineDTO> toInvoiceLineDTOs(List<InvoiceLine> invoiceLines);

    @Mapping(target = "id", ignore = true)
    InvoiceLine toInvoiceLine(InvoiceLineSaveDTO invoiceLineSaveDTO);

    @Mapping(target = "id", ignore = true)
    void updateInvoiceLine(@MappingTarget InvoiceLine invoiceLine, InvoiceLineDTO invoiceLineDTO);

}
