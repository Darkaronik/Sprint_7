package ru.praktikum.scooter.api;
import io.restassured.response.Response;
import java.util.Map;
import static ru.praktikum.scooter.config.ApiConfig.*;
public class CourierClient {
    public Response create(Map<String,Object> body) { return Requests.json().body(body).post(COURIER); }
    public Response login(Map<String,Object> body) { return Requests.json().body(body).post(LOGIN); }
    public Response delete(long id) { return Requests.json().delete(COURIER + "/{id}", id); }
}
