package digital.store.jdbc.utils;

import com.github.javafaker.Faker;
import digital.store.jdbc.model.*;
import digital.store.jdbc.model.enumpack.AlbumType;
import digital.store.jdbc.model.enumpack.GenreDirection;
import digital.store.jdbc.model.enumpack.Position;
import io.qameta.allure.Step;
import org.instancio.Instancio;
import org.instancio.Select;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class DataUtil {

    @Step("Подготовка ArtistDto")
    public static ArtistDto getArtistDto() {
        return ArtistDto.builder()
                .artistId(UUID.randomUUID())
                .name(Faker.instance().artist().name())
                .surname(Faker.instance().name().lastName())
                .pseudonym(Faker.instance().dune().planet())
                .birthDate(Instancio.of(ArtistDto.class)
                        .generate(Select.field(ArtistDto::getBirthDate),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(35), LocalDate.now().minusYears(25)))
                        .create().getBirthDate())
                .build();
    }

    @Step("Подготовка AlbumDto для artistId={artistId}")
    public static AlbumDto getAlbumDto(UUID artistId) {
        return AlbumDto.builder()
                .albumId(UUID.randomUUID())
                .title(Faker.instance().elderScrolls().creature())
                .albumType(Instancio.of(AlbumDto.class)
                        .generate(Select.field(AlbumDto::getAlbumType), x -> x.enumOf(AlbumType.class))
                        .create().getAlbumType())
                .createdAt(Instancio.of(AlbumDto.class)
                        .generate(Select.field(AlbumDto::getCreatedAt),
                                x -> x.temporal().zonedDateTime()
                                        .range(ZonedDateTime.now().minusYears(5), ZonedDateTime.now().minusYears(1)))
                        .create().getCreatedAt())
                .artistId(artistId)
                .build();
    }

    @Step("Подготовка GenreDto")
    public static GenreDto getGenreDto() {
        return GenreDto.builder()
                .genreId(UUID.randomUUID())
                .name(Faker.instance().music().genre())
                .createdAt(Instancio.of(GenreDto.class)
                        .generate(Select.field(GenreDto::getCreatedAt),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(45), LocalDate.now().minusYears(25)))
                        .create().getCreatedAt())
                .genreDirection(Instancio.of(GenreDto.class)
                        .generate(Select.field(GenreDto::getGenreDirection), x -> x.enumOf(GenreDirection.class))
                        .create().getGenreDirection())
                .build();
    }

    @Step("Подготовка MediaTypeDto")
    public static MediaTypeDto getMediaTypeDto() {
        return MediaTypeDto.builder()
                .mediaTypeId(UUID.randomUUID())
                .name(Faker.instance().company().buzzword())
                .createdAt(Instancio.of(MediaTypeDto.class)
                        .generate(Select.field(MediaTypeDto::getCreatedAt),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(35), LocalDate.now().minusYears(25)))
                        .create().getCreatedAt())
                .build();
    }

    @Step("Подготовка TrackDto для albumId={albumId}")
    public static TrackDto getTrackDto(UUID albumId, UUID genreId, UUID mediaTypeId) {
        return TrackDto.builder()
                .trackId(UUID.randomUUID())
                .name(Faker.instance().lordOfTheRings().location())
                .author(Faker.instance().artist().name())
                .milliseconds(Faker.instance().number().numberBetween(120000, 240000))
                .bytes(Faker.instance().number().numberBetween(2000000, 6000000))
                .unitPrice(BigDecimal.TEN)
                .createdAt(Instancio.of(TrackDto.class)
                        .generate(Select.field(TrackDto::getCreatedAt),
                                x -> x.temporal().zonedDateTime()
                                        .range(ZonedDateTime.now().minusYears(40), ZonedDateTime.now().minusYears(1)))
                        .create().getCreatedAt())
                .albumId(albumId)
                .genreId(genreId)
                .mediaTypeId(mediaTypeId)
                .build();
    }

    @Step("Подготовка CustomerDto")
    public static CustomerDto getCustomerDto() {
        return CustomerDto.builder()
                .customerId(UUID.randomUUID())
                .name(Faker.instance().name().firstName())
                .surname(Faker.instance().name().lastName())
                .address(Faker.instance().address().fullAddress())
                .city(Faker.instance().address().city())
                .state(Faker.instance().address().state())
                .country(Faker.instance().address().country())
                .postalCode(Faker.instance().address().zipCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(Faker.instance().finance().bic())
                .email(Faker.instance().internet().emailAddress())
                .birthDate(Instancio.of(CustomerDto.class)
                        .generate(Select.field(CustomerDto::getBirthDate),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(65), LocalDate.now().minusYears(25)))
                        .create().getBirthDate())
                .build();
    }

    @Step("Подготовка EmployeeDto")
    public static EmployeeDto getEmployeeDto() {
        return EmployeeDto.builder()
                .employeeId(UUID.randomUUID())
                .name(Faker.instance().name().firstName())
                .surname(Faker.instance().name().lastName())
                .address(Faker.instance().address().fullAddress())
                .city(Faker.instance().address().city())
                .state(Faker.instance().address().state())
                .country(Faker.instance().address().country())
                .postalCode(Faker.instance().address().zipCode())
                .phone(Faker.instance().phoneNumber().cellPhone())
                .fax(Faker.instance().finance().bic())
                .email(Faker.instance().internet().emailAddress())
                .position(Instancio.of(EmployeeDto.class)
                        .generate(Select.field(EmployeeDto::getPosition), x -> x.enumOf(Position.class))
                        .create().getPosition())
                .hireDate(Instancio.of(EmployeeDto.class)
                        .generate(Select.field(EmployeeDto::getHireDate),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(5), LocalDate.now().minusYears(1)))
                        .create().getHireDate())
                .birthDate(Instancio.of(EmployeeDto.class)
                        .generate(Select.field(EmployeeDto::getBirthDate),
                                x -> x.temporal().localDate()
                                        .range(LocalDate.now().minusYears(35), LocalDate.now().minusYears(20)))
                        .create().getBirthDate())
                .build();
    }

    @Step("Подготовка InvoiceDto для customerId={customerId}")
    public static InvoiceDto getInvoiceDto(UUID customerId, UUID employeeId) {
        return InvoiceDto.builder()
                .invoiceId(UUID.randomUUID())
                .invoiceDate(ZonedDateTime.now())
                .billingAddress(Faker.instance().address().fullAddress())
                .billingCity(Faker.instance().address().city())
                .billingState(Faker.instance().address().state())
                .billingCountry(Faker.instance().address().country())
                .billingPostalCode(Faker.instance().address().zipCode())
                .total(BigDecimal.TEN)
                .customerId(customerId)
                .employeeId(employeeId)
                .build();
    }

    @Step("Подготовка InvoiceLineDto для invoiceId={invoiceId}")
    public static InvoiceLineDto getInvoiceLineDto(UUID invoiceId, UUID trackId) {
        return InvoiceLineDto.builder()
                .invoiceLineId(UUID.randomUUID())
                .unitPrice(BigDecimal.TEN)
                .quantity(Faker.instance().number().numberBetween(1, 10))
                .invoiceId(invoiceId)
                .trackId(trackId)
                .build();
    }

}
