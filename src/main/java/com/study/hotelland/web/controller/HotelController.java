package com.study.hotelland.web.controller;

import com.study.hotelland.mapper.HotelMapper;
import com.study.hotelland.service.HotelService;
import com.study.hotelland.web.dto.filter.HotelFilter;
import com.study.hotelland.web.dto.hotel.HotelRequest;
import com.study.hotelland.web.dto.hotel.HotelResponse;
import com.study.hotelland.web.dto.hotel.HotelResponseList;
import com.study.hotelland.web.dto.hotel.HotelResponseWithRatingAndNumberRatings;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotelland/hotel")
@Validated
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody HotelRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelMapper.hotelEntityToHotelResponse(hotelService.create(hotelMapper.hotelRequestToHotelEntity(request))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable(name = "id") @NotNull Long hotelId, @RequestBody HotelRequest request) {
        return ResponseEntity
                .ok(hotelMapper.hotelEntityToHotelResponse
                        (hotelService.update(hotelMapper.hotelRequestToHotelEntity(hotelId, request))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") @NotNull Long hotelId) {
        hotelService.deleteById(hotelId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/rating/{id}")
    public ResponseEntity<HotelResponseWithRatingAndNumberRatings> addRatingToHotel(@RequestParam
                                                                                    @Range(min = 1L, max = 5L, message = "The rating " +
                                                                                            "provided is outside the required range." +
                                                                                            "Must be from 1 to 5.")
                                                                                    Long newMark,
                                                                                    @PathVariable(value = "id") @NotNull Long hotelId) {

        return ResponseEntity.ok(hotelMapper.hotelEntityToHotelResponseWithRatingAndNumberRatings
                (hotelService.addHotelRating(newMark, hotelId)));
    }

    @GetMapping("/filter-by")
    public ResponseEntity<HotelResponseList> filterBy(@Valid HotelFilter filter) {
        return ResponseEntity.ok(hotelMapper.listHotelEntityToHotelResponseLIst(hotelService.filterBy(filter)));
    }
}
