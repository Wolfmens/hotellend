package com.study.hotelland.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Long number;

    private Long price;

    @Column(name = "max_people")
    private Long maxPeople;

    @Column(name = "block_dates")
    @ToString.Exclude
    @Builder.Default
    private CopyOnWriteArrayList<LocalDate> blockDates = new CopyOnWriteArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @ToString.Exclude
    private Hotel hotel;

}
