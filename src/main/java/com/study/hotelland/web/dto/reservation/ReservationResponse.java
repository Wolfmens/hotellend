package com.study.hotelland.web.dto.reservation;

import com.study.hotelland.web.dto.room.RoomResponse;
import com.study.hotelland.web.dto.visitor.VisitorResponse;

import java.time.LocalDate;

public class ReservationResponse {

    private Long id;

    private LocalDate arrival;

    private LocalDate departure;

    private VisitorResponse visitor;

    private RoomResponse room;
}
