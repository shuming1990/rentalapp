package com.rental.car.rentalapp.service.rent.repository.reservation;


import com.rental.car.rentalapp.service.rent.model.Reservation;

import java.util.List;


public interface ReservationRepository {
    public List<Reservation> findBy(String mobile);
    public int save(Reservation reservation);
}
