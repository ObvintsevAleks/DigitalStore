package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.InvoiceLineDTO;
import com.personal.Chinook.DTO.InvoiceLineSaveDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.InvoiceLineMapper;
import com.personal.Chinook.models.InvoiceLine;
import com.personal.Chinook.repositories.InvoiceLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceLineService {

    private final InvoiceLineRepository invoiceLineRepository;
    private final InvoiceLineMapper invoiceLineMapper;

    @Transactional
    public InvoiceLineDTO createInvoiceLine(InvoiceLineSaveDTO invoiceLineSaveDTO) {
        InvoiceLine invoiceLine = invoiceLineMapper.toInvoiceLine(invoiceLineSaveDTO);
        invoiceLineRepository.save(invoiceLine);
        return invoiceLineMapper.toInvoiceLineDTO(invoiceLine);
    }

    @Transactional(readOnly = true)
    public InvoiceLineDTO getInvoiceLineById(UUID id) throws NotFoundInDBException {
        InvoiceLine invoiceLine = invoiceLineRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден invoice-line по id = "+ id));
        return invoiceLineMapper.toInvoiceLineDTO(invoiceLine);
    }

    @Transactional
    public InvoiceLineDTO updateInvoiceLine(InvoiceLineDTO invoiceLineDTO) throws NotFoundInDBException {
        InvoiceLine invoiceLineEntity = invoiceLineRepository.findById(invoiceLineDTO.getId()).orElseThrow(() -> new NotFoundInDBException("Не найден invoice-line по id = "+ invoiceLineDTO.getId()));
        if (invoiceLineMapper.toInvoiceLineDTO(invoiceLineEntity).equals(invoiceLineDTO)) {
            return invoiceLineMapper.toInvoiceLineDTO(invoiceLineEntity);
        }
        invoiceLineMapper.updateInvoiceLine(invoiceLineEntity, invoiceLineDTO);
        invoiceLineRepository.save(invoiceLineEntity);
        return invoiceLineMapper.toInvoiceLineDTO(invoiceLineEntity);
    }

    @Transactional
    public InvoiceLineDTO deleteInvoiceLineById(UUID id) throws NotFoundInDBException {
        InvoiceLine invoiceLine = invoiceLineRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден invoice-line по id = "+ id));
        UUID invoiceId = invoiceLine.getId();
        invoiceLineRepository.deleteById(invoiceId);
        return invoiceLineMapper.toInvoiceLineDTO(invoiceLine);
    }

}
