package com.rental.car.rentalapp.service.rent.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rental.car.rentalapp.infrasturcture.dto.SubmittedReservation;
import com.rental.car.rentalapp.service.rent.repository.reservation.ReservationRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Builder
public class Reservation implements Serializable {
    @JsonIgnore
    private long orderId;
    private String transactionId;
    private LocalDateTime startAt;
    private LocalDateTime predictedEndAt;
    private LocalDateTime actualEndAt;
    private int status;
    private Car car;

    // user info
    private String mobile;
    private String email;
    private String firstName;
    private String lastName;

    @Tolerate
    public Reservation(){}
    @Tolerate
    public Reservation(SubmittedReservation submittedReservation){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.setStartAt(LocalDateTime.parse(submittedReservation.getStartAt(), df));
        this.setPredictedEndAt(LocalDateTime.parse(submittedReservation.getPredictedEndAt(), df));
        this.setActualEndAt(LocalDateTime.parse(submittedReservation.getPredictedEndAt(), df));
        this.setStatus(1);
        this.setCar(submittedReservation.getCar());
        this.setMobile(submittedReservation.getMobile());
        this.setEmail(submittedReservation.getEmail());
        this.setFirstName(submittedReservation.getFirstName());
        this.setLastName(submittedReservation.getLastName());
    }
    @JsonIgnore
    private transient ReservationRepository orderRepository;

    public int create(){
        return orderRepository.save(this);
    }

    public List<Reservation> findBy(String mobile){
        return orderRepository.findBy(mobile);
    }
}
