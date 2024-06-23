package com.study.hotelland.service;

import com.study.hotelland.entity.Room;
import com.study.hotelland.web.dto.filter.RoomFilter;

import java.util.List;

public interface RoomService {

    List<Room> findAll();

    Room findById(Long id);

    Room create(Room room);

    Room update(Room room);

    void delete(Long id);

    List<Room> filterBy(RoomFilter filter);
}
