package com.study.hotelland.web.controller;

import com.study.hotelland.mapper.HotelMapper;
import com.study.hotelland.service.HotelService;
import com.study.hotelland.web.dto.hotel.HotelRequest;
import com.study.hotelland.web.dto.hotel.HotelResponse;
import com.study.hotelland.web.dto.hotel.HotelResponseList;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotelland/hotel")
public class HotelController {

    private final HotelMapper hotelMapper;

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<HotelResponseList> findAll(@RequestParam @NotNull Integer pageSize,
                                                     @RequestParam @NotNull Integer pageNumber) {
        return ResponseEntity.ok(hotelMapper.listHotelEntityToHotelResponseLIst(hotelService.findAll(pageSize, pageNumber)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById(@PathVariable(name = "id") @NotNull Long hotelId) {
        return ResponseEntity.ok(hotelMapper.hotelEntityToHotelResponse(hotelService.findById(hotelId)));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> create(@RequestBody HotelRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelMapper.hotelEntityToHotelResponse(hotelService.create(hotelMapper.hotelRequestToHotelEntity(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable(name = "id") @NotNull Long hotelId,@RequestBody HotelRequest request) {
        return ResponseEntity
                .ok(hotelMapper.hotelEntityToHotelResponse
                        (hotelService.update(hotelMapper.hotelRequestToHotelEntity(hotelId, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") @NotNull Long hotelId) {
        hotelService.deleteById(hotelId);
        return ResponseEntity.noContent().build();
    }
}
