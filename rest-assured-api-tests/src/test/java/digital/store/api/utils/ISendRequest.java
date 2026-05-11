package digital.store.api.utils;

import digital.store.api.configs.Config;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public interface ISendRequest {

    @Step("Выполняем get запрос по адресу: {path}, получаем {clazz}")
    default <D> ResponseDto<D> get(String path, Class<D> clazz) {
        Response response = given().when().get(path);
        return new ResponseDto<>(response.getStatusCode(), response.then().extract().body().as(clazz));
    }

    @Step("Выполняем delete запрос по адресу: {path}")
    default Response delete(String path) {
        return given().when().delete(path);
    }

    @Step("Выполняем get запрос по адресу: {path} и получаем список {clazz}")
    default <D> ResponseDto<List<D>> getList(String path, Class<D> clazz) {
        Response response = given().when().get(path);
        return new ResponseDto<>(response.getStatusCode(), response.then().extract().body().jsonPath().getList("", clazz));
    }

    @Step("Выполняем put запрос по адресу: {path} и получаем обновленный {clazz}")
    default <D> ResponseDto<D> put(String path, D body, Class<D> clazz) {
        Response response = given().body(body).when().put(path);
        return new ResponseDto<>(response.getStatusCode(), response.then().extract().body().as(clazz));
    }

    @Step("Выполняем post запрос по адресу: {path} и создаем {clazz}")
    default <S, D> ResponseDto<D> post(String path, S body, Class<D> clazz) {
        Response response = given().body(body).when().post(path);
        return new ResponseDto<>(response.getStatusCode(), response.then().extract().body().as(clazz));
    }
}
