package digital.store.jdbc.tests;

import com.github.javafaker.Faker;
import digital.store.jdbc.config.DbConnection;
import digital.store.jdbc.model.*;
import digital.store.jdbc.model.enumpack.AlbumType;
import digital.store.jdbc.model.enumpack.GenreDirection;
import digital.store.jdbc.model.enumpack.Position;
import digital.store.jdbc.steps.*;
import digital.store.jdbc.utils.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Owner("ObvintcevAE")
@DisplayName("JDBC-тесты DigitalStore")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class DigitalStoreJdbcTests {

    // ─── слой шагов (бизнес-цепочка через шаги, а не прямые SQL-вызовы) ─────────
    private final DbConnection     db               = new DbConnection();
    private final ArtistSteps      artistSteps      = new ArtistSteps(db);
    private final AlbumSteps       albumSteps       = new AlbumSteps(db);
    private final GenreSteps       genreSteps       = new GenreSteps(db);
    private final MediaTypeSteps   mediaTypeSteps   = new MediaTypeSteps(db);
    private final TrackSteps       trackSteps       = new TrackSteps(db);
    private final CustomerSteps    customerSteps    = new CustomerSteps(db);
    private final EmployeeSteps    employeeSteps    = new EmployeeSteps(db);
    private final InvoiceSteps     invoiceSteps     = new InvoiceSteps(db);
    private final InvoiceLineSteps invoiceLineSteps = new InvoiceLineSteps(db);

    // ─── цепочка созданных сущностей, переиспользуется в зависимых тестах ────────
    private ArtistDto      artist;
    private AlbumDto       album;
    private GenreDto       genre;
    private MediaTypeDto   mediaType;
    private TrackDto       track;
    private CustomerDto    customer;
    private EmployeeDto    employee;
    private InvoiceDto     invoice;
    private InvoiceLineDto invoiceLine;

    // ════════════════════════════════════════════════════════════════════════════
    //  A — Genre  (нет FK-зависимостей, старт простейшего сценария)
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testAA — Genre: INSERT → SELECT → данные совпадают")
    @Description("Создаём жанр и проверяем, что все поля корректно сохранились в БД")
    void testAA_genre_insertAndSelect() {
        genre = genreSteps.insertGenre(DataUtil.getGenreDto());
        GenreDto fromDb = genreSteps.selectGenreById(genre.getGenreId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("жанр должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getGenreId()).isEqualTo(genre.getGenreId());
        soft.assertThat(fromDb.getName()).isEqualTo(genre.getName());
        soft.assertThat(fromDb.getCreatedAt()).isEqualTo(genre.getCreatedAt());
        soft.assertThat(fromDb.getGenreDirection()).isEqualTo(genre.getGenreDirection());
        soft.assertAll();
    }

    @Test
    @DisplayName("testAB — Genre: UPDATE name/direction → SELECT → изменения применились")
    @Description("Меняем название и направление жанра и проверяем обновление")
    void testAB_genre_update() {
        GenreDto toUpdate = GenreDto.builder()
                .genreId(genre.getGenreId())
                .name("Updated_" + Faker.instance().music().genre())
                .createdAt(genre.getCreatedAt())
                .genreDirection(GenreDirection.POPULAR)
                .build();
        genreSteps.updateGenre(toUpdate);
        GenreDto fromDb = genreSteps.selectGenreById(genre.getGenreId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getName()).isEqualTo(toUpdate.getName());
        soft.assertThat(fromDb.getGenreDirection()).isEqualTo(GenreDirection.POPULAR);
        soft.assertAll();

        genre = fromDb;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  B — MediaType
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testBA — MediaType: INSERT → SELECT → данные совпадают")
    @Description("Создаём медиа-тип и проверяем сохранение всех полей")
    void testBA_mediaType_insertAndSelect() {
        mediaType = mediaTypeSteps.insertMediaType(DataUtil.getMediaTypeDto());
        MediaTypeDto fromDb = mediaTypeSteps.selectMediaTypeById(mediaType.getMediaTypeId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("медиа-тип должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getMediaTypeId()).isEqualTo(mediaType.getMediaTypeId());
        soft.assertThat(fromDb.getName()).isEqualTo(mediaType.getName());
        soft.assertThat(fromDb.getCreatedAt()).isEqualTo(mediaType.getCreatedAt());
        soft.assertAll();
    }

    @Test
    @DisplayName("testBB — MediaType: UPDATE name → SELECT → изменения применились")
    @Description("Меняем название медиа-типа и проверяем обновление")
    void testBB_mediaType_update() {
        MediaTypeDto toUpdate = MediaTypeDto.builder()
                .mediaTypeId(mediaType.getMediaTypeId())
                .name("Updated_" + Faker.instance().company().buzzword())
                .createdAt(mediaType.getCreatedAt())
                .build();
        mediaTypeSteps.updateMediaType(toUpdate);
        MediaTypeDto fromDb = mediaTypeSteps.selectMediaTypeById(mediaType.getMediaTypeId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getName()).isEqualTo(toUpdate.getName());
        soft.assertAll();

        mediaType = fromDb;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  C — Artist
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testCA — Artist: INSERT → SELECT → данные совпадают")
    @Description("Создаём артиста и проверяем корректность всех полей")
    void testCA_artist_insertAndSelect() {
        artist = artistSteps.insertArtist(DataUtil.getArtistDto());
        ArtistDto fromDb = artistSteps.selectArtistById(artist.getArtistId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("артист должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getArtistId()).isEqualTo(artist.getArtistId());
        soft.assertThat(fromDb.getName()).isEqualTo(artist.getName());
        soft.assertThat(fromDb.getSurname()).isEqualTo(artist.getSurname());
        soft.assertThat(fromDb.getPseudonym()).isEqualTo(artist.getPseudonym());
        soft.assertThat(fromDb.getBirthDate()).isEqualTo(artist.getBirthDate());
        soft.assertAll();
    }

    @Test
    @DisplayName("testCB — Artist: UPDATE name/surname/pseudonym → SELECT → изменения применились")
    @Description("Меняем имя, фамилию и псевдоним артиста и проверяем обновление")
    void testCB_artist_update() {
        ArtistDto toUpdate = ArtistDto.builder()
                .artistId(artist.getArtistId())
                .name(Faker.instance().artist().name())
                .surname(Faker.instance().name().lastName())
                .pseudonym("Updated_" + artist.getPseudonym())
                .birthDate(artist.getBirthDate())
                .build();
        artistSteps.updateArtist(toUpdate);
        ArtistDto fromDb = artistSteps.selectArtistById(artist.getArtistId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getName()).isEqualTo(toUpdate.getName());
        soft.assertThat(fromDb.getSurname()).isEqualTo(toUpdate.getSurname());
        soft.assertThat(fromDb.getPseudonym()).isEqualTo(toUpdate.getPseudonym());
        soft.assertAll();

        artist = fromDb;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  D — Album  (шаг 1: переиспользуем artist → шаг 2: создаём album)
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testDA — Album: шаг 1 берём artist → INSERT album → SELECT → данные совпадают")
    @Description("Бизнес-цепочка: artist (создан ранее) → создаём альбом, связанный с ним")
    void testDA_album_insertAndSelect() {
        // шаг 1: переиспользуем ранее созданного артиста
        // шаг 2: вставляем альбом, передавая artistId
        album = albumSteps.insertAlbum(DataUtil.getAlbumDto(artist.getArtistId()));
        AlbumDto fromDb = albumSteps.selectAlbumById(album.getAlbumId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("альбом должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getAlbumId()).isEqualTo(album.getAlbumId());
        soft.assertThat(fromDb.getTitle()).isEqualTo(album.getTitle());
        soft.assertThat(fromDb.getAlbumType()).isEqualTo(album.getAlbumType());
        soft.assertThat(fromDb.getArtistId()).isEqualTo(artist.getArtistId());
        soft.assertThat(fromDb.getCreatedAt().toInstant().truncatedTo(ChronoUnit.SECONDS))
                .isEqualTo(album.getCreatedAt().toInstant().truncatedTo(ChronoUnit.SECONDS));
        soft.assertAll();
    }

    @Test
    @DisplayName("testDB — Album: UPDATE title/albumType → SELECT → изменения применились")
    @Description("Меняем название и тип альбома и проверяем обновление")
    void testDB_album_update() {
        AlbumDto toUpdate = AlbumDto.builder()
                .albumId(album.getAlbumId())
                .title("Updated_" + Faker.instance().elderScrolls().creature())
                .albumType(AlbumType.SINGLE)
                .createdAt(album.getCreatedAt())
                .artistId(album.getArtistId())
                .build();
        albumSteps.updateAlbum(toUpdate);
        AlbumDto fromDb = albumSteps.selectAlbumById(album.getAlbumId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getTitle()).isEqualTo(toUpdate.getTitle());
        soft.assertThat(fromDb.getAlbumType()).isEqualTo(AlbumType.SINGLE);
        soft.assertAll();

        album = fromDb;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  E — Customer
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testEA — Customer: INSERT → SELECT → данные совпадают")
    @Description("Создаём клиента и проверяем корректность всех полей")
    void testEA_customer_insertAndSelect() {
        customer = customerSteps.insertCustomer(DataUtil.getCustomerDto());
        CustomerDto fromDb = customerSteps.selectCustomerById(customer.getCustomerId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("клиент должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getCustomerId()).isEqualTo(customer.getCustomerId());
        soft.assertThat(fromDb.getName()).isEqualTo(customer.getName());
        soft.assertThat(fromDb.getSurname()).isEqualTo(customer.getSurname());
        soft.assertThat(fromDb.getBirthDate()).isEqualTo(customer.getBirthDate());
        soft.assertThat(fromDb.getAddress()).isEqualTo(customer.getAddress());
        soft.assertThat(fromDb.getCity()).isEqualTo(customer.getCity());
        soft.assertThat(fromDb.getEmail()).isEqualTo(customer.getEmail());
        soft.assertAll();
    }

    @Test
    @DisplayName("testEB — Customer: UPDATE phone/email/city → SELECT → изменения применились")
    @Description("Меняем контактные данные клиента и проверяем обновление")
    void testEB_customer_update() {
        CustomerDto toUpdate = CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .birthDate(customer.getBirthDate())
                .address(customer.getAddress())
                .city(Faker.instance().address().city())
                .state(customer.getState())
                .country(customer.getCountry())
                .postalCode(customer.getPostalCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(customer.getFax())
                .email(Faker.instance().internet().emailAddress())
                .build();
        customerSteps.updateCustomer(toUpdate);
        CustomerDto fromDb = customerSteps.selectCustomerById(customer.getCustomerId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getPhone()).isEqualTo(toUpdate.getPhone());
        soft.assertThat(fromDb.getEmail()).isEqualTo(toUpdate.getEmail());
        soft.assertThat(fromDb.getCity()).isEqualTo(toUpdate.getCity());
        soft.assertAll();

        customer = fromDb;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  F — Employee
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testFA — Employee: INSERT → SELECT → данные совпадают")
    @Description("Создаём сотрудника и проверяем корректность всех полей")
    void testFA_employee_insertAndSelect() {
        employee = employeeSteps.insertEmployee(DataUtil.getEmployeeDto());
        EmployeeDto fromDb = employeeSteps.selectEmployeeById(employee.getEmployeeId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("сотрудник должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        soft.assertThat(fromDb.getName()).isEqualTo(employee.getName());
        soft.assertThat(fromDb.getSurname()).isEqualTo(employee.getSurname());
        soft.assertThat(fromDb.getPosition()).isEqualTo(employee.getPosition());
        soft.assertThat(fromDb.getBirthDate()).isEqualTo(employee.getBirthDate());
        soft.assertThat(fromDb.getHireDate()).isEqualTo(employee.getHireDate());
        soft.assertThat(fromDb.getEmail()).isEqualTo(employee.getEmail());
        soft.assertAll();
    }

    @Test
    @DisplayName("testFB — Employee: UPDATE position/phone/email → SELECT → изменения применились")
    @Description("Меняем должность и контакты сотрудника и проверяем обновление")
    void testFB_employee_update() {
        EmployeeDto toUpdate = EmployeeDto.builder()
                .employeeId(employee.getEmployeeId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .position(Position.MANAGER)
                .birthDate(employee.getBirthDate())
                .hireDate(employee.getHireDate())
                .address(employee.getAddress())
                .city(employee.getCity())
                .state(employee.getState())
                .country(employee.getCountry())
                .postalCode(employee.getPostalCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(employee.getFax())
                .email(Faker.instance().internet().emailAddress())
                .build();
        employeeSteps.updateEmployee(toUpdate);
        EmployeeDto fromDb = employeeSteps.selectEmployeeById(employee.getEmployeeId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getPosition()).isEqualTo(Position.MANAGER);
        soft.assertThat(fromDb.getPhone()).isEqualTo(toUpdate.getPhone());
        soft.assertThat(fromDb.getEmail()).isEqualTo(toUpdate.getEmail());
        soft.assertAll();

        employee = fromDb;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  G — Track  (цепочка: artist → album; genre; mediaType → track)
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testGA — Track: artist→album + genre + mediaType → INSERT track → SELECT → данные совпадают")
    @Description("Бизнес-цепочка из 4 зависимостей: album(artist), genre, mediaType → создаём трек")
    void testGA_track_insertAndSelect() {
        // шаг 1: переиспользуем album (внутри которого уже есть artist)
        // шаг 2: переиспользуем genre
        // шаг 3: переиспользуем mediaType
        // шаг 4: создаём трек, связывая все три зависимости
        track = trackSteps.insertTrack(
                DataUtil.getTrackDto(album.getAlbumId(), genre.getGenreId(), mediaType.getMediaTypeId())
        );
        TrackDto fromDb = trackSteps.selectTrackById(track.getTrackId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("трек должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getTrackId()).isEqualTo(track.getTrackId());
        soft.assertThat(fromDb.getName()).isEqualTo(track.getName());
        soft.assertThat(fromDb.getAuthor()).isEqualTo(track.getAuthor());
        soft.assertThat(fromDb.getMilliseconds()).isEqualTo(track.getMilliseconds());
        soft.assertThat(fromDb.getBytes()).isEqualTo(track.getBytes());
        soft.assertThat(fromDb.getUnitPrice().compareTo(track.getUnitPrice())).isZero();
        soft.assertThat(fromDb.getAlbumId()).isEqualTo(album.getAlbumId());
        soft.assertThat(fromDb.getGenreId()).isEqualTo(genre.getGenreId());
        soft.assertThat(fromDb.getMediaTypeId()).isEqualTo(mediaType.getMediaTypeId());
        soft.assertThat(fromDb.getCreatedAt().toInstant().truncatedTo(ChronoUnit.SECONDS))
                .isEqualTo(track.getCreatedAt().toInstant().truncatedTo(ChronoUnit.SECONDS));
        soft.assertAll();
    }

    @Test
    @DisplayName("testGB — Track: UPDATE name/author/unitPrice → SELECT → изменения применились")
    @Description("Меняем название, автора и цену трека и проверяем обновление")
    void testGB_track_update() {
        TrackDto toUpdate = TrackDto.builder()
                .trackId(track.getTrackId())
                .name("Updated_" + Faker.instance().lordOfTheRings().location())
                .author(Faker.instance().artist().name())
                .createdAt(track.getCreatedAt())
                .milliseconds(Faker.instance().number().numberBetween(120000, 300000))
                .bytes(Faker.instance().number().numberBetween(2000000, 8000000))
                .unitPrice(BigDecimal.ONE)
                .albumId(track.getAlbumId())
                .genreId(track.getGenreId())
                .mediaTypeId(track.getMediaTypeId())
                .build();
        trackSteps.updateTrack(toUpdate);
        TrackDto fromDb = trackSteps.selectTrackById(track.getTrackId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getName()).isEqualTo(toUpdate.getName());
        soft.assertThat(fromDb.getAuthor()).isEqualTo(toUpdate.getAuthor());
        soft.assertThat(fromDb.getUnitPrice().compareTo(BigDecimal.ONE)).isZero();
        soft.assertAll();

        track = fromDb;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  H — Invoice  (шаг 1: customer → шаг 2: employee → шаг 3: invoice)
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testHA — Invoice: customer + employee → INSERT invoice → SELECT → данные совпадают")
    @Description("Бизнес-цепочка: переиспользуем customer и employee → создаём заказ")
    void testHA_invoice_insertAndSelect() {
        // шаг 1: переиспользуем customer
        // шаг 2: переиспользуем employee
        // шаг 3: создаём invoice, связывая обоих
        invoice = invoiceSteps.insertInvoice(
                DataUtil.getInvoiceDto(customer.getCustomerId(), employee.getEmployeeId())
        );
        InvoiceDto fromDb = invoiceSteps.selectInvoiceById(invoice.getInvoiceId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb).as("заказ должен найтись в БД").isNotNull();
        soft.assertThat(fromDb.getInvoiceId()).isEqualTo(invoice.getInvoiceId());
        soft.assertThat(fromDb.getBillingAddress()).isEqualTo(invoice.getBillingAddress());
        soft.assertThat(fromDb.getBillingCity()).isEqualTo(invoice.getBillingCity());
        soft.assertThat(fromDb.getBillingCountry()).isEqualTo(invoice.getBillingCountry());
        soft.assertThat(fromDb.getTotal().compareTo(invoice.getTotal())).isZero();
        soft.assertThat(fromDb.getCustomerId()).isEqualTo(customer.getCustomerId());
        soft.assertThat(fromDb.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        soft.assertAll();
    }

    @Test
    @DisplayName("testHB — Invoice: UPDATE billingAddress/total → SELECT → изменения применились")
    @Description("Меняем адрес и сумму заказа и проверяем обновление")
    void testHB_invoice_update() {
        InvoiceDto toUpdate = InvoiceDto.builder()
                .invoiceId(invoice.getInvoiceId())
                .invoiceDate(invoice.getInvoiceDate())
                .billingAddress(Faker.instance().address().fullAddress())
                .billingCity(Faker.instance().address().city())
                .billingState(invoice.getBillingState())
                .billingCountry(invoice.getBillingCountry())
                .billingPostalCode(invoice.getBillingPostalCode())
                .total(new BigDecimal("25.99"))
                .customerId(invoice.getCustomerId())
                .employeeId(invoice.getEmployeeId())
                .build();
        invoiceSteps.updateInvoice(toUpdate);
        InvoiceDto fromDb = invoiceSteps.selectInvoiceById(invoice.getInvoiceId());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fromDb.getBillingAddress()).isEqualTo(toUpdate.getBillingAddress());
        soft.assertThat(fromDb.getTotal().compareTo(new BigDecimal("25.99"))).isZero();
        soft.assertAll();

        invoice = fromDb;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  I — InvoiceLine  (самый сложный: полная цепочка из 7 таблиц)
    //      artist → album → track (+ genre + mediaType)
    //      customer → employee → invoice
    //      → invoice_line
    // ════════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("testIA — InvoiceLine: artist→album→track + customer→employee→invoice → INSERT → SELECT → данные совпадают")
    @Description("Полная бизнес-цепочка: 7 зависимых таблиц. Проверяем корректность позиции заказа")
    void testIA_invoiceLine_insertAndSelect() {
        // шаг 1: переиспользуем track (внутри которого album(artist) + genre + mediaType)
        // шаг 2: переиспользуем invoice (внутри которого customer + employee)
        // шаг 3: создаём позицию заказа, связывая трек и заказ
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
    @DisplayName("testIB — InvoiceLine: UPDATE quantity/unitPrice → SELECT → изменения применились")
    @Description("Меняем количество и цену позиции заказа и проверяем обновление")
    void testIB_invoiceLine_update() {
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

        invoiceLine = fromDb;
    }
}
