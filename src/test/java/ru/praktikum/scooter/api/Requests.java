package ru.praktikum.scooter.api;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.scooter.config.ApiConfig;
public final class Requests {
    private Requests() { }
    public static RequestSpecification json() {
        return RestAssured.given().baseUri(ApiConfig.BASE_URL).accept(ContentType.JSON).contentType(ContentType.JSON)
            .config(RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", ApiConfig.CONNECT_TIMEOUT).setParam("http.socket.timeout", ApiConfig.READ_TIMEOUT)));
    }
}
