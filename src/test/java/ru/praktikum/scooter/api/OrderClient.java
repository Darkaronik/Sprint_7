package ru.praktikum.scooter.api;
import io.restassured.response.Response;
import java.util.*;
import static ru.praktikum.scooter.config.ApiConfig.*;
public class OrderClient {
    public Response create(Map<String,Object> body) { return Requests.json().body(body).post(ORDERS); }
    public Response cancel(long track) { return Requests.json().body(Collections.singletonMap("track", track)).put(CANCEL); }
    public Response cancelWithQuery(long track) { return Requests.json().queryParam("track", track).body(Collections.singletonMap("track", track)).put(CANCEL); }
    public Response list() { return Requests.json().queryParam("limit", 10).queryParam("page", 0).get(ORDERS); }
}
