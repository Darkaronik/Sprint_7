package ru.praktikum.scooter.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public final class OrderRequest {
    @JsonProperty("firstName")
    private final String firstName;

    @JsonProperty("lastName")
    private final String lastName;

    @JsonProperty("address")
    private final String address;

    @JsonProperty("metroStation")
    private final String metroStation;

    @JsonProperty("phone")
    private final String phone;

    @JsonProperty("rentTime")
    private final int rentTime;

    @JsonProperty("deliveryDate")
    private final String deliveryDate;

    @JsonProperty("comment")
    private final String comment;

    @JsonProperty("color")
    private final List<String> color;

    public OrderRequest(String firstName, String lastName, String address, String metroStation,
                        String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
}
