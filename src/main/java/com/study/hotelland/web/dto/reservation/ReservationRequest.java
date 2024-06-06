package com.study.hotelland.web.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private LocalDate arrival;

    private LocalDate departure;

    private Long visitorId;

    private Long roomId;
}
