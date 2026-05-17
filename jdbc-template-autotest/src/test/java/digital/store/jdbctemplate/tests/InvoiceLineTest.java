package digital.store.jdbctemplate.tests;

import digital.store.jdbctemplate.model.*;
import digital.store.jdbctemplate.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

@Owner("ObvintcevAE")
@DisplayName("InvoiceLine: JdbcTemplate тесты (полная FK-цепочка из 7 таблиц)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class InvoiceLineTest extends BaseTest {

    // ─── полная FK-цепочка: artist → album → track (+ genre + mediaType)
    //                         customer → employee → invoice
    //                         → invoice_line
    private ArtistDto      artist;
    private AlbumDto       album;
    private GenreDto       genre;
    private MediaTypeDto   mediaType;
    private TrackDto       track;
    private CustomerDto    customer;
    private EmployeeDto    employee;
    private InvoiceDto     invoice;
    private InvoiceLineDto invoiceLine;

    @BeforeAll
    void setup() {
        // ветка трека: artist → album + genre + mediaType
        artist    = artistSteps.insertArtist(DataUtil.getArtistDto());
        album     = albumSteps.insertAlbum(DataUtil.getAlbumDto(artist.getArtistId()));
        genre     = genreSteps.insertGenre(DataUtil.getGenreDto());
        mediaType = mediaTypeSteps.insertMediaType(DataUtil.getMediaTypeDto());
        track     = trackSteps.insertTrack(
                DataUtil.getTrackDto(album.getAlbumId(), genre.getGenreId(), mediaType.getMediaTypeId())
        );
        // ветка заказа: customer + employee → invoice
        customer = customerSteps.insertCustomer(DataUtil.getCustomerDto());
        employee = employeeSteps.insertEmployee(DataUtil.getEmployeeDto());
        invoice  = invoiceSteps.insertInvoice(
                DataUtil.getInvoiceDto(customer.getCustomerId(), employee.getEmployeeId())
        );
    }

    @Test
    @DisplayName("testA — полная цепочка 7 таблиц → INSERT invoiceLine → SELECT → данные совпадают")
    @Description("Все 7 зависимых таблиц подготовлены в @BeforeAll. Вставляем позицию заказа и проверяем все поля")
    void testA_insertAndSelect() {
        // итоговый шаг: создаём позицию заказа, связывая track и invoice
        invoiceLine = invoiceLineSteps.insertInvoiceLine(
                DataUtil.getInvoiceLineDto(invoice.getInvoiceId(), track.getTrackId())
        );
        InvoiceLineDto fromDb = invoiceLineSteps.selectInvoiceLineById(invoiceLine.getInvoiceLineId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("позиция заказа должна найтись в БД").isNotNull();
        soft.assertThat(fromDb.getInvoiceLineId()).isEqualTo(invoiceLine.getInvoiceLineId());
        soft.assertThat(fromDb.getQuantity()).isEqualTo(invoiceLine.getQuantity());
        soft.assertThat(fromDb.getUnitPrice().compareTo(invoiceLine.getUnitPrice())).isZero();
        soft.assertThat(fromDb.getInvoiceId()).isEqualTo(invoice.getInvoiceId());
        soft.assertThat(fromDb.getTrackId()).isEqualTo(track.getTrackId());
        soft.assertAll();
    }

    @Test
    @DisplayName("testB — UPDATE quantity/unitPrice → SELECT → изменения применились")
    @Description("Меняем количество и цену позиции заказа, проверяем обновление через SELECT")
    void testB_update() {
        InvoiceLineDto toUpdate = InvoiceLineDto.builder()
                .invoiceLineId(invoiceLine.getInvoiceLineId())
                .unitPrice(new BigDecimal("5.49"))
                .quantity(3)
                .invoiceId(invoiceLine.getInvoiceId())
                .trackId(invoiceLine.getTrackId())
                .build();
        invoiceLineSteps.updateInvoiceLine(toUpdate);
        InvoiceLineDto fromDb = invoiceLineSteps.selectInvoiceLineById(invoiceLine.getInvoiceLineId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getQuantity()).isEqualTo(3);
        soft.assertThat(fromDb.getUnitPrice().compareTo(new BigDecimal("5.49"))).isZero();
        soft.assertAll();
    }
}
