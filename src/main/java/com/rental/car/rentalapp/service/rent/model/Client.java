package com.rental.car.rentalapp.service.rent.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.car.rentalapp.infrasturcture.exception.AppException;
import com.rental.car.rentalapp.infrasturcture.exception.ResultCode;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client implements Serializable {
    private Reservation reservation;
    private final static ZoneOffset CHINA_ZONE_OFFSET = ZoneOffset.of("+08:00");

    public List<Car> query(Car car, LocalDateTime startAt, LocalDateTime endAt){
        return car.findAvailableBy(startAt, endAt);
    }

    public Reservation reserve() {
        try {
            if (reservation.create() > 0)
                return reservation;
            else
                throw new AppException(ResultCode.NOT_SAVE, "Order not placed. Please check the available time slot again.");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new AppException(ResultCode.NOT_SAVE, "Order not placed. Please check the available time slot again.");
        }
    }

    public List<Reservation> findReservationsWith(String mobile){
        return reservation.findBy(mobile);
    }

    public Reservation cancel(String transactionId){

        return reservation;
    }

    public Reservation returnCar(Car car){
        Reservation reservation = Reservation.builder().build();

        return reservation;
    }
}
