package digital.store.api.utils;

import com.github.javafaker.Faker;
import digital.store.api.model.*;
import digital.store.api.model.enumpack.AlbumType;
import digital.store.api.model.enumpack.GenreDirection;
import digital.store.api.model.enumpack.Position;
import digital.store.api.model.security.LoginPojo;
import digital.store.api.model.security.RegistrationPojo;
import io.qameta.allure.Step;
import org.instancio.Instancio;
import org.instancio.Select;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class DataUtil {

    @Step("Подготовка LoginPojo")
    public static LoginPojo getLoginPojo() {
        return LoginPojo.builder()
                .username(Params.userName)
                .password(Params.userPass)
                .build();
    }

    @Step("Подготовка RegistrationPojo")
    public static RegistrationPojo getRegistrationPojo() {
        return RegistrationPojo.builder()
                .email(Params.email)
                .name(Params.name)
                .password(Params.userPass)
                .username(Params.userName)
                .build();
    }

    @Step("Подготовка ArtistSaveDTO")
    public static ArtistSaveDTO getArtistSaveDto() {
        return ArtistSaveDTO.builder()
                .name(Faker.instance().artist().name())
                .surname(Faker.instance().name().lastName())
                .pseudonym(Faker.instance().dune().planet())
                .birthDate(Instancio.of(ArtistSaveDTO.class)
                        .generate(Select.field(ArtistSaveDTO::getBirthDate),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(35), LocalDate.now().minusYears(25)))
                        .create().getBirthDate())
                .build();
    }

    @Step("Подготовка AlbumSaveDto")
    public static AlbumSaveDto getAlbumSaveDto(ArtistDTO artist) {
        return AlbumSaveDto.builder()
                .title(Faker.instance().elderScrolls().creature())
                .artist(artist)
                .albumType(Instancio.of(AlbumSaveDto.class)
                        .generate(Select.field(AlbumSaveDto::getAlbumType), x -> x.enumOf(AlbumType.class))
                        .create().getAlbumType())
                .createdAt(Instancio.of(AlbumSaveDto.class)
                        .generate(Select.field(AlbumSaveDto::getCreatedAt),
                                x -> x.temporal().zonedDateTime()
                                        .range(ZonedDateTime.now().minusYears(5), ZonedDateTime.now().minusYears(1)))
                        .create().getCreatedAt())
                .build();
    }

    @Step("Подготовка GenreSaveDTO")
    public static GenreSaveDTO getGenreSaveDTO() {
        return GenreSaveDTO.builder()
                .name(Faker.instance().music().genre())
                .createdAt(Instancio.of(GenreSaveDTO.class)
                        .generate(Select.field(GenreSaveDTO::getCreatedAt),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(45), LocalDate.now().minusYears(25)))
                        .create().getCreatedAt())
                .genreDirection(Instancio.of(GenreSaveDTO.class)
                        .generate(Select.field(GenreSaveDTO::getGenreDirection), x -> x.enumOf(GenreDirection.class))
                        .create().getGenreDirection())
                .build();
    }

    @Step("Подготовка MediaTypeSaveDTO")
    public static MediaTypeSaveDTO getMediaTypeSaveDTO() {
        return MediaTypeSaveDTO.builder()
                .name(Faker.instance().company().buzzword())
                .createdAt(Instancio.of(MediaTypeSaveDTO.class)
                        .generate(Select.field(MediaTypeSaveDTO::getCreatedAt),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(35), LocalDate.now().minusYears(25)))
                        .create().getCreatedAt())
                .build();
    }

    @Step("Подготовка CustomerSaveDTO")
    public static CustomerSaveDTO getCustomerSaveDTO() {
        return CustomerSaveDTO.builder()
                .firstName(Faker.instance().name().firstName())
                .lastName(Faker.instance().name().lastName())
                .address(Faker.instance().address().fullAddress())
                .city(Faker.instance().address().city())
                .state(Faker.instance().address().state())
                .country(Faker.instance().address().country())
                .postalCode(Faker.instance().address().zipCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(Faker.instance().finance().bic())
                .email(Faker.instance().internet().emailAddress())
                .birthDate(Instancio.of(CustomerSaveDTO.class)
                        .generate(Select.field(CustomerSaveDTO::getBirthDate),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(65), LocalDate.now().minusYears(25)))
                        .create().getBirthDate())
                .build();
    }

    @Step("Подготовка EmployeeSaveDTO")
    public static EmployeeSaveDTO getEmployeeSaveDTO() {
        return EmployeeSaveDTO.builder()
                .firstName(Faker.instance().name().firstName())
                .lastName(Faker.instance().name().lastName())
                .address(Faker.instance().address().fullAddress())
                .city(Faker.instance().address().city())
                .state(Faker.instance().address().state())
                .country(Faker.instance().address().country())
                .postalCode(Faker.instance().address().zipCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(Faker.instance().finance().bic())
                .email(Faker.instance().internet().emailAddress())
                .position(Instancio.of(EmployeeSaveDTO.class)
                        .generate(Select.field(EmployeeSaveDTO::getPosition), x -> x.enumOf(Position.class))
                        .create().getPosition())
                .hireDate(Instancio.of(EmployeeSaveDTO.class)
                        .generate(Select.field(EmployeeSaveDTO::getHireDate),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(5), LocalDate.now().minusYears(1)))
                        .create().getBirthDate())
                .birthDate(Instancio.of(EmployeeSaveDTO.class)
                        .generate(Select.field(EmployeeSaveDTO::getBirthDate),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(35), LocalDate.now().minusYears(20)))
                        .create().getBirthDate())
                .build();
    }

    @Step("Подготовка InvoiceSaveDTO")
    public static InvoiceSaveDTO getInvoiceSaveDTO(CustomerDTO customer, EmployeeDTO employee) {
        return InvoiceSaveDTO.builder()
                .invoiceDate(ZonedDateTime.now())
                .billingAddress(Faker.instance().address().fullAddress())
                .billingCity(Faker.instance().address().city())
                .billingState(Faker.instance().address().state())
                .billingCountry(Faker.instance().address().country())
                .billingPostalCode(Faker.instance().address().zipCode())
                .customer(customer)
                .employee(employee)
                .total(BigDecimal.TEN)
                .build();
    }

    @Step("Подготовка TrackSaveDTO")
    public static TrackSaveDTO getTrackSaveDTO(AlbumDTO album,
                                               MediaTypeDTO mediaType,
                                               GenreDTO genre
    ) {
        return TrackSaveDTO.builder()
                .name(Faker.instance().lordOfTheRings().location())
                .author(Faker.instance().artist().name())
                .milliseconds(Faker.instance().number().numberBetween(120000, 240000))
                .bytes(Faker.instance().number().numberBetween(2000000, 6000000))
                .unitPrice(BigDecimal.TEN)
                .album(album)
                .mediaType(mediaType)
                .genre(genre)
                .createdAt(Instancio.of(TrackSaveDTO.class)
                        .generate(Select.field(TrackSaveDTO::getCreatedAt),
                                x -> x.temporal().zonedDateTime()
                                        .range(ZonedDateTime.now().minusYears(40), ZonedDateTime.now().minusYears(1)))
                        .create().getCreatedAt())
                .build();
    }

    @Step("Подготовка InvoiceLineSaveDTO")
    public static InvoiceLineSaveDTO getInvoiceLineSaveDTO(TrackDTO track, InvoiceDTO invoice) {
        return InvoiceLineSaveDTO.builder()
                .unitPrice(BigDecimal.TEN)
                .quantity(Faker.instance().number().numberBetween(2, 20))
                .invoice(invoice)
                .track(track)
                .build();
    }

    @Step("Обновляем данные ArtistDTO")
    public static ArtistDTO getArtistDTO(ArtistDTO dtoBeforeChange) {
        return  ArtistDTO.builder()
                .id(dtoBeforeChange.getId())
                .name(Faker.instance().artist().name())
                .surname(Faker.instance().name().lastName())
                .pseudonym(Faker.instance().dune().planet())
                .birthDate(getArtistSaveDto().getBirthDate())
                .build();
    }

    @Step("Обновляем данные AlbumDTO")
    public static AlbumDTO getAlbumDTO(AlbumDTO dtoBeforeChange) {
        return  AlbumDTO.builder()
                .id(dtoBeforeChange.getId())
                .title(Faker.instance().elderScrolls().creature())
                .albumType(AlbumType.ALBUM)
                .createdAt(ZonedDateTime.now().minusYears(20))
                .artist(dtoBeforeChange.getArtist())
                .build();
    }

    @Step("Обновляем данные GenreDTO")
    public static GenreDTO getGenreDTO(GenreDTO dtoBeforeChange) {
        return GenreDTO.builder()
                .id(dtoBeforeChange.getId())
                .genreDirection(getGenreSaveDTO().getGenreDirection())
                .createdAt(getGenreSaveDTO().getCreatedAt())
                .name(Faker.instance().music().genre())
                .build();
    }

    @Step("Обновляем данные MediaTypeDTO")
    public static MediaTypeDTO getMediaTypeDTO(MediaTypeDTO dtoBeforeChange) {
        return MediaTypeDTO.builder()
                .id(dtoBeforeChange.getId())
                .name(Faker.instance().company().buzzword())
                .createdAt(getMediaTypeSaveDTO().getCreatedAt()).build();
    }

    @Step("Обновляем данные CustomerDTO")
    public static CustomerDTO getCustomerDTO(CustomerDTO dtoBeforeChange) {
        return  CustomerDTO.builder()
                .id(dtoBeforeChange.getId())
                .birthDate(getCustomerSaveDTO().getBirthDate())
                .firstName(Faker.instance().name().firstName())
                .lastName(Faker.instance().name().lastName())
                .address(Faker.instance().address().fullAddress())
                .city(Faker.instance().address().city())
                .state(Faker.instance().address().state())
                .country(Faker.instance().address().country())
                .postalCode(Faker.instance().address().zipCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(Faker.instance().finance().bic())
                .email(Faker.instance().internet().emailAddress())
                .build();
    }

    @Step("Обновляем данные EmployeeDTO")
    public static EmployeeDTO getEmployeeDto(EmployeeDTO dtoBeforeChange) {
        return EmployeeDTO.builder()
                .id(dtoBeforeChange.getId())
                .firstName(Faker.instance().name().firstName())
                .lastName(Faker.instance().name().lastName())
                .address(Faker.instance().address().fullAddress())
                .city(Faker.instance().address().city())
                .state(Faker.instance().address().state())
                .country(Faker.instance().address().country())
                .postalCode(Faker.instance().address().zipCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(Faker.instance().finance().bic())
                .email(Faker.instance().internet().emailAddress())
                .position(dtoBeforeChange.getPosition())
                .birthDate(dtoBeforeChange.getBirthDate())
                .hireDate(dtoBeforeChange.getHireDate())
                .build();
    }

    @Step("Обновляем данные InvoiceDTO")
    public static InvoiceDTO getInvoiceDTO(InvoiceDTO dtoBeforeChange) {
        return InvoiceDTO.builder()
                .id(dtoBeforeChange.getId())
                .customer(dtoBeforeChange.getCustomer())
                .employee(dtoBeforeChange.getEmployee())
                .invoiceDate(ZonedDateTime.now())
                .billingAddress(Faker.instance().address().fullAddress())
                .billingCity(Faker.instance().address().city())
                .billingState(Faker.instance().address().state())
                .billingCountry(Faker.instance().address().country())
                .billingPostalCode(Faker.instance().address().zipCode())
                .total(BigDecimal.ONE)
                .build();
    }

    @Step("Обновляем данные TrackDTO")
    public static TrackDTO getTrackDto(TrackDTO dtoBeforeChange) {
        return TrackDTO.builder()
                .id(dtoBeforeChange.getId())
                .name(Faker.instance().lordOfTheRings().location())
                .author(Faker.instance().artist().name())
                .milliseconds(Faker.instance().number().numberBetween(120000, 240000))
                .bytes(Faker.instance().number().numberBetween(2000000, 6000000))
                .unitPrice(BigDecimal.ONE)
                .album(dtoBeforeChange.getAlbum())
                .mediaType(dtoBeforeChange.getMediaType())
                .genre(dtoBeforeChange.getGenre())
                .createdAt(Instancio.of(TrackSaveDTO.class)
                        .generate(Select.field(TrackSaveDTO::getCreatedAt),
                                x -> x.temporal().zonedDateTime()
                                        .range(ZonedDateTime.now().minusYears(40), ZonedDateTime.now().minusYears(1)))
                        .create().getCreatedAt())
                .build();
    }

    @Step("Обновляем данные InvoiceLineDTO")
    public static InvoiceLineDTO getInvoiceLineDto(InvoiceLineDTO dtoBeforeChange) {
        return InvoiceLineDTO.builder()
                .id(dtoBeforeChange.getId())
                .quantity(Faker.instance().number().numberBetween(2, 20))
                .unitPrice(BigDecimal.ONE)
                .invoice(dtoBeforeChange.getInvoice())
                .track(dtoBeforeChange.getTrack())
                .build();
    }

    static class Params {
        private static final String UNIQUE_ID = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        public static final String userPass = "aBAsdasdasd";
        public static final String userName = "TestUser_" + UNIQUE_ID;
        public static final String email = UNIQUE_ID + "@test.com";
        public static final String name = "TestUser";
    }

}
