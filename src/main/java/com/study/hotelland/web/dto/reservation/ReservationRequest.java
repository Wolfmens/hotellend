package com.study.hotelland.web.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private String arrival;

    private String departure;

    private Long visitorId;

    private Long roomId;
}
