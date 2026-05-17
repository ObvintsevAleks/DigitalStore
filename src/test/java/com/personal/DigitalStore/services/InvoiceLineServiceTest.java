package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.AlbumDTO;
import com.personal.DigitalStore.dto.ArtistDTO;
import com.personal.DigitalStore.dto.CustomerDTO;
import com.personal.DigitalStore.dto.EmployeeDTO;
import com.personal.DigitalStore.dto.GenreDTO;
import com.personal.DigitalStore.dto.InvoiceDTO;
import com.personal.DigitalStore.dto.InvoiceLineDTO;
import com.personal.DigitalStore.dto.InvoiceLineSaveDTO;
import com.personal.DigitalStore.dto.MediaTypeDTO;
import com.personal.DigitalStore.dto.TrackDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.InvoiceLineMapper;
import com.personal.DigitalStore.models.InvoiceLine;
import com.personal.DigitalStore.models.enumpack.AlbumType;
import com.personal.DigitalStore.models.enumpack.GenreDirection;
import com.personal.DigitalStore.repositories.InvoiceLineRepository;
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
class InvoiceLineServiceTest {

    @Mock
    private InvoiceLineRepository invoiceLineRepository;

    @Mock
    private InvoiceLineMapper invoiceLineMapper;

    @InjectMocks
    private InvoiceLineService invoiceLineService;

    private static final UUID LINE_ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final UUID INVOICE_ID = UUID.fromString("1a1b1c1d-1111-2222-3333-444444444444");
    private static final UUID TRACK_ID = UUID.fromString("2b2b2b2b-2222-3333-4444-555555555555");
    private static final ZonedDateTime NOW = ZonedDateTime.parse("2019-08-06T16:30:00Z");
    private static final LocalDate DATE = LocalDate.of(1990, 1, 1);

    private ArtistDTO artistDTO() {
        return new ArtistDTO(UUID.randomUUID(), "John", "Doe", "JD", DATE);
    }

    private AlbumDTO albumDTO() {
        return new AlbumDTO(UUID.randomUUID(), "Rock Album", AlbumType.ALBUM, NOW, artistDTO());
    }

    private MediaTypeDTO mediaTypeDTO() {
        return new MediaTypeDTO(UUID.randomUUID(), "MP3", DATE);
    }

    private GenreDTO genreDTO() {
        return new GenreDTO(UUID.randomUUID(), "Rock", DATE, GenreDirection.POPULAR);
    }

    private TrackDTO trackDTO() {
        return new TrackDTO(TRACK_ID, "Test Track", "John", NOW, 300000, 30000,
                BigDecimal.valueOf(2.41), albumDTO(), mediaTypeDTO(), genreDTO());
    }

    private CustomerDTO customerDTO() {
        return new CustomerDTO(UUID.randomUUID(), "Ivan", "Petrov", DATE,
                "Lenin St. 1", "Moscow", "Moscow", "Russia", "101000", "+79001234567", null, "ivan@mail.com");
    }

    private EmployeeDTO employeeDTO() {
        return new EmployeeDTO(UUID.randomUUID(), "Anna", "Sokolova", "MANAGER", DATE, DATE,
                "Marx St. 5", "Moscow", "Moscow", "Russia", "101000", "+79001234568", null, "anna@store.com");
    }

    private InvoiceDTO invoiceDTO() {
        return new InvoiceDTO(INVOICE_ID, NOW, "Pushkin St. 1", "Moscow", null, null, null,
                BigDecimal.valueOf(10.10), customerDTO(), employeeDTO());
    }

    private InvoiceLine invoiceLine() {
        InvoiceLine line = new InvoiceLine();
        line.setId(LINE_ID);
        line.setUnitPrice(BigDecimal.valueOf(2.41));
        line.setQuantity(2);
        return line;
    }

    private InvoiceLineDTO invoiceLineDTO() {
        return new InvoiceLineDTO(LINE_ID, BigDecimal.valueOf(2.41), 2, invoiceDTO(), trackDTO());
    }

    private InvoiceLineSaveDTO invoiceLineSaveDTO() {
        return new InvoiceLineSaveDTO(invoiceDTO(), trackDTO(), BigDecimal.valueOf(2.41), 2);
    }

    @Nested
    @DisplayName("createInvoiceLine")
    class CreateInvoiceLine {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO позиции заказа")
        void success() {
            InvoiceLineSaveDTO saveDTO = invoiceLineSaveDTO();
            InvoiceLine invoiceLine = invoiceLine();
            InvoiceLineDTO expected = invoiceLineDTO();

            when(invoiceLineMapper.toInvoiceLine(saveDTO)).thenReturn(invoiceLine);
            when(invoiceLineMapper.toInvoiceLineDTO(invoiceLine)).thenReturn(expected);

            InvoiceLineDTO result = invoiceLineService.createInvoiceLine(saveDTO);

            assertThat(result).isEqualTo(expected);
            verify(invoiceLineRepository).save(invoiceLine);
        }
    }

