package ru.praktikum.scooter.tests;
import org.junit.*;
import java.util.*;
import ru.praktikum.scooter.support.CourierFixture;
import static ru.praktikum.scooter.data.Contract.*;
public class CourierCreateTest extends CourierFixture {
    @Before public void setUp() { prepareData(); }
    @After public void tearDown() { cleanup(); }
    @Test public void shouldCreateCourierWithRequiredFields() { body = new LinkedHashMap<>(courier); sendCreate(); checkCreated(); }
    @Test public void shouldRejectDuplicateCourier() { createFixture(); body = new LinkedHashMap<>(courier); sendCreate(); expectedStatus = CONFLICT; expectedMessage = COURIER_LOGIN_TAKEN; checkError(); }
    @Test public void shouldRejectDuplicateLoginWithOtherData() { createFixture(); body = new LinkedHashMap<>(courier); body.put("password", courier.get("password") + "x"); body.put("firstName", "Other"); sendCreate(); expectedStatus = CONFLICT; expectedMessage = COURIER_LOGIN_TAKEN; checkError(); }
    @Test public void shouldRejectCourierCreationWithoutLogin() { missing("login"); }
    @Test public void shouldRejectCourierCreationWithoutPassword() { missing("password"); }
    @Test public void shouldRejectCourierCreationWithoutFirstName() { missing("firstName"); }
    private void missing(String key) { body = new LinkedHashMap<>(courier); body.remove(key); sendCreate(); expectedStatus = BAD_REQUEST; expectedMessage = COURIER_CREATE_MISSING_FIELDS; checkError(); }
}
