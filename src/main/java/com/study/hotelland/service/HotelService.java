package com.study.hotelland.service;

import com.study.hotelland.entity.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> findAll(Integer pageSize, Integer pageNumber);

    Hotel findById(Long id);

    Hotel create(Hotel hotel);

    Hotel update(Hotel hotel);

    void deleteById(Long id);

}
