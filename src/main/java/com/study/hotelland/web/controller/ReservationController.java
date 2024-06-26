package com.study.hotelland.web.controller;

import com.study.hotelland.mapper.ReservationMapper;
import com.study.hotelland.service.ReservationService;
import com.study.hotelland.web.dto.reservation.ReservationRequest;
import com.study.hotelland.web.dto.reservation.ReservationResponse;
import com.study.hotelland.web.dto.reservation.ReservationResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotelland/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationMapper reservationMapper;

    private final ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ReservationResponseList> findAll() {
        return ResponseEntity.ok(reservationMapper.reservationListToReservationResponseList(reservationService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationMapper.reservationEntityToReservationResponse
                        (reservationService.create(reservationMapper.reservationRequestToReservationEntity(request))));
    }
}
