package ru.praktikum.scooter.api;

import io.restassured.response.Response;
import ru.praktikum.scooter.data.OrderRequest;
import static ru.praktikum.scooter.config.ApiConfig.*;

public class OrderClient {

    public static class CancelRequest {
        public final long track;
        public CancelRequest(long track) { this.track = track; }
    }

    public Response create(OrderRequest body) {
        return Requests.json().body(body).post(ORDERS);
    }

    public Response cancel(long track) {
        return Requests.json().body(new CancelRequest(track)).put(CANCEL);
    }

    public Response cancelWithQuery(long track) {
        return Requests.json().queryParam("track", track).body(new CancelRequest(track)).put(CANCEL);
    }

    public Response list() {
        return Requests.json().queryParam("limit", 10).queryParam("page", 0).get(ORDERS);
    }
}
