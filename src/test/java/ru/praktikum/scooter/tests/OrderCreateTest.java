package ru.praktikum.scooter.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;
import ru.praktikum.scooter.api.OrderClient;
import ru.praktikum.scooter.data.TestData;
import ru.praktikum.scooter.support.Checks;
import static org.junit.Assert.*;
import static ru.praktikum.scooter.config.ApiConfig.*;
import static ru.praktikum.scooter.data.Contract.*;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    @Parameterized.Parameters(name = "{index} — {0}")
    public static Collection<Object[]> colors() {
        return Arrays.asList(new Object[][] {
            {
                "BLACK", Collections.singletonList("BLACK")
            }, {
                "GREY", Collections.singletonList("GREY")
            }, {
                "BLACK и GREY", Arrays.asList("BLACK", "GREY")
            }, {
                "без color", null
            }
        });
    }

    private final List<String> colors;

    private final OrderClient client = new OrderClient();

    private String marker;

    private Map<String, Object> body;

    private Response response;

    private Long track;

    private boolean attempted;

    public OrderCreateTest(String name, List<String> colors) {
        this.colors = colors;
    }

    @Before
    @Step("Подготовить уникальный заказ")
    public void setUp() {
        marker = "qa-" + TestData.unique();
        body = TestData.order(colors, marker);
    }

    @Step("Отправить заказ")
    public void sendOrder() {
        attempted = true;
        response = client.create(body);
        try {
            track = Checks.positiveInteger(response.jsonPath().get("track"));
        } catch (Exception ignored) {
        }
    }

    @Step("Проверить создание и трек")
    public void checkOrder() {
        Checks.status(response, CREATED, "POST", ORDERS);
        Checks.requirePositiveInteger(response, "track");
    }

    @Test
    public void shouldCreateOrderForColorVariant() {
        sendOrder();
        checkOrder();
    }

    @After
    @Step("Отменить созданный заказ")
    public void tearDown() {
        if (attempted && track == null) fail("Очистка не подтверждена: трек неизвестен, метка " + marker);
        if (track != null) {
            Response cancelled = client.cancel(track);
            if (cancelled.statusCode() == BAD_REQUEST) {
                System.err.println("Отмена с JSON вернула 400 для track=" + track + "; повтор с параметром запроса");
                cancelled = client.cancelWithQuery(track);
            }
            try {
                Checks.status(cancelled, OK, "PUT", CANCEL);
                Checks.ok(cancelled);
            } catch (AssertionError e) {
                throw new AssertionError("Не подтверждена отмена заказа: track=" + track + ", метка=" + marker + "; " + e.getMessage(), e);
            }
        }
    }
}
