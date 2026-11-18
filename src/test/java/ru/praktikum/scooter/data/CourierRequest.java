package ru.praktikum.scooter.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourierRequest {
    public String login;
    public String password;
    public String firstName;

    public CourierRequest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierRequest copy() {
        return new CourierRequest(login, password, firstName);
    }
}
