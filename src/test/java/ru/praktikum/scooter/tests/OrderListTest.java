package ru.praktikum.scooter.tests;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import java.util.*;
import ru.praktikum.scooter.api.OrderClient;
import ru.praktikum.scooter.support.Checks;
import static org.junit.Assert.*;
import static ru.praktikum.scooter.config.ApiConfig.ORDERS;
import static ru.praktikum.scooter.data.Contract.OK;
public class OrderListTest {
    private Response response;
    @Step("Запросить первую страницу списка") public void requestList() { response = new OrderClient().list(); }
    @Step("Проверить структуру списка") public void checkList() {
        Checks.status(response, OK, "GET", ORDERS);
        Object root = response.jsonPath().get(); assertTrue("Корень должен быть объектом", root instanceof Map);
        assertTrue("orders должен быть массивом", ((Map<?,?>) root).get("orders") instanceof List);
    }
    @Test public void shouldReturnOrdersArray() { requestList(); checkList(); }
}
