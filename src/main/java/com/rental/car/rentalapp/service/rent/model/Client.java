package com.rental.car.rentalapp.service.rent.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.car.rentalapp.infrasturcture.exception.AppException;
import com.rental.car.rentalapp.infrasturcture.exception.ResultCode;
import com.rental.car.rentalapp.service.rent.repository.car.CarRepository;
import com.rental.car.rentalapp.service.rent.repository.order.OrderRepository;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client implements Serializable {
    private Order order;
    private final static ZoneOffset CHINA_ZONE_OFFSET = ZoneOffset.of("+08:00");

    public List<Car> query(Car car, LocalDateTime startAt, LocalDateTime endAt){
        return car.findAvailableBy(startAt, endAt);
    }

    public Order reserve() {
        try {
            if (order.create() > 0)
                return order;
            else
                throw new AppException(ResultCode.NOT_SAVE, "Order not placed. Please check the available time slot again.");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new AppException(ResultCode.NOT_SAVE, "Order not placed. Please check the available time slot again.");
        }
    }

    public List<Order> findOrderWith(String mobile){
        return order.findBy(mobile);
    }

    public Order cancel(String transactionId){

        return order; 
    }

    public Order returnCar(Car car){
        Order order = Order.builder().build();

        return order;
    }
}
