package ru.praktikum.scooter.tests;
import org.junit.*;
import java.util.*;
import ru.praktikum.scooter.data.TestData;
import ru.praktikum.scooter.support.CourierFixture;
import static ru.praktikum.scooter.data.Contract.*;
public class CourierLoginTest extends CourierFixture {
    @Before public void setUp() { prepareData(); }
    @After public void tearDown() { cleanup(); }
    @Test public void shouldLoginWithValidCredentials() { createFixture(); body = TestData.credentials(courier); sendLogin(); checkLogin(); }
    @Test public void shouldRejectLoginWithoutLogin() { createFixture(); body = TestData.credentials(courier); body.remove("login"); sendLogin(); checkMissing(); }
    @Test public void shouldRejectLoginWithoutPassword() { createFixture(); body = TestData.credentials(courier); body.remove("password"); sendLogin(); checkMissing(); }
    @Test public void shouldRejectUnknownLogin() { createFixture(); body = TestData.credentials(courier); body.put("login", "qa" + TestData.unique()); sendLogin(); checkNotFound(); }
    @Test public void shouldRejectWrongPassword() { createFixture(); body = TestData.credentials(courier); body.put("password", courier.get("password") + "x"); sendLogin(); checkNotFound(); }
    @Test public void shouldRejectUnregisteredCredentials() { body = TestData.credentials(courier); sendLogin(); checkNotFound(); }
    private void checkMissing() { expectedStatus = BAD_REQUEST; expectedMessage = COURIER_LOGIN_MISSING_FIELDS; checkError(); }
    private void checkNotFound() { expectedStatus = NOT_FOUND; expectedMessage = COURIER_NOT_FOUND; checkError(); }
}
