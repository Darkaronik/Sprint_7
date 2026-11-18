package ru.praktikum.scooter.api;

import io.restassured.response.Response;
import ru.praktikum.scooter.data.CourierRequest;
import static ru.praktikum.scooter.config.ApiConfig.*;

public class CourierClient {

    public Response create(CourierRequest body) {
        return Requests.json().body(body).post(COURIER);
    }

    public Response login(CourierRequest body) {
        return Requests.json().body(body).post(LOGIN);
    }

    public Response delete(long id) {
        return Requests.json().delete(COURIER + "/{id}", id);
    }
}
