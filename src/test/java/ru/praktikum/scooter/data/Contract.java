package ru.praktikum.scooter.data;

public final class Contract {

    public static final int OK = 200, CREATED = 201, BAD_REQUEST = 400, NOT_FOUND = 404, CONFLICT = 409;

    public static final String COURIER_CREATE_MISSING_FIELDS = "Недостаточно данных для создания учетной записи";

    public static final String COURIER_LOGIN_TAKEN = "Этот логин уже используется";

    public static final String COURIER_LOGIN_MISSING_FIELDS = "Недостаточно данных для входа";

    public static final String COURIER_NOT_FOUND = "Учетная запись не найдена";

    private Contract() {
    }
}
