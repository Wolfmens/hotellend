package com.study.hotelland.mapper;


import com.study.hotelland.entity.Room;
import com.study.hotelland.service.HotelService;
import com.study.hotelland.web.dto.room.RoomRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class RoomMapperDelegate implements RoomMapper {

    @Autowired
    private HotelService hotelService;

    @Override
    public Room roomRequestToRoomEntity(RoomRequest request) {
        Room room = new Room();
        room.setName(request.getName());
        room.setPrice(request.getPrice());
        room.setNumber(request.getNumber());
        room.setDescription(request.getDescription());
        room.setMaxPeople(request.getMaxPeople());
        if (request.getHotelId() == null) {
            room.setHotel(null);
        } else {
            room.setHotel(hotelService.findById(request.getHotelId()));
        }
        room.setBlockDates(new CopyOnWriteArrayList<>());
        return room;
    }

    @Override
    public Room roomRequestToRoomEntity(Long roomId, RoomRequest request) {
        Room room = roomRequestToRoomEntity(request);
        room.setId(roomId);

        return room;
    }
}
