package com.study.hotelland.web.controller;

import com.study.hotelland.mapper.ReservationMapper;
import com.study.hotelland.service.ReservationService;
import com.study.hotelland.web.dto.reservation.ReservationRequest;
import com.study.hotelland.web.dto.reservation.ReservationResponse;
import com.study.hotelland.web.dto.reservation.ReservationResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotelland/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationMapper reservationMapper;

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ReservationResponseList> findAll() {
        return ResponseEntity.ok(reservationMapper.reservationListToReservationResponseList(reservationService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(ReservationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationMapper.reservationEntityToReservationResponse
                        (reservationService.create(reservationMapper.reservationRequestToReservationEntity(request))));
    }
}
