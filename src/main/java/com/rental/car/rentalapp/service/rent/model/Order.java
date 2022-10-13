package com.rental.car.rentalapp.service.rent.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rental.car.rentalapp.infrasturcture.dto.SubmittedOrder;
import com.rental.car.rentalapp.service.rent.repository.order.OrderRepository;
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
public class Order implements Serializable {
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
    public Order(){}
    @Tolerate
    public Order(SubmittedOrder submittedOrder){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.setStartAt(LocalDateTime.parse(submittedOrder.getStartAt(), df));
        this.setPredictedEndAt(LocalDateTime.parse(submittedOrder.getPredictedEndAt(), df));
        this.setActualEndAt(LocalDateTime.parse(submittedOrder.getPredictedEndAt(), df));
        this.setStatus(1);
        this.setCar(submittedOrder.getCar());
        this.setMobile(submittedOrder.getMobile());
        this.setEmail(submittedOrder.getEmail());
        this.setFirstName(submittedOrder.getFirstName());
        this.setLastName(submittedOrder.getLastName());
    }
    @JsonIgnore
    private transient OrderRepository orderRepository;

    public int create(){
        return orderRepository.save(this);
    }

    public List<Order> findBy(String mobile){
        return orderRepository.findBy(mobile);
    }
}
