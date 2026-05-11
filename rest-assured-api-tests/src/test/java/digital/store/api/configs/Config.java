package digital.store.api.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.UUID;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class Config {

    static {
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> {
                            ObjectMapper om = new ObjectMapper();
                            om.registerModule(new JavaTimeModule());
                            om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                            return om;
                        }));
    }

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectContentType(ContentType.JSON)
            .build();

    public static RequestSpecification requestSpecAuthNo(String url){
        return with()
                .baseUri(url)
                .basePath("/api")
                .log().all()
                .contentType(ContentType.JSON);
    }

    public static RequestSpecification requestSpecAuth(String url, String token){
        return with()
                .baseUri(url)
                .header(new Header("Authorization", "Bearer " + token))
                .basePath("/api")
                .log().all()
                .contentType(ContentType.JSON);
    }

    public static void installSpecification(RequestSpecification requestSpec, ResponseSpecification responseSpec){
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    public static class EndpointUtil {

        public static String ALBUMS = "/albums";
        public static String LOGIN = "/login";
        public static String REGISTER = "/register";
        public static String ARTISTS = "/artists";
        public static String CUSTOMERS = "/customers";
        public static String EMPLOYEES = "/employees";
        public static String GENRES = "/genres";
        public static String INVOICES = "/invoices";
        public static String INVOICE_LINES = "/invoice-lines";
        public static String MEDIA_TYPES = "/media-types";
        public static String TRACKS = "/tracks";

        public static class Album {
            public static String albumsId(UUID albumId) {
                return ALBUMS + "/" + albumId;
            }

            public static String albumsByArtistId(UUID artistId) {
                return ALBUMS + "/albums-by-artist-id/" + artistId;
            }

            public static String albumsByTitle(String title) {
                return ALBUMS + "/albums-by-title/" + title;
            }

            public static String albumsByArtistPseudonym(String pseudonym) {
                return ALBUMS + "/albums-by-artist-pseudonym/" + pseudonym;
            }
        }

        public static class Artist {
            public static String artistId(UUID artistId) {
                return ARTISTS + "/" + artistId;
            }

            public static String artistsByName(String name) {
                return ARTISTS + "/artists-by-name/" + name;
            }

            public static String artistsByPseudonym(String pseudonym) {
                return ARTISTS + "/artists-by-pseudonym/" + pseudonym;
            }
        }

        public static class Customer {
            public static String customerId(UUID customerId) {
                return CUSTOMERS + "/" + customerId;
            }

            public static String customersByFirstname(String firstName) {
                return CUSTOMERS + "/firstname/" + firstName;
            }

            public static String customersByLastname(String lastname) {
                return CUSTOMERS + "/lastname/" + lastname;
            }
        }

        public static class Employee {
            public static String employeeId(UUID employeeId) {
                return EMPLOYEES + "/" + employeeId;
            }

            public static String employeesByFirstname(String firstName) {
                return EMPLOYEES + "/firstname/" + firstName;
            }

            public static String employeesByLastname(String lastname) {
                return EMPLOYEES + "/lastname/" + lastname;
            }
        }

        public static class Genre {
            public static String genreId(UUID genreId) {
                return GENRES + "/" + genreId;
            }

            public static String genreAll() {
                return GENRES + "/all";
            }
        }

        public static class Invoice {
            public static String invoiceId(UUID invoiceId) {
                return INVOICES + "/" + invoiceId;
            }

            public static String invoicesByCustomerId(UUID customerId) {
                return INVOICES + "/invoices-by-customer/" + customerId;
            }

            public static String invoicesByEmployeeId(UUID employeeId) {
                return INVOICES + "/invoices-by-employee/" + employeeId;
            }
        }

        public static class InvoiceLine {
            public static String invoiceLineId(UUID invoiceLineId) {
                return INVOICE_LINES + "/" + invoiceLineId;
            }

            public static String invoiceLinesByTrackId(UUID trackId) {
                return INVOICE_LINES + "/invoice-lines-by-track/" + trackId;
            }

            public static String invoiceLinesByInvoiceId(UUID invoiceId) {
                return INVOICE_LINES + "/invoice-line-by-invoice/" + invoiceId;
            }
        }

        public static class MediaType {
            public static String mediaTypeId(UUID mediaTypeId) {
                return MEDIA_TYPES + "/" + mediaTypeId;
            }

            public static String mediaTypeAll() {
                return MEDIA_TYPES + "/all";
            }
        }

        public static class Track {
            public static String trackId(UUID trackId) {
                return TRACKS + "/" + trackId;
            }

            public static String tracksByAlbumId(UUID albumId) {
                return TRACKS + "/all-tracks-by-album/" + albumId;
            }

            public static String tracksByGenreId(UUID genreId) {
                return TRACKS + "/all-tracks-by-genre/" + genreId;
            }

            public static String tracksByMediaTypeId(UUID mediaTypeId) {
                return TRACKS + "/all-tracks-by-media-type/" + mediaTypeId;
            }

            public static String tracksByArtistId(UUID artistId) {
                return TRACKS + "/tracks-by-artist-id/" + artistId;
            }

            public static String tracksByArtistPseudonym(String pseudonym) {
                return TRACKS + "/tracks-by-artist-pseudonym/" + pseudonym;
            }
        }
    }

}
