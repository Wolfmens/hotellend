package com.study.hotelland.mapper;


import com.study.hotelland.entity.Reservation;
import com.study.hotelland.service.RoomService;
import com.study.hotelland.service.VisitorService;
import com.study.hotelland.web.dto.reservation.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ReservationMapperDelegate implements ReservationMapper {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private RoomService roomService;

    @Override
    public Reservation reservationRequestToReservationEntity(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setArrival(reservation.getArrival());
        reservation.setDeparture(reservation.getDeparture());
        reservation.setVisitor(visitorService.findById(request.getVisitorId()));
        reservation.setRoom(roomService.findById(request.getRoomId()));

        return reservation;
    }

    @Override
    public Reservation reservationRequestToReservationEntity(Long reservationId, ReservationRequest request) {
        Reservation reservation = reservationRequestToReservationEntity(request);
        reservation.setId(reservationId);

        return reservation;
    }
}
