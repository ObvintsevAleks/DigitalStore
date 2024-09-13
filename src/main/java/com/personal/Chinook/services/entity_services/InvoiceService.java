package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.InvoiceDTO;
import com.personal.Chinook.DTO.InvoiceSaveDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.InvoiceMapper;
import com.personal.Chinook.models.Invoice;
import com.personal.Chinook.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Transactional
    public InvoiceDTO createInvoice(InvoiceSaveDTO invoiceSaveDTO) {
        Invoice invoice = invoiceMapper.toInvoice(invoiceSaveDTO);
        invoiceRepository.save(invoice);
        return invoiceMapper.toInvoiceDTO(invoice);
    }

    @Transactional(readOnly = true)
    public InvoiceDTO getInvoiceById(UUID id) throws NotFoundInDBException {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден заказ по id = "+ id));
        return invoiceMapper.toInvoiceDTO(invoice);
    }

    @Transactional
    public InvoiceDTO updateInvoice(InvoiceDTO invoiceDTO) throws NotFoundInDBException {
        Invoice invoiceEntity = invoiceRepository.findById(invoiceDTO.getId()).orElseThrow(() -> new NotFoundInDBException("Не найден заказ по id = "+ invoiceDTO.getId()));
        if (invoiceMapper.toInvoiceDTO(invoiceEntity).equals(invoiceDTO)) {
            return invoiceMapper.toInvoiceDTO(invoiceEntity);
        }
        invoiceMapper.updateInvoice(invoiceEntity, invoiceDTO);
        invoiceRepository.save(invoiceEntity);
        return invoiceMapper.toInvoiceDTO(invoiceEntity);
    }

    @Transactional
    public InvoiceDTO deleteInvoiceById(UUID id) throws NotFoundInDBException {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден заказ по id = "+ id));
        UUID invoiceId = invoice.getId();
        invoiceRepository.deleteById(invoiceId);
        return invoiceMapper.toInvoiceDTO(invoice);
    }

    @Transactional(readOnly = true)
    public List<InvoiceDTO> getInvoiceByCustomerId(UUID customerId) throws NotFoundInDBException {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId).orElseThrow(() -> new NotFoundInDBException("Не найден заказ по id клиента = "+ customerId));
        return invoiceMapper.toInvoiceDTOs(invoices);
    }

    @Transactional(readOnly = true)
    public List<InvoiceDTO> getInvoiceByEmployeeId(UUID employeeId) throws NotFoundInDBException {
        List<Invoice> invoices = invoiceRepository.findByEmployeeId(employeeId).orElseThrow(() -> new NotFoundInDBException("Не найден заказ по id сотрудника = "+ employeeId));
        return invoiceMapper.toInvoiceDTOs(invoices);
    }

}
