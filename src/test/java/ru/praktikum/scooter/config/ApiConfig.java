package ru.praktikum.scooter.config;

public final class ApiConfig {

    public static final String BASE_URL = System.getProperty("scooter.baseUrl", "https://qa-scooter.praktikum-services.ru");

    public static final String COURIER = "/api/v1/courier";

    public static final String LOGIN = COURIER + "/login";

    public static final String ORDERS = "/api/v1/orders";

    public static final String CANCEL = ORDERS + "/cancel";

    public static final int CONNECT_TIMEOUT = Integer.getInteger("scooter.connectTimeout", 5000);

    public static final int READ_TIMEOUT = Integer.getInteger("scooter.readTimeout", 15000);

}
