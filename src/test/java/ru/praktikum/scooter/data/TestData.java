package ru.praktikum.scooter.data;
import java.time.LocalDate;
import java.util.*;
public final class TestData {
    private TestData() { }
    public static String unique() { return UUID.randomUUID().toString().replace("-", "").substring(0, 16); }
    public static Map<String,Object> courier() {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("login", "qa" + unique()); body.put("password", "pw" + unique()); body.put("firstName", "Test"); return body;
    }
    public static Map<String,Object> credentials(Map<String,Object> courier) {
        Map<String,Object> body = new LinkedHashMap<>(); body.put("login", courier.get("login")); body.put("password", courier.get("password")); return body;
    }
    public static Map<String,Object> order(List<String> colors, String marker) {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("firstName", "Test"); body.put("lastName", "Testov"); body.put("address", "Test street 1"); body.put("metroStation", "1");
        body.put("phone", "+79990000000"); body.put("rentTime", 1); body.put("deliveryDate", LocalDate.now().plusDays(7).toString()); body.put("comment", marker);
        if (colors != null) body.put("color", colors); return body;
    }
}
