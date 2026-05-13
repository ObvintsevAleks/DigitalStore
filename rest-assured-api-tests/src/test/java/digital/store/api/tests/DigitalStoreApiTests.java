package digital.store.api.tests;

import digital.store.api.configs.Config;
import digital.store.api.model.*;
import digital.store.api.model.security.LoginPojo;
import digital.store.api.model.security.RegistrationPojo;
import digital.store.api.utils.DataUtil;
import digital.store.api.utils.ICheckResponse;
import digital.store.api.utils.ISendRequest;
import digital.store.api.utils.ResponseDto;
import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static digital.store.api.configs.Config.EndpointUtil.*;
import static digital.store.api.configs.Config.EndpointUtil.Album.*;
import static digital.store.api.configs.Config.EndpointUtil.Artist.*;
import static digital.store.api.configs.Config.EndpointUtil.Customer.*;
import static digital.store.api.configs.Config.EndpointUtil.Employee.*;
import static digital.store.api.configs.Config.EndpointUtil.Genre.genreAll;
import static digital.store.api.configs.Config.EndpointUtil.Genre.genreId;
import static digital.store.api.configs.Config.EndpointUtil.Invoice.*;
import static digital.store.api.configs.Config.EndpointUtil.InvoiceLine.invoiceLineId;
import static digital.store.api.configs.Config.EndpointUtil.MediaType.mediaTypeAll;
import static digital.store.api.configs.Config.EndpointUtil.MediaType.mediaTypeId;
import static digital.store.api.configs.Config.EndpointUtil.Track.*;
import static digital.store.api.configs.Config.installSpecification;
import static io.restassured.RestAssured.given;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@ExtendWith(SoftAssertionsExtension.class)
public class DigitalStoreApiTests implements ISendRequest, ICheckResponse {

    private final static String URL = "http://localhost:8181/";

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Nested
    @Order(1)
    class AuthTest {
        @BeforeAll
        public static void initAll() {
            installSpecification(Config.requestSpecAuthNo(URL), Config.responseSpec);
        }

