package digital.store.api.utils;

import digital.store.api.model.SomeObjectWithId;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

public interface ICheckResponse {

    private String notEqualCode(String methodName) {
        return "Неправильный статус-код для " + methodName + " метода!";
    }

    @Step("Выполняем проверки после post и get запросов")
    default <D> void checkPostGetResponse(ResponseDto<D> postResponse, ResponseDto<D> getResponse, SoftAssertions softly) {
        softly.assertThat(getResponse.getBody()).withFailMessage("Полученный через get метод по id объект не соответствует ранее созданному объекту!")
                .isEqualTo(postResponse.getBody());
        softly.assertThat(getResponse.getStatus()).withFailMessage(notEqualCode("get")).isEqualTo(200);
        softly.assertThat(postResponse.getStatus()).withFailMessage(notEqualCode("post")).isEqualTo(201);
    }

    @Step("Выполняем проверки после put запроса")
    default <D> void checkPutResponse(D preparedForPutRequestBody, ResponseDto<D> putResponse, SoftAssertions softly) {
        softly.assertThat(preparedForPutRequestBody).withFailMessage("Dto после использования put-метода не соответствует ранее переданой для этого метода dto!")
                .isEqualTo(putResponse.getBody());
        softly.assertThat(putResponse.getStatus()).withFailMessage(notEqualCode("put")).isEqualTo(200);
    }

    @Step("Выполняем проверки для get запроса, возвращающего коллекцию объектов")
    default <D extends SomeObjectWithId> void checkGetListMethods(D actualObject, List<ResponseDto<List<D>>> getListMethodsResponse, SoftAssertions softly) {
        getListMethodsResponse.forEach(response -> {
            softly.assertThat(response.getBody().stream().anyMatch(object -> object.getId().equals(actualObject.getId())))
                    .withFailMessage("Сущность не найдена после создания в методах, возвращающих списки объектов!")
                    .isEqualTo(true);
            softly.assertThat(response.getStatus()).withFailMessage(notEqualCode("get")).isEqualTo(200);
        });
    }

}
