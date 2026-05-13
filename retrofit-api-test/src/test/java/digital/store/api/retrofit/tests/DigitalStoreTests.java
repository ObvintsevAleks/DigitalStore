package digital.store.api.retrofit.tests;

import digital.store.api.retrofit.model.*;
import digital.store.api.retrofit.model.security.LoginPojo;
import digital.store.api.retrofit.model.security.RegistrationPojo;
import digital.store.api.retrofit.model.security.SuccessRegisterMessage;
import digital.store.api.retrofit.model.security.Token;
import digital.store.api.retrofit.util.DataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static io.qameta.allure.Allure.step;


@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@ExtendWith(SoftAssertionsExtension.class)
public class DigitalStoreTests extends BaseTest {

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Nested
    @Order(1)
    class AuthTest {
        @Test
        public void testUserCreateAndGetToken() {
            RegistrationPojo registrationPojo = DataUtil.getRegistrationPojo();
            Response<SuccessRegisterMessage> message = step("Выполняем post запрос - register controller",
                    () -> registerController.createUser(registrationPojo).execute());
            Assertions.assertEquals(message.body().getMessage(), registrationPojo.getUsername() + " registered successfully!");
            LoginPojo loginPojo = DataUtil.getLoginPojo();
            Response<Token> token = step("Выполняем post запрос - login controller",
                    () -> loginController.loginUser(loginPojo).execute());
            Assertions.assertNotNull(token.body().getToken(), "Ошибка! Пользователь получил пустой токен!");
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class ControllerTests {

        @BeforeEach
        public void init() throws IOException {
            Token token = loginController.loginUser(DataUtil.getLoginPojo()).execute().body();
            albumController = createAlbumController(token.getToken());
            artistController = createArtistController(token.getToken());
            customerController = createCustomerController(token.getToken());
            employeeController = createEmployeeController(token.getToken());
            genreController = createGenreController(token.getToken());
            invoiceController = createInvoiceController(token.getToken());
            invoiceLineController = createInvoiceLineController(token.getToken());
            mediaTypeController = createMediaTypeController(token.getToken());
            trackController = createTrackController(token.getToken());
        }

        @DisplayName("Тест на retrofit - проверка artist-controller")
        @Description("Тест на retrofit - проверка artist-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testAArtistController() {
            ArtistSaveDTO artistSaveDTO = DataUtil.getArtistSaveDto();
            Response<ArtistDTO> actualDto = step("Выполняем post запрос - artist controller",
                    () -> artistController.createArtist(artistSaveDTO).execute());
            Response<ArtistDTO> expectedDto = step("Выполняем get запрос - artist controller",
                    () -> artistController.getArtist(actualDto.body().getId()).execute());

            Response<List<ArtistDTO>> expectedDtos2 = step("Выполняем get запрос - artist controller - get artist by name",
                    () -> artistController.getArtistByName(actualDto.body().getName()).execute());
            Response<List<ArtistDTO>> expectedDtos3 = step("Выполняем get запрос - artist controller - get artist by pseudonym",
                    () -> artistController.getArtistByPseudonym(actualDto.body().getPseudonym()).execute());

            ArtistDTO actualChangedDto = DataUtil.getArtistDTO(actualDto.body());
            Response<ArtistDTO> expectedChangedDto = step("Выполняем put запрос - artist controller",
                    () -> artistController.updateArtist(actualChangedDto).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2, expectedDtos3), softly);
            step("Выполняем delete запрос и проверяем статус - artist controller",
                    () -> softly.assertThat(artistController.deleteArtist(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }

        @DisplayName("Тест на retrofit - проверка album-controller")
        @Description("Тест на retrofit - проверка album-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testBBAlbumController() {
            ArtistSaveDTO artistSaveDTO = DataUtil.getArtistSaveDto();
            Response<ArtistDTO> artistDTO = step("Выполняем post запрос - artist controller",
                    () -> artistController.createArtist(artistSaveDTO).execute());
            AlbumSaveDto albumSaveDto = DataUtil.getAlbumSaveDto(artistDTO.body());
            Response<AlbumDTO> actualDto = step("Выполняем post запрос - album controller",
                    () -> albumController.createAlbum(albumSaveDto).execute());
            Response<AlbumDTO> expectedDto = step("Выполняем get запрос - album controller - get album by id",
                    () -> albumController.getAlbum(actualDto.body().getId()).execute());
            Response<List<AlbumDTO>> expectedDtos2 = step("Выполняем get запрос - album controller - get album by artist id",
                    () -> albumController.getAlbumByArtistId(artistDTO.body().getId()).execute());
            Response<List<AlbumDTO>> expectedDtos3 = step("Выполняем get запрос - album controller - get album by title",
                    () -> albumController.getAlbumByTitle(actualDto.body().getTitle()).execute());
            Response<List<AlbumDTO>> expectedDtos4 = step("Выполняем get запрос - album controller - get album by artist pseudonym",
                    () -> albumController.getAlbumByArtistPseudonym(artistDTO.body().getPseudonym()).execute());
            AlbumDTO actualChangedDto = DataUtil.getAlbumDTO(actualDto.body());
            Response<AlbumDTO> expectedChangedDto = step("Выполняем put запрос - album controller",
                    () -> albumController.updateAlbum(actualChangedDto).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2, expectedDtos3, expectedDtos4), softly);
            step("Выполняем delete запрос - album controller",
                    () -> softly.assertThat(albumController.deleteAlbum(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }

        @DisplayName("Тест на retrofit - проверка genre-controller")
        @Description("Тест на retrofit - проверка genre-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testCGenreController() {
            GenreSaveDTO genreSaveDTO = DataUtil.getGenreSaveDTO();
            Response<GenreDTO> actualDto = step("Выполняем post запрос - genre controller",
                    () -> genreController.createGenre(genreSaveDTO).execute());
            Response<GenreDTO> expectedDto = step("Выполняем get запрос - genre controller by id",
                    () -> genreController.getGenre(actualDto.body().getId()).execute());
            Response<List<GenreDTO>> expectedDtos2 = step("Выполняем get запрос - genre controller - get all genres",
                    () -> genreController.getGenreAll().execute());
            GenreDTO actualChangedDto = DataUtil.getGenreDTO(actualDto.body());
            Response<GenreDTO> expectedChangedDto = step("Выполняем put запрос - genre controller",
                    () -> genreController.updateGenre(actualChangedDto).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2), softly);
            step("Выполняем delete запрос и проверяем статус - genre controller", () ->
                    softly.assertThat(genreController.deleteGenre(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }

        @DisplayName("Тест на retrofit - проверка media-type-controller")
        @Description("Тест на retrofit - проверка media-type-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testDMediaTypeController() {
            MediaTypeSaveDTO mediaTypeSaveDTO = DataUtil.getMediaTypeSaveDTO();
            Response<MediaTypeDTO> actualDto = step("Выполняем post запрос - mediaType controller",
                    () -> mediaTypeController.createMediaType(mediaTypeSaveDTO).execute());
            Response<MediaTypeDTO> expectedDto = step("Выполняем get запрос - mediaType controller",
                    () -> mediaTypeController.getMediaType(actualDto.body().getId()).execute());
            Response<List<MediaTypeDTO>> expectedDtos2 = step("Выполняем get запрос - mediaType controller - get all media types",
                    () -> mediaTypeController.getMediaTypeAll().execute());
            MediaTypeDTO actualChangedDto = DataUtil.getMediaTypeDTO(actualDto.body());
            Response<MediaTypeDTO> expectedChangedDto = step("Выполняем put запрос - mediaType controller",
                    () -> mediaTypeController.updateMediaType(actualChangedDto).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2), softly);
            step("Выполняем delete запрос - mediaType controller", () -> softly.assertThat(mediaTypeController.deleteMediaType(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }

        @DisplayName("Тест на retrofit - проверка customer-controller")
        @Description("Тест на retrofit - проверка customer-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testFCustomerController() {
            CustomerSaveDTO customerSaveDTO = DataUtil.getCustomerSaveDTO();
            Response<CustomerDTO> actualDto = step("Выполняем post запрос - customer controller",
                    () -> customerController.createCustomer(customerSaveDTO).execute());
            Response<CustomerDTO> expectedDto = step("Выполняем get запрос - customer controller - get customer by id",
                    () -> customerController.getCustomer(actualDto.body().getId()).execute());
            Response<List<CustomerDTO>> expectedDtos2 = step("Выполняем get запрос - customer controller - get customer by firstName",
                    () -> customerController.getCustomerByFirstname(actualDto.body().getFirstName()).execute());
            Response<List<CustomerDTO>> expectedDtos3 = step("Выполняем get запрос - customer controller - get customer by lastName",
                    () -> customerController.getCustomerByLastname(actualDto.body().getLastName()).execute());
            CustomerDTO actualChangedDto = DataUtil.getCustomerDTO(actualDto.body());
            Response<CustomerDTO> expectedChangedDto = step("Выполняем put запрос - customer controller",
                    () -> customerController.updateCustomer(actualChangedDto).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2, expectedDtos3), softly);
            step("Выполняем delete запрос - customer controller",
                    () -> softly.assertThat(customerController.deleteCustomer(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }

        @DisplayName("Тест на retrofit - проверка employee-controller")
        @Description("Тест на retrofit - проверка employee-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testGEmployeeController() {
            EmployeeSaveDTO employeeSaveDTO = DataUtil.getEmployeeSaveDTO();
            Response<EmployeeDTO> actualDto = step("Выполняем post запрос - employee controller",
                    () -> employeeController.createEmployee(employeeSaveDTO).execute());
            Response<EmployeeDTO> expectedDto = step("Выполняем get запрос - employee controller - get employee by id",
                    () -> employeeController.getEmployee(actualDto.body().getId()).execute());
            Response<List<EmployeeDTO>> expectedDtos2 = step("Выполняем get запрос - employee controller - get employee by firstName",
                    () -> employeeController.getEmployeeByFirstname(actualDto.body().getFirstName()).execute());
            Response<List<EmployeeDTO>> expectedDtos3 = step("Выполняем get запрос - employee controller - get employee by lastName",
                    () -> employeeController.getEmployeeByLastname(actualDto.body().getLastName()).execute());
            EmployeeDTO actualChangedDto = DataUtil.getEmployeeDto(actualDto.body());
            Response<EmployeeDTO> expectedChangedDto = step("Выполняем put запрос - employee controller",
                    () -> employeeController.updateEmployee(actualChangedDto).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2, expectedDtos3), softly);
            step("Выполняем delete запрос - employee controller",
                    () -> softly.assertThat(employeeController.deleteEmployee(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }

        @DisplayName("Тест на retrofit - проверка invoice-controller")
        @Description("Тест на retrofit - проверка invoice-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testHInvoiceController() {
            EmployeeSaveDTO employeeSaveDTO = DataUtil.getEmployeeSaveDTO();
            CustomerSaveDTO customerSaveDTO = DataUtil.getCustomerSaveDTO();
            Response<EmployeeDTO> employeeDTO = step("Выполняем post запрос - employee controller",
                    () -> employeeController.createEmployee(employeeSaveDTO).execute());
            Response<CustomerDTO> customerDTO = step("Выполняем post запрос - customer controller",
                    () -> customerController.createCustomer(customerSaveDTO).execute());
            InvoiceSaveDTO invoiceSaveDto = DataUtil.getInvoiceSaveDTO(customerDTO.body(), employeeDTO.body());
            Response<InvoiceDTO> actualDto = step("Выполняем post запрос - invoice controller",
                    () -> invoiceController.createInvoice(invoiceSaveDto).execute());
            Response<InvoiceDTO> expectedDto = step("Выполняем get запрос - invoice controller - get invoice by id",
                    () -> invoiceController.getInvoice(actualDto.body().getId()).execute());
            Response<List<InvoiceDTO>> expectedDtos2 = step("Выполняем get запрос - invoice controller - get invoice by customer id",
                    () -> invoiceController.getInvoiceByCustomerId(customerDTO.body().getId()).execute());
            Response<List<InvoiceDTO>> expectedDtos3 = step("Выполняем get запрос - invoice controller - get invoice by employee id",
                    () -> invoiceController.getInvoiceByEmployeeId(employeeDTO.body().getId()).execute());
            InvoiceDTO actualChangedDto = DataUtil.getInvoiceDTO(actualDto.body());
            Response<InvoiceDTO> expectedChangedDto = step("Выполняем put запрос - invoice controller",
                    () -> invoiceController.updateInvoice(actualChangedDto).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2, expectedDtos3), softly);
            step("Выполняем delete запрос - invoice controller",
                    () -> softly.assertThat(invoiceController.deleteInvoice(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }

        @DisplayName("Тест на retrofit - проверка track-controller")
        @Description("Тест на retrofit - проверка track-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testJTrackController() {
            ArtistSaveDTO artistSaveDTO = DataUtil.getArtistSaveDto();
            ArtistDTO artistDTO = step("Выполняем post запрос - artist controller",
                    () -> artistController.createArtist(artistSaveDTO).execute().body());
            AlbumSaveDto albumSaveDto = DataUtil.getAlbumSaveDto(artistDTO);
            AlbumDTO albumDTO = step("Выполняем post запрос - album controller",
                    () -> albumController.createAlbum(albumSaveDto).execute().body());
            GenreSaveDTO genreSaveDto = DataUtil.getGenreSaveDTO();
            MediaTypeSaveDTO mediaTypeSaveDTO = DataUtil.getMediaTypeSaveDTO();
            GenreDTO genreDTO = step("Выполняем post запрос - genre controller",
                    () -> genreController.createGenre(genreSaveDto).execute().body());
            MediaTypeDTO mediaTypeDTO = step("Выполняем post запрос - mediaType controller",
                    () -> mediaTypeController.createMediaType(mediaTypeSaveDTO).execute().body());
            TrackSaveDTO trackSaveDTO = DataUtil.getTrackSaveDTO(albumDTO, mediaTypeDTO, genreDTO);
            Response<TrackDTO> actualDto = step("Выполняем post запрос - track controller",
                    () -> trackController.createTrack(trackSaveDTO).execute());
            Response<TrackDTO> expectedDto = step("Выполняем get запрос - track controller  - get track by id",
                    () -> trackController.getTrack(actualDto.body().getId()).execute());
            TrackDTO actualChangedDto = DataUtil.getTrackDto(actualDto.body());
            Response<TrackDTO> expectedChangedDto = step("Выполняем put запрос - track controller",
                    () -> trackController.updateTrack(actualChangedDto).execute());
            Response<List<TrackDTO>> expectedDtos2 = step("Выполняем get запрос - track controller - get track by album id",
                    () -> trackController.getTrackByAlumId(albumDTO.getId()).execute());
            Response<List<TrackDTO>> expectedDtos3 = step("Выполняем get запрос - track controller - get track by artist id",
                    () -> trackController.getTrackByArtistId(artistDTO.getId()).execute());
            Response<List<TrackDTO>> expectedDtos4 = step("Выполняем get запрос - track controller - get track by artist pseudonym",
                    () -> trackController.getTrackByArtistPseudonym(artistDTO.getPseudonym()).execute());
            Response<List<TrackDTO>> expectedDtos5 = step("Выполняем get запрос - track controller - get track by genreId",
                    () -> trackController.getTrackByGenreId(genreDTO.getId()).execute());
            Response<List<TrackDTO>> expectedDtos6 = step("Выполняем get запрос - track controller - get track by mediaType id",
                    () -> trackController.getTrackByMediaTypeId(mediaTypeDTO.getId()).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2, expectedDtos3, expectedDtos4, expectedDtos5, expectedDtos6), softly);
            step("Выполняем delete запрос - track controller",
                    () -> softly.assertThat(trackController.deleteTrack(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }

        @DisplayName("Тест на retrofit - проверка invoice-line-controller")
        @Description("Тест на retrofit - проверка invoice-line-controller")
        @Owner("ObvintcevAE")
        @Test
        public void testLInvoiceLineController() {
            ArtistSaveDTO artistSaveDTO = DataUtil.getArtistSaveDto();
            ArtistDTO artistDTO = step("Выполняем post запрос - artist controller",
                    () -> artistController.createArtist(artistSaveDTO).execute().body());
            AlbumSaveDto albumSaveDto = DataUtil.getAlbumSaveDto(artistDTO);
            AlbumDTO albumDTO = step("Выполняем post запрос - album controller",
                    () -> albumController.createAlbum(albumSaveDto).execute().body());
            GenreDTO genreDTO = step("Выполняем get запрос - genre controller - get all genres",
                    () -> genreController.getGenreAll().execute().body().get(0));
            MediaTypeDTO mediaTypeDTO = step("Выполняем get запрос - mediaType controller - get all media types",
                    () -> mediaTypeController.getMediaTypeAll().execute().body().get(0));
            TrackSaveDTO trackSaveDTO = DataUtil.getTrackSaveDTO(albumDTO, mediaTypeDTO, genreDTO);
            EmployeeSaveDTO employeeSaveDTO = DataUtil.getEmployeeSaveDTO();
            CustomerSaveDTO customerSaveDTO = DataUtil.getCustomerSaveDTO();
            EmployeeDTO employeeDTO = step("Выполняем post запрос - employee controller",
                    () -> employeeController.createEmployee(employeeSaveDTO).execute().body());
            CustomerDTO customerDTO = step("Выполняем post запрос - customer controller",
                    () -> customerController.createCustomer(customerSaveDTO).execute().body());
            InvoiceSaveDTO invoiceSaveDto = DataUtil.getInvoiceSaveDTO(customerDTO, employeeDTO);
            InvoiceDTO invoiceDTO = step("Выполняем post запрос - invoice controller",
                    () -> invoiceController.createInvoice(invoiceSaveDto).execute().body());
            TrackDTO trackDTO = step("Выполняем post запрос - track controller",
                    () -> trackController.createTrack(trackSaveDTO).execute().body());

            InvoiceLineSaveDTO invoiceLineSaveDTO = DataUtil.getInvoiceLineSaveDTO(trackDTO, invoiceDTO);
            Response<InvoiceLineDTO> actualDto = step("Выполняем post запрос - invoice line controller",
                    () -> invoiceLineController.createInvoiceLine(invoiceLineSaveDTO).execute());
            Response<InvoiceLineDTO> expectedDto = step("Выполняем get запрос - invoice line controller - get invoice line by id",
                    () -> invoiceLineController.getInvoiceLine(actualDto.body().getId()).execute());
            Response<List<InvoiceLineDTO>> expectedDtos2 = step("Выполняем get запрос - invoice line controller - get invoice line by invoice id",
                    () -> invoiceLineController.getInvoiceLineByInvoice(invoiceDTO.getId()).execute());
            Response<List<InvoiceLineDTO>> expectedDtos3 = step("Выполняем get запрос - invoice line controller - get invoice line by track id",
                    () -> invoiceLineController.getInvoiceLineByTrack(trackDTO.getId()).execute());
            InvoiceLineDTO actualChangedDto = DataUtil.getInvoiceLineDto(actualDto.body());
            Response<InvoiceLineDTO> expectedChangedDto = step("Выполняем put запрос - invoice line controller",
                    () -> invoiceLineController.updateInvoiceLine(actualChangedDto).execute());

            checkPostGetResponse(actualDto, expectedDto, softly);
            checkPutResponse(actualChangedDto, expectedChangedDto, softly);
            checkGetListMethods(actualDto.body(), List.of(expectedDtos2, expectedDtos3), softly);
            step("Выполняем delete запрос - invoice line controller",
                    () -> softly.assertThat(invoiceLineController.deleteInvoiceLine(actualDto.body().getId()).execute().code()).isEqualTo(204));
        }
    }
}
