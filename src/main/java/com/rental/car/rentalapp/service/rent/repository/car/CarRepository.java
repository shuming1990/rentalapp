package com.rental.car.rentalapp.service.rent.repository.car;

import java.time.LocalDateTime;
import java.util.List;

import com.rental.car.rentalapp.service.rent.model.Car;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

public interface CarRepository {
    public List<Car> findAvailableCarsBy(@Param("startAt") LocalDateTime startAt, @Param("endAt")LocalDateTime endAt);
}
