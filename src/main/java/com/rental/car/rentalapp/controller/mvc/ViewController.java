package com.rental.car.rentalapp.controller.mvc;

import com.rental.car.rentalapp.infrasturcture.dto.CarQuery;
import com.rental.car.rentalapp.infrasturcture.dto.ReservationQuery;
import com.rental.car.rentalapp.infrasturcture.dto.SubmittedOrder;
import com.rental.car.rentalapp.infrasturcture.exception.AppException;
import com.rental.car.rentalapp.service.rent.model.Car;
import com.rental.car.rentalapp.service.rent.model.Order;
import com.rental.car.rentalapp.service.rent.service.RentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ViewController {

    final RentService rentService ;

    public ViewController(RentService rentService) {
        this.rentService = rentService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        if(model.getAttribute("carQuery")==null)
            model.addAttribute("carQuery",new CarQuery());
        return "hello";
    }

    @RequestMapping("/hello")
    public String hello(Model model) {
        if(model.getAttribute("carQuery")==null)
            model.addAttribute("carQuery",new CarQuery());
        return "hello";
    }

    @RequestMapping("/check-reservation")
    public String searchReservation(Model model) {
        model.addAttribute("reservationQuery",new ReservationQuery());
        return "reservation";
    }

    @RequestMapping("/check-my-reservations")
    public String searchMyReservation(Model model, @ModelAttribute ReservationQuery reservationQuery) {
        model.addAttribute("reservationQuery",reservationQuery);
        model.addAttribute("orders", rentService.findOrdersByPhone(reservationQuery.getMobile(), null).getResponseObject());
        return "reservation";
    }

    @RequestMapping("/query-available-cars")
    public String query(Model model, @ModelAttribute CarQuery carQuery){
        SubmittedOrder submittedOrder = SubmittedOrder.builder().startAt(carQuery.getStartAt()).predictedEndAt(carQuery.getEndAt()).build();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        carQuery.setStartAt(carQuery.getStartAt().replaceAll("T", " ") + ":00");
        carQuery.setEndAt(carQuery.getEndAt().replaceAll("T", " ") + ":00");

        LocalDateTime start = LocalDateTime.parse(carQuery.getStartAt(), df);
        LocalDateTime end = LocalDateTime.parse(carQuery.getEndAt(), df);

        List<Car> cars;
        try {
            cars = (List<Car>) rentService.findAvailableCars(carQuery.getModel(),start, end).getResponseObject();
        }catch (AppException appException){
            model.addAttribute("msg", appException.getMessage());
            return hello(model);
        }
        model.addAttribute("cars", cars);
        model.addAttribute("submittedOrder",submittedOrder);
        return "available";
    }

    @RequestMapping("/rent-car")
    public String rent(Model model, @ModelAttribute SubmittedOrder submittedOrder){
        CarQuery carQuery = CarQuery.builder().startAt(submittedOrder.getStartAt()).endAt(submittedOrder.getPredictedEndAt()).build();
        submittedOrder.setStartAt(submittedOrder.getStartAt().replaceAll("T", " ") + ":00");
        submittedOrder.setPredictedEndAt(submittedOrder.getPredictedEndAt().replaceAll("T", " ") + ":00");

        Order order = new Order(submittedOrder);
        try {
            rentService.create(order);
        }catch (AppException appException){
            model.addAttribute("msg", appException.getMessage());
            return query(model, carQuery);
        }
        model.addAttribute("carQuery", new CarQuery());
        model.addAttribute("success", "Order Placed!");
        return "hello";
    }
}