    @Nested
    @DisplayName("getInvoiceLineById")
    class GetInvoiceLineById {

        @Test
        @DisplayName("возвращает DTO когда позиция найдена")
        void found() {
            InvoiceLine invoiceLine = invoiceLine();
            InvoiceLineDTO expected = invoiceLineDTO();

            when(invoiceLineRepository.findById(LINE_ID)).thenReturn(Optional.of(invoiceLine));
            when(invoiceLineMapper.toInvoiceLineDTO(invoiceLine)).thenReturn(expected);

            InvoiceLineDTO result = invoiceLineService.getInvoiceLineById(LINE_ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда позиция не найдена")
        void notFound() {
            when(invoiceLineRepository.findById(LINE_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceLineService.getInvoiceLineById(LINE_ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(LINE_ID.toString());
        }
    }

    @Nested
    @DisplayName("updateInvoiceLine")
    class UpdateInvoiceLine {

        @Test
        @DisplayName("обновляет позицию и сохраняет изменения")
        void updatesWhenDataChanged() {
            InvoiceLine invoiceLine = invoiceLine();
            InvoiceLineDTO originalDTO = invoiceLineDTO();
            InvoiceLineDTO updatedDTO = new InvoiceLineDTO(LINE_ID, BigDecimal.valueOf(9.99), 5, invoiceDTO(), trackDTO());

            when(invoiceLineRepository.findById(LINE_ID)).thenReturn(Optional.of(invoiceLine));
            // первый вызов — сравнение, второй — после обновления
            when(invoiceLineMapper.toInvoiceLineDTO(invoiceLine)).thenReturn(originalDTO, updatedDTO);

            InvoiceLineDTO result = invoiceLineService.updateInvoiceLine(updatedDTO);

            verify(invoiceLineMapper).updateInvoiceLine(invoiceLine, updatedDTO);
            verify(invoiceLineRepository).save(invoiceLine);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            InvoiceLine invoiceLine = invoiceLine();
            InvoiceLineDTO dto = invoiceLineDTO();

            when(invoiceLineRepository.findById(LINE_ID)).thenReturn(Optional.of(invoiceLine));
            when(invoiceLineMapper.toInvoiceLineDTO(invoiceLine)).thenReturn(dto);

            InvoiceLineDTO result = invoiceLineService.updateInvoiceLine(dto);

            verify(invoiceLineRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если позиция не найдена")
        void notFound() {
            when(invoiceLineRepository.findById(LINE_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceLineService.updateInvoiceLine(invoiceLineDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteInvoiceLineById")
    class DeleteInvoiceLineById {

        @Test
        @DisplayName("успешно удаляет позицию заказа")
        void success() {
            InvoiceLine invoiceLine = invoiceLine();
            InvoiceLineDTO expected = invoiceLineDTO();

            when(invoiceLineRepository.findById(LINE_ID)).thenReturn(Optional.of(invoiceLine));
            when(invoiceLineMapper.toInvoiceLineDTO(invoiceLine)).thenReturn(expected);

            InvoiceLineDTO result = invoiceLineService.deleteInvoiceLineById(LINE_ID);

            verify(invoiceLineRepository).deleteById(LINE_ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если позиция не найдена")
        void notFound() {
            when(invoiceLineRepository.findById(LINE_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceLineService.deleteInvoiceLineById(LINE_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getInvoiceLineByTrackId")
    class GetInvoiceLineByTrackId {

        @Test
        @DisplayName("возвращает список позиций по id трека")
        void found() {
            List<InvoiceLine> lines = List.of(invoiceLine());
            InvoiceLineDTO dto = invoiceLineDTO();

            when(invoiceLineRepository.findByTrackId(TRACK_ID)).thenReturn(Optional.of(lines));
            when(invoiceLineMapper.toInvoiceLineDTOs(lines)).thenReturn(List.of(dto));

            List<InvoiceLineDTO> result = invoiceLineService.getInvoiceLineByTrackId(TRACK_ID);

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если позиции не найдены")
        void notFound() {
            when(invoiceLineRepository.findByTrackId(TRACK_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceLineService.getInvoiceLineByTrackId(TRACK_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("getInvoiceLineByInvoiceId")
    class GetInvoiceLineByInvoiceId {

        @Test
        @DisplayName("возвращает список позиций по id заказа")
        void found() {
            List<InvoiceLine> lines = List.of(invoiceLine());
            InvoiceLineDTO dto = invoiceLineDTO();

            when(invoiceLineRepository.findByInvoiceId(INVOICE_ID)).thenReturn(Optional.of(lines));
            when(invoiceLineMapper.toInvoiceLineDTOs(lines)).thenReturn(List.of(dto));

            List<InvoiceLineDTO> result = invoiceLineService.getInvoiceLineByInvoiceId(INVOICE_ID);

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если позиции не найдены")
        void notFound() {
            when(invoiceLineRepository.findByInvoiceId(INVOICE_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> invoiceLineService.getInvoiceLineByInvoiceId(INVOICE_ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }
}
