package ru.praktikum.scooter.data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public final class TestData {
    private TestData() { }

    public static String unique() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    public static CourierRequest courier() {
        return new CourierRequest("qa" + unique(), "pw" + unique(), "Test");
    }

    public static CourierRequest credentials(CourierRequest courier) {
        return new CourierRequest(courier.login, courier.password, null);
    }

    public static OrderRequest order(List<String> colors, String marker) {
        return new OrderRequest("Test", "Testov", "Test street 1", "1", "+79990000000", 1,
                LocalDate.now().plusDays(7).toString(), marker, colors);
    }
}
