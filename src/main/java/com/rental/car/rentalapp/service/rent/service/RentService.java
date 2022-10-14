package com.rental.car.rentalapp.service.rent.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.rental.car.rentalapp.infrasturcture.dto.ResponseEntity;
import com.rental.car.rentalapp.infrasturcture.exception.AppException;
import com.rental.car.rentalapp.infrasturcture.exception.ResultCode;
import com.rental.car.rentalapp.service.rent.repository.car.CarRepository;
import com.rental.car.rentalapp.service.rent.repository.order.OrderRepository;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental.car.rentalapp.service.rent.model.Car;
import com.rental.car.rentalapp.service.rent.model.Client;
import com.rental.car.rentalapp.service.rent.model.Order;

import javax.annotation.Resource;

@Service
public class RentService{

    @Resource
    CarRepository carRepository;
    @Resource
    OrderRepository orderRepository;

    final ExpiringMap<String, String> appExpiringMap;

    public RentService(ExpiringMap<String, String> appExpiringMap) {
        this.appExpiringMap = appExpiringMap;
    }

    public ResponseEntity findAvailableCars(String model, String startAt, String endAt) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start;
        LocalDateTime end;
        try{
            start = LocalDateTime.parse(startAt,df);
            end = LocalDateTime.parse(endAt,df);
        }catch (Exception ex){
            throw new AppException(ResultCode.RULE_VIOLATION, "Parsed Time Failed. Please check the chosen time format.");
        }
        return findAvailableCars(model, start, end);
    }

    public ResponseEntity findAvailableCars(String model, LocalDateTime start, LocalDateTime end) {
        if(start.isBefore(LocalDateTime.now()))
            throw new AppException(ResultCode.RULE_VIOLATION, "The start time should be after current time.");
        if(start.isAfter(end))
            throw new AppException(ResultCode.RULE_VIOLATION, "The start time should be before end time.");
        Client client = Client.builder().build();
        Car car = Car.builder().model(model).carRepository(carRepository).build();
        List<Car> cars = client.query(car, start , end);
        if(!Strings.isBlank(model))
            cars = cars.stream().filter(item -> item.getModel().equals(model)).collect(Collectors.toList());
        return new ResponseEntity<>(ResultCode.SUCCESS,cars);
    }

    public ResponseEntity create(Order order) {
        // time rules violation
        if(order.getStartAt().isBefore(LocalDateTime.now()))
            throw new AppException(ResultCode.RULE_VIOLATION, "The start time should be after current time.");
        if(order.getStartAt().isAfter(order.getPredictedEndAt()))
            throw new AppException(ResultCode.RULE_VIOLATION, "The start time should before end time.");
        // mobile should not be blank
        if(Strings.isBlank(order.getMobile().trim()))
            throw new AppException(ResultCode.RULE_VIOLATION, "Mobile phone is required.");
        if(Strings.isBlank(order.getFirstName().trim()) || Strings.isBlank(order.getFirstName().trim()))
            throw new AppException(ResultCode.RULE_VIOLATION, "Full name is required.");
        // car number should not be black
        if(Strings.isBlank(order.getCar().getCarNumber().trim()))
            throw new AppException(ResultCode.RULE_VIOLATION, "You have to choose a car for reservation.");
        order.setOrderRepository(orderRepository);
        Client client = Client.builder().order(order).build();
        return new ResponseEntity<>(ResultCode.SUCCESS,client.reserve());
    }

    public ResponseEntity generateConfirmCode(String mobile){
        if(null != appExpiringMap.get(mobile))
            throw new AppException(ResultCode.RULE_VIOLATION, "Your code is valid for five minutes. You don't need to apply for it again.");
        String code = String.valueOf((int)((Math.random() * 9 + 1) * Math.pow(10,5)));
        appExpiringMap.put(mobile, code);
        return new ResponseEntity<>(ResultCode.SUCCESS,code);
    }

    public ResponseEntity findOrdersByPhone(String mobile, String confirmCode){
//        if(!confirmCode.equals(appExpiringMap.get(mobile)))
//            throw new AppException(ResultCode.RULE_VIOLATION, "Please apply for confirm code first.");

        Client client = Client.builder().order(Order.builder().orderRepository(orderRepository).build()).build();
        List<Order> result = client.findOrderWith(mobile);
        result.stream().forEach(order->
                        order.setTransactionId(order.getMobile().substring(order.getMobile().length()-4)
                                + padLeadingChars(String.valueOf(order.getOrderId()),'0', 11)));
        return new ResponseEntity<>(ResultCode.SUCCESS,result);
    }

    public ResponseEntity cancelReservation(Order order) {
        return null;
    }

    public ResponseEntity returnCar(Car car) {
        return null;
    }

    static String padLeadingChars(String source, char anyChar, int resultLength) {
        char[] chars = new char[resultLength];
        Arrays.fill(chars, anyChar);
        return (new String(chars) + source).substring(source.length());
    }
}
