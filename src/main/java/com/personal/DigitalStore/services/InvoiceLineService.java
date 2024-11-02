package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.InvoiceLineDTO;
import com.personal.DigitalStore.dto.InvoiceLineSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.InvoiceLineMapper;
import com.personal.DigitalStore.models.InvoiceLine;
import com.personal.DigitalStore.repositories.InvoiceLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional(readOnly = true)
    public List<InvoiceLineDTO> getInvoiceLineByTrackId(UUID trackId) throws NotFoundInDBException {
        List<InvoiceLine> invoiceLines = invoiceLineRepository.findByTrackId(trackId).orElseThrow(() -> new NotFoundInDBException("Не найден список invoice-line по trackId = "+ trackId));
        return invoiceLineMapper.toInvoiceLineDTOs(invoiceLines);
    }

    @Transactional(readOnly = true)
    public List<InvoiceLineDTO> getInvoiceLineByInvoiceId(UUID invoiceId) throws NotFoundInDBException {
        List<InvoiceLine> invoiceLines = invoiceLineRepository.findByInvoiceId(invoiceId).orElseThrow(() -> new NotFoundInDBException("Не найден список invoice-line по invoiceId = "+ invoiceId));
        return invoiceLineMapper.toInvoiceLineDTOs(invoiceLines);
    }

}
