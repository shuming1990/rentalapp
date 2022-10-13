package com.rental.car.rentalapp.controller.api;


import com.rental.car.rentalapp.infrasturcture.dto.ResponseEntity;
import com.rental.car.rentalapp.service.rent.service.RentService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/car")
public class CarApiController {

    final RentService rentService ;

    public CarApiController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping(value="/query")
    public ResponseEntity queryAvailableCars(@RequestParam("startAt") String startAt, @RequestParam("endAt") String endAt, @RequestParam("model")String model){
        return rentService.findAvailableCars(model,startAt, endAt);
    }
}
