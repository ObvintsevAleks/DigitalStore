package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.CustomerDTO;
import com.personal.DigitalStore.dto.EmployeeDTO;
import com.personal.DigitalStore.dto.InvoiceDTO;
import com.personal.DigitalStore.dto.InvoiceSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.InvoiceMapper;
import com.personal.DigitalStore.models.Customer;
import com.personal.DigitalStore.models.Employee;
import com.personal.DigitalStore.models.Invoice;
import com.personal.DigitalStore.models.enumpack.Position;
import com.personal.DigitalStore.repositories.InvoiceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceMapper invoiceMapper;

    @InjectMocks
    private InvoiceService invoiceService;

    private static final UUID INVOICE_ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final UUID CUSTOMER_ID = UUID.fromString("1a1b1c1d-1111-2222-3333-444444444444");
    private static final UUID EMPLOYEE_ID = UUID.fromString("2b2b2b2b-2222-3333-4444-555555555555");
    private static final ZonedDateTime NOW = ZonedDateTime.parse("2019-08-06T16:30:00Z");
    private static final LocalDate DATE = LocalDate.of(1990, 1, 1);

    private Customer customer() {
        return new Customer(CUSTOMER_ID, "Ivan", "Petrov", DATE,
                "Lenin St. 1", "Moscow", "Moscow", "Russia", "101000", "+79001234567", null, "ivan@mail.com");
    }

    private Employee employee() {
        return new Employee(EMPLOYEE_ID, "Anna", "Sokolova", Position.MANAGER, DATE, DATE,
                "Marx St. 5", "Moscow", "Moscow", "Russia", "101000", "+79001234568", null, "anna@store.com");
    }

    private CustomerDTO customerDTO() {
        return new CustomerDTO(CUSTOMER_ID, "Ivan", "Petrov", DATE,
                "Lenin St. 1", "Moscow", "Moscow", "Russia", "101000", "+79001234567", null, "ivan@mail.com");
    }

    private EmployeeDTO employeeDTO() {
        return new EmployeeDTO(EMPLOYEE_ID, "Anna", "Sokolova", "MANAGER", DATE, DATE,
                "Marx St. 5", "Moscow", "Moscow", "Russia", "101000", "+79001234568", null, "anna@store.com");
    }

    private Invoice invoice() {
        Invoice inv = new Invoice();
        inv.setId(INVOICE_ID);
        inv.setInvoiceDate(NOW);
        inv.setBillingAddress("Pushkin St. 1");
        inv.setBillingCity("Moscow");
        inv.setTotal(BigDecimal.valueOf(10.10));
        inv.setCustomer(customer());
        inv.setEmployee(employee());
        return inv;
    }

    private InvoiceDTO invoiceDTO() {
        return new InvoiceDTO(INVOICE_ID, NOW, "Pushkin St. 1", "Moscow", null, null, null,
                BigDecimal.valueOf(10.10), customerDTO(), employeeDTO());
    }

    private InvoiceSaveDTO invoiceSaveDTO() {
        return new InvoiceSaveDTO(NOW, "Pushkin St. 1", "Moscow", null, null, null,
                BigDecimal.valueOf(10.10), customerDTO(), employeeDTO());
    }

    @Nested
    @DisplayName("createInvoice")
    class CreateInvoice {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO заказа")
        void success() {
            InvoiceSaveDTO saveDTO = invoiceSaveDTO();
            Invoice invoice = invoice();
            InvoiceDTO expected = invoiceDTO();

            when(invoiceMapper.toInvoice(saveDTO)).thenReturn(invoice);
            when(invoiceMapper.toInvoiceDTO(invoice)).thenReturn(expected);

            InvoiceDTO result = invoiceService.createInvoice(saveDTO);

            assertThat(result).isEqualTo(expected);
            verify(invoiceRepository).save(invoice);
        }
    }

    @Nested
    @DisplayName("getInvoiceById")
    class GetInvoiceById {

        @Test
        @DisplayName("возвращает DTO когда заказ найден")
        void found() {
            Invoice invoice = invoice();
            InvoiceDTO expected = invoiceDTO();

            when(invoiceRepository.findById(INVOICE_ID)).thenReturn(Optional.of(invoice));
            when(invoiceMapper.toInvoiceDTO(invoice)).thenReturn(expected);

            InvoiceDTO result = invoiceService.getInvoiceById(INVOICE_ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда заказ не найден")
        void notFound() {
            when(invoiceRepository.findById(INVOICE_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceService.getInvoiceById(INVOICE_ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(INVOICE_ID.toString());
        }
    }

    @Nested
    @DisplayName("updateInvoice")
    class UpdateInvoice {

        @Test
        @DisplayName("обновляет заказ и сохраняет изменения")
        void updatesWhenDataChanged() {
            Invoice invoice = invoice();
            InvoiceDTO originalDTO = invoiceDTO();
            InvoiceDTO updatedDTO = new InvoiceDTO(INVOICE_ID, NOW, "New Address", "SPb", null, null, null,
                    BigDecimal.valueOf(99.99), customerDTO(), employeeDTO());

            when(invoiceRepository.findById(INVOICE_ID)).thenReturn(Optional.of(invoice));
            // первый вызов — сравнение, второй — после обновления
            when(invoiceMapper.toInvoiceDTO(invoice)).thenReturn(originalDTO, updatedDTO);

            InvoiceDTO result = invoiceService.updateInvoice(updatedDTO);

            verify(invoiceMapper).updateInvoice(invoice, updatedDTO);
            verify(invoiceRepository).save(invoice);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            Invoice invoice = invoice();
            InvoiceDTO dto = invoiceDTO();

            when(invoiceRepository.findById(INVOICE_ID)).thenReturn(Optional.of(invoice));
            when(invoiceMapper.toInvoiceDTO(invoice)).thenReturn(dto);

            InvoiceDTO result = invoiceService.updateInvoice(dto);

            verify(invoiceRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если заказ не найден")
        void notFound() {
            when(invoiceRepository.findById(INVOICE_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceService.updateInvoice(invoiceDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteInvoiceById")
    class DeleteInvoiceById {

        @Test
        @DisplayName("успешно удаляет заказ")
        void success() {
            Invoice invoice = invoice();
            InvoiceDTO expected = invoiceDTO();

            when(invoiceRepository.findById(INVOICE_ID)).thenReturn(Optional.of(invoice));
            when(invoiceMapper.toInvoiceDTO(invoice)).thenReturn(expected);

            InvoiceDTO result = invoiceService.deleteInvoiceById(INVOICE_ID);

            verify(invoiceRepository).deleteById(INVOICE_ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если заказ не найден")
        void notFound() {
            when(invoiceRepository.findById(INVOICE_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceService.deleteInvoiceById(INVOICE_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getInvoiceByCustomerId")
    class GetInvoiceByCustomerId {

        @Test
        @DisplayName("возвращает список заказов клиента")
        void found() {
            List<Invoice> invoices = List.of(invoice());
            InvoiceDTO dto = invoiceDTO();

            when(invoiceRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(invoices));
            when(invoiceMapper.toInvoiceDTOs(invoices)).thenReturn(List.of(dto));

            List<InvoiceDTO> result = invoiceService.getInvoiceByCustomerId(CUSTOMER_ID);

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если заказы не найдены")
        void notFound() {
            when(invoiceRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceService.getInvoiceByCustomerId(CUSTOMER_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getInvoiceByEmployeeId")
    class GetInvoiceByEmployeeId {

        @Test
        @DisplayName("возвращает список заказов сотрудника")
        void found() {
            List<Invoice> invoices = List.of(invoice());
            InvoiceDTO dto = invoiceDTO();

            when(invoiceRepository.findByEmployeeId(EMPLOYEE_ID)).thenReturn(Optional.of(invoices));
            when(invoiceMapper.toInvoiceDTOs(invoices)).thenReturn(List.of(dto));

            List<InvoiceDTO> result = invoiceService.getInvoiceByEmployeeId(EMPLOYEE_ID);

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если заказы не найдены")
        void notFound() {
            when(invoiceRepository.findByEmployeeId(EMPLOYEE_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceService.getInvoiceByEmployeeId(EMPLOYEE_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }
}
