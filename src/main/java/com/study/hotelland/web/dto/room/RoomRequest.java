package com.study.hotelland.web.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private String name;

    private String description;

    private Long number;

    private Long price;

    private Long maxPeople;

    private Long hotelId;

}
