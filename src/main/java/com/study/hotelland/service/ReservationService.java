package com.study.hotelland.service;

import com.study.hotelland.entity.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    void delete(Long id);

}
