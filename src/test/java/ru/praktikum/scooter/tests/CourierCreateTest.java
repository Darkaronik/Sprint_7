package ru.praktikum.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import java.util.*;
import ru.praktikum.scooter.support.CourierFixture;
import static ru.praktikum.scooter.data.Contract.*;

public class CourierCreateTest extends CourierFixture {

    @Rule
    public org.junit.rules.TestName testName = new org.junit.rules.TestName();

    @Before
    public void setUp() {
        prepareData();
        if (testName.getMethodName().startsWith("shouldRejectDuplicate")) createFixture();
    }

    @After
    public void tearDown() {
        cleanup();
    }

    @Test
    @DisplayName("shouldCreateCourierWithRequiredFields")
    @Description("should Create Courier With Required Fields")
    public void shouldCreateCourierWithRequiredFields() {
        body = courier.copy();
        sendCreate();
        checkCreated();
    }

    @Test
    @DisplayName("shouldRejectDuplicateCourier")
    @Description("should Reject Duplicate Courier")
    public void shouldRejectDuplicateCourier() {
        body = courier.copy();
        sendCreate();
        expectedStatus = CONFLICT;
        expectedMessage = COURIER_LOGIN_TAKEN;
        checkError();
    }

    @Test
    @DisplayName("shouldRejectDuplicateLoginWithOtherData")
    @Description("should Reject Duplicate Login With Other Data")
    public void shouldRejectDuplicateLoginWithOtherData() {
        body = courier.copy();
        body.password = courier.password + "x";
        body.firstName = "Other";
        sendCreate();
        expectedStatus = CONFLICT;
        expectedMessage = COURIER_LOGIN_TAKEN;
        checkError();
    }

    @Test
    @DisplayName("shouldRejectCourierCreationWithoutLogin")
    @Description("should Reject Courier Creation Without Login")
    public void shouldRejectCourierCreationWithoutLogin() {
        missing("login");
    }

    @Test
    @DisplayName("shouldRejectCourierCreationWithoutPassword")
    @Description("should Reject Courier Creation Without Password")
    public void shouldRejectCourierCreationWithoutPassword() {
        missing("password");
    }

    @Test
    @DisplayName("shouldRejectCourierCreationWithoutFirstName")
    @Description("should Reject Courier Creation Without First Name")
    public void shouldRejectCourierCreationWithoutFirstName() {
        missing("firstName");
    }

    private void missing(String key) {
        body = courier.copy();
        if (key.equals("login")) body.login = null;
        else if (key.equals("password")) body.password = null;
        else body.firstName = null;
        sendCreate();
        expectedStatus = BAD_REQUEST;
        expectedMessage = COURIER_CREATE_MISSING_FIELDS;
        checkError();
    }
}
