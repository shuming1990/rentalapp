package com.rental.car.rentalapp.service.rent.service;

import com.rental.car.rentalapp.infrasturcture.dto.ResponseEntity;
import com.rental.car.rentalapp.infrasturcture.exception.AppException;
import com.rental.car.rentalapp.infrasturcture.exception.ResultCode;
import com.rental.car.rentalapp.service.rent.model.Car;
import com.rental.car.rentalapp.service.rent.model.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class RentServiceTest {
    @Resource
    private RentService rentService;

    @Test
    void should_find_all_cars() {
        ResponseEntity<List<Car>> responseEntity = rentService.findAvailableCars("", "2099-01-01 20:00:00", "2099-12-02 10:00:00");
        assertEquals(ResultCode.SUCCESS.getCode(),responseEntity.getResponseCode());
        assertEquals(4, responseEntity.getResponseObject().size());
    }

    @Test
    @Transactional
    @Rollback
    void should_find_available_cars(){
        Reservation reservation = Reservation.builder()
                .startAt(LocalDateTime.of(2099, 1, 1,0,0,0))
                .predictedEndAt(LocalDateTime.of(2099, 1, 2,0,0,0))
                .actualEndAt(LocalDateTime.of(2099, 1, 2,0,0,0))
                .status(1)
                .mobile("18600000000")
                .email("xxx_xxx@126.com")
                .car(Car.builder().carNumber("CAR001").build())
                .firstName("John")
                .lastName("Doe").build();
        rentService.create(reservation);

        ResponseEntity<List<Car>> responseEntity = rentService.findAvailableCars("", "2099-01-01 00:00:00", "2099-01-01 10:00:00");
        assertEquals(ResultCode.SUCCESS.getCode(),responseEntity.getResponseCode());
        assertEquals(3, responseEntity.getResponseObject().size());
    }

    @Test
    @Transactional
    @Rollback
    void should_create_successfully() {
        Reservation reservation = Reservation.builder()
                .startAt(LocalDateTime.of(2099, 1, 1,0,0,0))
                .predictedEndAt(LocalDateTime.of(2099, 1, 2,0,0,0))
                .actualEndAt(LocalDateTime.of(2099, 1, 2,0,0,0))
                .status(1)
                .mobile("18600000000")
                .email("xxx_xxx@126.com")
                .car(Car.builder().carNumber("CAR001").build())
                .firstName("John")
                .lastName("Doe").build();

        ResponseEntity responseEntity = rentService.create(reservation);
        assertEquals(ResultCode.SUCCESS.getCode(), responseEntity.getResponseCode());
        assertNotNull(responseEntity.getResponseObject());
    }

    @Test
    @Transactional
    @Rollback
    void failure_on_overlapping_timeslot_save(){
        Reservation reservation = Reservation.builder()
                .startAt(LocalDateTime.of(2099, 1, 1,0,0,0))
                .predictedEndAt(LocalDateTime.of(2099, 1, 2,0,0,0))
                .actualEndAt(LocalDateTime.of(2099, 1, 2,0,0,0))
                .status(1)
                .mobile("18600000000")
                .email("xxx_xxx@126.com")
                .car(Car.builder().carNumber("CAR001").build())
                .firstName("John")
                .lastName("Doe").build();
        ResponseEntity responseEntity = rentService.create(reservation);
        assertEquals(ResultCode.SUCCESS.getCode(), responseEntity.getResponseCode());

        Reservation newReservation = Reservation.builder()
                .startAt(LocalDateTime.of(2099, 1, 1,0,0,0))
                .predictedEndAt(LocalDateTime.of(2099, 1, 3,0,0,0))
                .actualEndAt(LocalDateTime.of(2099, 1, 3,0,0,0))
                .status(1)
                .mobile("18600000000")
                .email("xxx_xxx@126.com")
                .car(Car.builder().carNumber("CAR001").build())
                .firstName("John")
                .lastName("Doe").build();
        assertThrows(AppException.class, ()-> rentService.create(newReservation));
    }

    @Test
    @Transactional
    @Rollback
    void findReservationsByPhone() {
        Reservation reservation = Reservation.builder()
                .startAt(LocalDateTime.of(2099, 1, 1,0,0,0))
                .predictedEndAt(LocalDateTime.of(2099, 1, 2,0,0,0))
                .actualEndAt(LocalDateTime.of(2099, 1, 2,0,0,0))
                .status(1)
                .mobile("9898989898")
                .email("xxx_xxx@126.com")
                .car(Car.builder().carNumber("CAR001").build())
                .firstName("John")
                .lastName("Doe").build();
        rentService.create(reservation);

        ResponseEntity<List<Reservation>> r = rentService.findReservationsByPhone("9898989898",null);
        assertEquals(ResultCode.SUCCESS.getCode(), r.getResponseCode());
        assertEquals(1, r.getResponseObject().size());
    }
}