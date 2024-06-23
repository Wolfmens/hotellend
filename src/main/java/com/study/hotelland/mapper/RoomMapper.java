package com.study.hotelland.mapper;


import com.study.hotelland.entity.Room;
import com.study.hotelland.web.dto.room.RoomRequest;
import com.study.hotelland.web.dto.room.RoomResponse;
import com.study.hotelland.web.dto.room.RoomResponseList;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room roomRequestToRoomEntity(RoomRequest request);

    @Mapping(source = "roomId", target = "id")
    Room roomRequestToRoomEntity(Long roomId, RoomRequest request);

    @Mapping(target = "hotelId", expression = "java(room.getHotel().getId())")
    RoomResponse roomEntityToRoomResponse(Room room);

    default RoomResponseList roomListToRoomResponseList(List<Room> roomList) {
        RoomResponseList roomResponseList = new RoomResponseList();
        roomResponseList.setRoomResponseList(roomList
                .stream()
                .map(this::roomEntityToRoomResponse)
                .toList());

        return roomResponseList;
    }


}
