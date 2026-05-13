package digital.store.api.retrofit.util;

import digital.store.api.retrofit.model.SomeObjectWithId;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import retrofit2.Response;

import java.util.List;

public interface ICheckResponse {

    private String notEqualCode(String methodName) {
        return "Неправильный статус-код для " + methodName + " метода!";
    }

    @Step("Выполняем проверки после post и get запросов")
    default <D> void checkPostGetResponse(Response<D> postResponse, Response<D> getResponse, SoftAssertions softly) {
        softly.assertThat(getResponse.body()).withFailMessage("Полученный через get метод по id объект не соответствует ранее созданному объекту!")
                .isEqualTo(postResponse.body());
        softly.assertThat(getResponse.code()).withFailMessage(notEqualCode("get")).isEqualTo(200);
        softly.assertThat(postResponse.code()).withFailMessage(notEqualCode("post")).isEqualTo(201);
    }

    @Step("Выполняем проверки после put запроса")
    default <D> void checkPutResponse(D preparedForPutRequestBody, Response<D> putResponse, SoftAssertions softly) {
        softly.assertThat(preparedForPutRequestBody).withFailMessage("Dto после использования put-метода не соответствует ранее переданой для этого метода dto!")
                .isEqualTo(putResponse.body());
        softly.assertThat(putResponse.code()).withFailMessage(notEqualCode("put")).isEqualTo(200);
    }

    @Step("Выполняем проверки для get запроса, возвращающего коллекцию объектов")
    default <D extends SomeObjectWithId> void checkGetListMethods(D actualObject, List<Response<List<D>>> getListMethodsResponse, SoftAssertions softly) {
        getListMethodsResponse.forEach(response -> {
            softly.assertThat(response.body().stream().anyMatch(object -> object.getId().equals(actualObject.getId())))
                    .withFailMessage("Сущность не найдена после создания в методах, возвращающих списки объектов!")
                    .isEqualTo(true);
            softly.assertThat(response.code()).withFailMessage(notEqualCode("get")).isEqualTo(200);
        });
    }

}
