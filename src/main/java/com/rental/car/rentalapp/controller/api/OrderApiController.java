package com.rental.car.rentalapp.controller.api;

import com.rental.car.rentalapp.infrasturcture.dto.ResponseEntity;
import com.rental.car.rentalapp.service.rent.model.Order;
import com.rental.car.rentalapp.service.rent.service.RentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderApiController {

    final RentService rentService ;

    public OrderApiController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping(value="/create",produces = "application/json;charset=UTF-8")
    public ResponseEntity create(@RequestBody Order order){
        return rentService.create(order);
    }

    @GetMapping(value="/confirm-code",produces = "application/json;charset=UTF-8")
    public ResponseEntity fetchCode(@RequestParam("mobile")String mobile){
        return rentService.generateConfirmCode(mobile);
    }

    @GetMapping(value="/find",produces = "application/json;charset=UTF-8")
    public ResponseEntity find(@RequestParam("mobile")String mobile, @RequestParam("confirmCode") String confirmCode){
        return rentService.findOrdersByPhone(mobile,confirmCode);
    }

}
