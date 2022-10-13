package com.rental.car.rentalapp.service.rent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.car.rentalapp.service.rent.repository.car.CarRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car implements Serializable {
    @JsonIgnore
    private long carId;
    private String model;
    private String carNumber;
    @JsonIgnore
    private transient CarRepository carRepository;

    @Tolerate
    public Car(){}
    public List<Car> findAvailableBy(LocalDateTime startAt, LocalDateTime endAt){
        return carRepository.findAvailableCarsBy(startAt, endAt);
    }
}
