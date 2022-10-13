package com.rental.car.rentalapp.infrasturcture.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class CarQuery {
    private String startAt;
    private String endAt;
    private String model;
    @Tolerate
    public CarQuery(){}
}