        // registration user and check that user get token
        @Test
        public void testUserCreateAndGetToken() {
            RegistrationPojo registrationPojo = DataUtil.getRegistrationPojo();
            String message = postWithGetString(Config.EndpointUtil.REGISTER, registrationPojo, "message");
            Assertions.assertEquals(message, registrationPojo.getUsername() + " registered successfully!");
            LoginPojo loginPojo = DataUtil.getLoginPojo();
            String token = postWithGetString(Config.EndpointUtil.LOGIN, loginPojo, "token");
            Assertions.assertNotNull(token,"Ошибка! Пользователь получил пустой токен!");
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class ControllerTests {
        private final static String URL = "http://localhost:8181/";

        @BeforeAll
        public static void init() {
            installSpecification(Config.requestSpecAuthNo(URL), Config.responseSpec);
            String token = postWithGetString(Config.EndpointUtil.LOGIN, DataUtil.getLoginPojo(), "token");
            installSpecification(Config.requestSpecAuth(URL, token), Config.responseSpec);
        }

        @DisplayName("Тест на rest-assured - проверка artist-controller")
        @Description("Тест на rest-assured - проверка artist-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testAArtistController() {
            ArtistSaveDTO artistSaveDTO = DataUtil.getArtistSaveDto();
            ResponseDto<ArtistDTO> actualDto = post(ARTISTS, artistSaveDTO, ArtistDTO.class);
            ResponseDto<ArtistDTO> expectedDto = get(artistId(actualDto.getBody().getId()), ArtistDTO.class);
            ResponseDto<List<ArtistDTO>> expectedDtos2 = getList(artistsByName(actualDto.getBody().getName()), ArtistDTO.class);
            ResponseDto<List<ArtistDTO>> expectedDtos3 = getList(artistsByPseudonym(actualDto.getBody().getPseudonym()), ArtistDTO.class);
            ArtistDTO actualChangedDto = DataUtil.getArtistDTO(actualDto.getBody());
            ResponseDto<ArtistDTO> expectedChangedDto = put(ARTISTS, actualChangedDto, ArtistDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2, expectedDtos3), softly);
            softly.assertThat(delete(artistId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }

        @DisplayName("Тест на rest-assured - проверка album-controller")
        @Description("Тест на rest-assured - проверка album-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testBBAlbumController() {
            ArtistSaveDTO artistSaveDTO = DataUtil.getArtistSaveDto();
            ResponseDto<ArtistDTO> artistDTO = post(ARTISTS, artistSaveDTO, ArtistDTO.class);
            AlbumSaveDto albumSaveDto = DataUtil.getAlbumSaveDto(artistDTO.getBody());
            ResponseDto<AlbumDTO> actualDto = post(ALBUMS, albumSaveDto, AlbumDTO.class);
            ResponseDto<AlbumDTO> expectedDto = get(albumsId(actualDto.getBody().getId()), AlbumDTO.class);
            ResponseDto<List<AlbumDTO>> expectedDtos2 = getList(albumsByArtistId(artistDTO.getBody().getId()), AlbumDTO.class);
            ResponseDto<List<AlbumDTO>> expectedDtos3 = getList(albumsByTitle(actualDto.getBody().getTitle()), AlbumDTO.class);
            ResponseDto<List<AlbumDTO>> expectedDtos4 = getList(albumsByArtistPseudonym(artistDTO.getBody().getPseudonym()), AlbumDTO.class);
            AlbumDTO actualChangedDto = DataUtil.getAlbumDTO(actualDto.getBody());
            ResponseDto<AlbumDTO> expectedChangedDto = put(ALBUMS, actualChangedDto, AlbumDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2, expectedDtos3, expectedDtos4), softly);
            softly.assertThat(delete(albumsId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }

        @DisplayName("Тест на rest-assured - проверка genre-controller")
        @Description("Тест на rest-assured - проверка genre-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testCGenreController() {
            GenreSaveDTO genreSaveDTO = DataUtil.getGenreSaveDTO();
            ResponseDto<GenreDTO> actualDto = post(GENRES, genreSaveDTO, GenreDTO.class);
            ResponseDto<GenreDTO> expectedDto = get(genreId(actualDto.getBody().getId()), GenreDTO.class);
            ResponseDto<List<GenreDTO>> expectedDtos2 = getList(genreAll(), GenreDTO.class);
            GenreDTO actualChangedDto = DataUtil.getGenreDTO(actualDto.getBody());
            ResponseDto<GenreDTO> expectedChangedDto = put(GENRES, actualChangedDto, GenreDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2), softly);
            softly.assertThat(delete(genreId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }

        @DisplayName("Тест на rest-assured - проверка media-type-controller")
        @Description("Тест на rest-assured - проверка media-type-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testDMediaTypeController() {
            MediaTypeSaveDTO mediaTypeSaveDTO = DataUtil.getMediaTypeSaveDTO();
            ResponseDto<MediaTypeDTO> actualDto = post(MEDIA_TYPES, mediaTypeSaveDTO, MediaTypeDTO.class);
            ResponseDto<MediaTypeDTO> expectedDto = get(mediaTypeId(actualDto.getBody().getId()), MediaTypeDTO.class);
            ResponseDto<List<MediaTypeDTO>> expectedDtos2 = getList(mediaTypeAll(), MediaTypeDTO.class);
            MediaTypeDTO actualChangedDto = DataUtil.getMediaTypeDTO(actualDto.getBody());
            ResponseDto<MediaTypeDTO> expectedChangedDto = put(MEDIA_TYPES, actualChangedDto, MediaTypeDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2), softly);
            softly.assertThat(delete(mediaTypeId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }

        @DisplayName("Тест на rest-assured - проверка customer-controller")
        @Description("Тест на rest-assured - проверка customer-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testFCustomerController() {
            CustomerSaveDTO customerSaveDTO = DataUtil.getCustomerSaveDTO();
            ResponseDto<CustomerDTO> actualDto = post(CUSTOMERS, customerSaveDTO, CustomerDTO.class);
            ResponseDto<CustomerDTO> expectedDto = get(customerId(actualDto.getBody().getId()), CustomerDTO.class);
            ResponseDto<List<CustomerDTO>> expectedDtos2 = getList(customersByFirstname(actualDto.getBody().getFirstName()), CustomerDTO.class);
            ResponseDto<List<CustomerDTO>> expectedDtos3 = getList(customersByLastname(actualDto.getBody().getLastName()), CustomerDTO.class);
            CustomerDTO actualChangedDto = DataUtil.getCustomerDTO(actualDto.getBody());
            ResponseDto<CustomerDTO> expectedChangedDto = put(CUSTOMERS, actualChangedDto, CustomerDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2, expectedDtos3), softly);
            softly.assertThat(delete(customerId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }

        @DisplayName("Тест на rest-assured - проверка employee-controller")
        @Description("Тест на rest-assured - проверка employee-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testGEmployeeController() {
            EmployeeSaveDTO employeeSaveDTO = DataUtil.getEmployeeSaveDTO();
            ResponseDto<EmployeeDTO> actualDto = post(EMPLOYEES, employeeSaveDTO, EmployeeDTO.class);
            ResponseDto<EmployeeDTO> expectedDto = get(employeeId(actualDto.getBody().getId()), EmployeeDTO.class);
            ResponseDto<List<EmployeeDTO>> expectedDtos2 = getList(employeesByFirstname(actualDto.getBody().getFirstName()), EmployeeDTO.class);
            ResponseDto<List<EmployeeDTO>> expectedDtos3 = getList(employeesByLastname(actualDto.getBody().getLastName()), EmployeeDTO.class);
            EmployeeDTO actualChangedDto = DataUtil.getEmployeeDto(actualDto.getBody());
            ResponseDto<EmployeeDTO> expectedChangedDto = put(EMPLOYEES, actualChangedDto, EmployeeDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2, expectedDtos3), softly);
            softly.assertThat(delete(employeeId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }

        @DisplayName("Тест на rest-assured - проверка invoice-controller")
        @Description("Тест на rest-assured - проверка invoice-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testHInvoiceController() {
            EmployeeSaveDTO employeeSaveDTO = DataUtil.getEmployeeSaveDTO();
            CustomerSaveDTO customerSaveDTO = DataUtil.getCustomerSaveDTO();
            ResponseDto<EmployeeDTO> employeeDTO = post(EMPLOYEES, employeeSaveDTO, EmployeeDTO.class);
            ResponseDto<CustomerDTO> customerDTO = post(CUSTOMERS, customerSaveDTO, CustomerDTO.class);
            InvoiceSaveDTO invoiceSaveDto = DataUtil.getInvoiceSaveDTO(customerDTO.getBody(), employeeDTO.getBody());
            ResponseDto<InvoiceDTO> actualDto = post(INVOICES, invoiceSaveDto, InvoiceDTO.class);
            ResponseDto<InvoiceDTO> expectedDto = get(invoiceId(actualDto.getBody().getId()), InvoiceDTO.class);
            ResponseDto<List<InvoiceDTO>> expectedDtos2 = getList(invoicesByCustomerId(customerDTO.getBody().getId()), InvoiceDTO.class);
            ResponseDto<List<InvoiceDTO>> expectedDtos3 = getList(invoicesByEmployeeId(employeeDTO.getBody().getId()), InvoiceDTO.class);
            InvoiceDTO actualChangedDto = DataUtil.getInvoiceDTO(actualDto.getBody());
            ResponseDto<InvoiceDTO> expectedChangedDto = put(INVOICES, actualChangedDto, InvoiceDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2, expectedDtos3), softly);
            softly.assertThat(delete(invoiceId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }

        @DisplayName("Тест на rest-assured - проверка track-controller")
        @Description("Тест на rest-assured - проверка track-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testJTrackController() {
            ArtistSaveDTO artistSaveDTO = DataUtil.getArtistSaveDto();
            ArtistDTO artistDTO = post(ARTISTS, artistSaveDTO, ArtistDTO.class).getBody();
            AlbumSaveDto albumSaveDto = DataUtil.getAlbumSaveDto(artistDTO);
            AlbumDTO albumDTO = post(ALBUMS, albumSaveDto, AlbumDTO.class).getBody();
            GenreSaveDTO genreSaveDto = DataUtil.getGenreSaveDTO();
            MediaTypeSaveDTO mediaTypeSaveDTO = DataUtil.getMediaTypeSaveDTO();
            GenreDTO genreDTO = post(GENRES, genreSaveDto, GenreDTO.class).getBody();
            MediaTypeDTO mediaTypeDTO = post(MEDIA_TYPES, mediaTypeSaveDTO, MediaTypeDTO.class).getBody();
            TrackSaveDTO trackSaveDTO = DataUtil.getTrackSaveDTO(albumDTO, mediaTypeDTO, genreDTO);
            ResponseDto<TrackDTO> actualDto = post(TRACKS, trackSaveDTO, TrackDTO.class);
            ResponseDto<TrackDTO> expectedDto = get(Track.trackId(actualDto.getBody().getId()), TrackDTO.class);
            TrackDTO actualChangedDto = DataUtil.getTrackDto(actualDto.getBody());
            ResponseDto<TrackDTO> expectedChangedDto = put(TRACKS, actualChangedDto, TrackDTO.class);
            ResponseDto<List<TrackDTO>> expectedDtos2 = getList(tracksByAlbumId(albumDTO.getId()), TrackDTO.class);
            ResponseDto<List<TrackDTO>> expectedDtos3 = getList(tracksByArtistId((artistDTO.getId())), TrackDTO.class);
            ResponseDto<List<TrackDTO>> expectedDtos4 = getList(tracksByArtistPseudonym((artistDTO.getPseudonym())), TrackDTO.class);
            ResponseDto<List<TrackDTO>> expectedDtos5 = getList(tracksByGenreId((genreDTO.getId())), TrackDTO.class);
            ResponseDto<List<TrackDTO>> expectedDtos6 = getList(tracksByMediaTypeId((mediaTypeDTO.getId())), TrackDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2, expectedDtos3, expectedDtos4, expectedDtos5, expectedDtos6), softly);
            softly.assertThat(delete(trackId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }

        @DisplayName("Тест на rest-assured - проверка invoice-line-controller")
        @Description("Тест на rest-assured - проверка invoice-line-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testLInvoiceLineController() {
            ArtistSaveDTO artistSaveDTO = DataUtil.getArtistSaveDto();
            ResponseDto<ArtistDTO> artistDTO = post(ARTISTS, artistSaveDTO, ArtistDTO.class);
            AlbumSaveDto albumSaveDto = DataUtil.getAlbumSaveDto(artistDTO.getBody());
            ResponseDto<AlbumDTO> albumDTO = post(ALBUMS, albumSaveDto, AlbumDTO.class);
            GenreDTO genreDTO = getList(genreAll(), GenreDTO.class).getBody().get(0);
            MediaTypeDTO mediaTypeDTO = getList(mediaTypeAll(), MediaTypeDTO.class).getBody().get(0);
            TrackSaveDTO trackSaveDTO = DataUtil.getTrackSaveDTO(albumDTO.getBody(), mediaTypeDTO, genreDTO);
            EmployeeSaveDTO employeeSaveDTO = DataUtil.getEmployeeSaveDTO();
            CustomerSaveDTO customerSaveDTO = DataUtil.getCustomerSaveDTO();
            ResponseDto<EmployeeDTO> employeeDTO = post(EMPLOYEES, employeeSaveDTO, EmployeeDTO.class);
            ResponseDto<CustomerDTO> customerDTO = post(CUSTOMERS, customerSaveDTO, CustomerDTO.class);
            InvoiceSaveDTO invoiceSaveDto = DataUtil.getInvoiceSaveDTO(customerDTO.getBody(), employeeDTO.getBody());
            ResponseDto<InvoiceDTO> invoiceDTO = post(INVOICES, invoiceSaveDto, InvoiceDTO.class);
            ResponseDto<TrackDTO> trackDTO = post(TRACKS, trackSaveDTO, TrackDTO.class);
            InvoiceLineSaveDTO invoiceLineSaveDTO = DataUtil.getInvoiceLineSaveDTO(trackDTO.getBody(), invoiceDTO.getBody());
            ResponseDto<InvoiceLineDTO> actualDto = post(INVOICE_LINES, invoiceLineSaveDTO, InvoiceLineDTO.class);
            ResponseDto<InvoiceLineDTO> expectedDto = get(invoiceLineId(actualDto.getBody().getId()), InvoiceLineDTO.class);
            ResponseDto<List<InvoiceLineDTO>> expectedDtos2 = getList(InvoiceLine.invoiceLinesByInvoiceId(invoiceDTO.getBody().getId()), InvoiceLineDTO.class);
            ResponseDto<List<InvoiceLineDTO>> expectedDtos3 = getList(InvoiceLine.invoiceLinesByTrackId(trackDTO.getBody().getId()), InvoiceLineDTO.class);
            InvoiceLineDTO actualChangedDto = DataUtil.getInvoiceLineDto(actualDto.getBody());
            ResponseDto<InvoiceLineDTO> expectedChangedDto = put(INVOICE_LINES, actualChangedDto, InvoiceLineDTO.class);

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.getBody(), List.of(expectedDtos2, expectedDtos3), softly);
            softly.assertThat(delete(invoiceLineId(actualDto.getBody().getId())).statusCode()).isEqualTo(204);
        }
    }

    @Step("Выполняем post запрос по адресу: {methodPass} и вытаскиваем из json {jsonBodyPass}")
    static <S> String postWithGetString(String methodPass, S body, String jsonBodyPass) {
        //подготовка тела запроса
        return given().body(body)
                //составление и отправка запроса
                .when().post(methodPass)
                //обработка после отправки запроса
                .then().extract().body().jsonPath().getString(jsonBodyPass);
    }

}
