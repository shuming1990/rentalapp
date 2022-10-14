package com.rental.car.rentalapp.controller.mvc;

import com.rental.car.rentalapp.infrasturcture.dto.CarQuery;
import com.rental.car.rentalapp.infrasturcture.dto.ReservationQuery;
import com.rental.car.rentalapp.infrasturcture.dto.SubmittedReservation;
import com.rental.car.rentalapp.service.rent.model.Car;
import com.rental.car.rentalapp.service.rent.model.Reservation;
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
        model.addAttribute("reservations", rentService.findReservationsByPhone(reservationQuery.getMobile(), null).getResponseObject());
        return "reservation";
    }

    @RequestMapping("/query-available-cars")
    public String query(Model model, @ModelAttribute CarQuery carQuery){

        SubmittedReservation submittedReservation = SubmittedReservation.builder().startAt(carQuery.getStartAt()).predictedEndAt(carQuery.getEndAt()).build();
        List<Car> cars;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            carQuery.setStartAt(carQuery.getStartAt().replaceAll("T", " ") + ":00");
            carQuery.setEndAt(carQuery.getEndAt().replaceAll("T", " ") + ":00");
            LocalDateTime start = LocalDateTime.parse(carQuery.getStartAt(), df);
            LocalDateTime end = LocalDateTime.parse(carQuery.getEndAt(), df);
            cars = (List<Car>) rentService.findAvailableCars(carQuery.getModel(),start, end).getResponseObject();
        } catch (Exception appException){
            model.addAttribute("msg", appException.getMessage());
            return hello(model);
        }
        model.addAttribute("cars", cars);
        model.addAttribute("submittedReservation", submittedReservation);
        return "available";
    }

    @RequestMapping("/rent-car")
    public String rent(Model model, @ModelAttribute SubmittedReservation submittedReservation){
        CarQuery carQuery = CarQuery.builder().startAt(submittedReservation.getStartAt()).endAt(submittedReservation.getPredictedEndAt()).build();
        try {
            submittedReservation.setStartAt(submittedReservation.getStartAt().replaceAll("T", " ") + ":00");
            submittedReservation.setPredictedEndAt(submittedReservation.getPredictedEndAt().replaceAll("T", " ") + ":00");

            Reservation reservation = new Reservation(submittedReservation);
            rentService.create(reservation);
        }catch (Exception exception){
            model.addAttribute("msg", exception.getMessage());
            return query(model, carQuery);
        }
        model.addAttribute("carQuery", new CarQuery());
        model.addAttribute("success", "Reservation Placed!");
        return "hello";
    }
}
