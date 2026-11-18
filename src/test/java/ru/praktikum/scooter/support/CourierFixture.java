package ru.praktikum.scooter.support;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.*;
import ru.praktikum.scooter.api.CourierClient;
import ru.praktikum.scooter.data.TestData;
import ru.praktikum.scooter.data.CourierRequest;
import static org.junit.Assert.*;
import static ru.praktikum.scooter.config.ApiConfig.*;
import static ru.praktikum.scooter.data.Contract.*;

public class CourierFixture {

    protected final CourierClient client = new CourierClient();

    protected final List<CourierRequest> attempts = new ArrayList<>();

    protected final Set<Long> ids = new LinkedHashSet<>();

    protected CourierRequest courier, body;

    protected Response response;

    protected int expectedStatus;

    protected String expectedMessage;

    protected boolean unidentifiable;

    protected String requestPath;

    @Step("Подготовить уникальные данные курьера")
    public void prepareData() {
        courier = TestData.courier();
    }

    @Step("Создать тестового курьера и получить его ID")
    public void createFixture() {
        body = courier.copy();
        sendCreate();
        Checks.status(response, CREATED, "POST", COURIER);
        Checks.ok(response);
        body = TestData.credentials(courier);
        sendLogin();
        Checks.status(response, OK, "POST", LOGIN);
        ids.add(Checks.requirePositiveInteger(response, "id"));
    }

    @Step("Отправить запрос создания курьера")
    public void sendCreate() {
        requestPath = COURIER;
        if (body.login != null && body.password != null) attempts.add(TestData.credentials(body));
        else unidentifiable = true;
        try {
            response = client.create(body);
        } catch (RuntimeException e) {
            if (unidentifiable) System.err.println("Не удалось подтвердить очистку попытки без обязательного поля: " + courier.login);
            throw e;
        }
        if (unidentifiable && response.statusCode() != CREATED) unidentifiable = false;
        if (response.statusCode() == CREATED && body.firstName == null && !unidentifiable) Checks.ok(response);
    }

    @Step("Отправить запрос авторизации курьера")
    public void sendLogin() {
        requestPath = LOGIN;
        response = client.login(body);
    }

    @Step("Проверить код и сообщение ответа")
    public void checkError() {
        Checks.status(response, expectedStatus, "POST", requestPath);
        Checks.message(response, expectedMessage);
    }

    @Step("Проверить успешное создание")
    public void checkCreated() {
        Checks.status(response, CREATED, "POST", COURIER);
        Checks.ok(response);
    }

    @Step("Проверить успешный вход")
    public void checkLogin() {
        Checks.status(response, OK, "POST", LOGIN);
        ids.add(Checks.requirePositiveInteger(response, "id"));
    }

    @Step("Очистить собственных курьеров")
    public void cleanup() {
        List<String> errors = new ArrayList<>();
        if (unidentifiable) errors.add("Невозможно подтвердить очистку создания без обязательного поля, логин: " + courier.login);
        for (CourierRequest attempt : attempts) {
            try {
                Response login = client.login(attempt);
                if (login.statusCode() == OK) {
                    Long id = Checks.positiveInteger(login.jsonPath().get("id"));
                    if (id != null) ids.add(id);
                    else errors.add("В ответе входа нет корректного ID для " + attempt.login);
                } else if (login.statusCode() != NOT_FOUND) errors.add("Поиск курьера " + attempt.login + ": HTTP " + login.statusCode());
            } catch (Exception e) {
                errors.add("Поиск курьера " + attempt.login + ": " + e.getClass().getSimpleName());
            }
        }
        for (long id : ids) {
            try {
                Response deleted = client.delete(id);
                Checks.status(deleted, OK, "DELETE", COURIER + "/{id}");
                Checks.ok(deleted);
            } catch (Throwable e) {
                errors.add("Удаление ID " + id + ": " + e.getMessage());
            }
        }
        assertTrue(String.join("; ", errors), errors.isEmpty());
    }
}
