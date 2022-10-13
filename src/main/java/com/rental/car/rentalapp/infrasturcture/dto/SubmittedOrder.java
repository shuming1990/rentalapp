package com.rental.car.rentalapp.infrasturcture.dto;

import com.rental.car.rentalapp.service.rent.model.Car;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SubmittedOrder {
    private String transactionId;
    private String startAt;
    private String predictedEndAt;
    private String actualEndAt;
    private Car car;
    // user info
    private String mobile;
    private String email;
    private String firstName;
    private String lastName;
}
