package ru.praktikum.scooter.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {
    public String firstName = "Test";
    public String lastName = "Testov";
    public String address = "Test street 1";
    public String metroStation = "1";
    public String phone = "+79990000000";
    public int rentTime = 1;
    public String deliveryDate;
    public String comment;
    public List<String> color;
}
