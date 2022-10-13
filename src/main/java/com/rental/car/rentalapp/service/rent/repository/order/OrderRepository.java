package com.rental.car.rentalapp.service.rent.repository.order;


import com.rental.car.rentalapp.service.rent.model.Order;

import java.util.List;


public interface OrderRepository {
    public List<Order> findBy(String mobile);
    public int save(Order order);
}
