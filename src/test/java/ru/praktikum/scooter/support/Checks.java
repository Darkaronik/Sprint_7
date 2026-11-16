package ru.praktikum.scooter.support;
import io.restassured.response.Response;
import java.util.*;
import static org.junit.Assert.*;
public final class Checks {
    private Checks() { }
    public static void status(Response response, int expected, String method, String path) {
        assertEquals(method + " " + path + ": HTTP " + response.statusCode() + "; message=" + safeMessage(response), expected, response.statusCode());
    }
    private static String safeMessage(Response response) {
        try { Object value = response.jsonPath().get("message"); return value instanceof String ? ((String) value).substring(0, Math.min(200, ((String) value).length())) : "<нет>"; }
        catch (Exception e) { return "<не JSON>"; }
    }
    public static void ok(Response response) { assertEquals(Boolean.TRUE, response.jsonPath().get("ok")); }
    public static void message(Response response, String expected) { assertEquals(expected, response.jsonPath().get("message")); }
    public static Long positiveInteger(Object value) {
        if (!(value instanceof Integer || value instanceof Long || value instanceof java.math.BigInteger)) return null;
        long number = ((Number)value).longValue(); return number > 0 ? number : null;
    }
    public static long requirePositiveInteger(Response response, String field) {
        Long value = positiveInteger(response.jsonPath().get(field)); assertNotNull(field + " должен быть положительным целым JSON-числом", value); return value;
    }
}
