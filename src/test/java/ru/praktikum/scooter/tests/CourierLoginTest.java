package ru.praktikum.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import java.util.*;
import ru.praktikum.scooter.data.TestData;
import ru.praktikum.scooter.support.CourierFixture;
import static ru.praktikum.scooter.data.Contract.*;

public class CourierLoginTest extends CourierFixture {

    @Rule
    public org.junit.rules.TestName testName = new org.junit.rules.TestName();

    @Before
    public void setUp() {
        prepareData();
        if (!testName.getMethodName().equals("shouldRejectUnregisteredCredentials")) createFixture();
    }

    @After
    public void tearDown() {
        cleanup();
    }

    @Test
    @DisplayName("shouldLoginWithValidCredentials")
    @Description("should Login With Valid Credentials")
    public void shouldLoginWithValidCredentials() {
        body = TestData.credentials(courier);
        sendLogin();
        checkLogin();
    }

    @Test
    @DisplayName("shouldRejectLoginWithoutLogin")
    @Description("should Reject Login Without Login")
    public void shouldRejectLoginWithoutLogin() {
        body = TestData.credentials(courier);
        body.login = null;
        sendLogin();
        checkMissing();
    }

    @Test
    @DisplayName("shouldRejectLoginWithoutPassword")
    @Description("should Reject Login Without Password")
    public void shouldRejectLoginWithoutPassword() {
        body = TestData.credentials(courier);
        body.password = null;
        sendLogin();
        checkMissing();
    }

    @Test
    @DisplayName("shouldRejectUnknownLogin")
    @Description("should Reject Unknown Login")
    public void shouldRejectUnknownLogin() {
        body = TestData.credentials(courier);
        body.login = "qa" + TestData.unique();
        sendLogin();
        checkNotFound();
    }

    @Test
    @DisplayName("shouldRejectWrongPassword")
    @Description("should Reject Wrong Password")
    public void shouldRejectWrongPassword() {
        body = TestData.credentials(courier);
        body.password = courier.password + "x";
        sendLogin();
        checkNotFound();
    }

    @Test
    @DisplayName("shouldRejectUnregisteredCredentials")
    @Description("should Reject Unregistered Credentials")
    public void shouldRejectUnregisteredCredentials() {
        body = TestData.credentials(courier);
        sendLogin();
        checkNotFound();
    }

    private void checkMissing() {
        expectedStatus = BAD_REQUEST;
        expectedMessage = COURIER_LOGIN_MISSING_FIELDS;
        checkError();
    }

    private void checkNotFound() {
        expectedStatus = NOT_FOUND;
        expectedMessage = COURIER_NOT_FOUND;
        checkError();
    }
}
