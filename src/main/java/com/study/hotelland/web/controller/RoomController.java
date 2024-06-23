package com.study.hotelland.web.controller;

import com.study.hotelland.mapper.RoomMapper;
import com.study.hotelland.service.RoomService;
import com.study.hotelland.web.dto.filter.RoomFilter;
import com.study.hotelland.web.dto.room.RoomRequest;
import com.study.hotelland.web.dto.room.RoomResponse;
import com.study.hotelland.web.dto.room.RoomResponseList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotelland/room")
@RequiredArgsConstructor
@Validated
public class RoomController {

    private final RoomService roomService;

    private final RoomMapper roomMapper;

    @GetMapping
    public ResponseEntity<RoomResponseList> findAll() {
        return ResponseEntity.ok(roomMapper.roomListToRoomResponseList(roomService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(roomMapper.roomEntityToRoomResponse(roomService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.roomEntityToRoomResponse(roomService.create(roomMapper.roomRequestToRoomEntity(request))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoomResponse> update(@PathVariable(name = "id") @NotNull Long id, @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomMapper.roomEntityToRoomResponse
                (roomService.update(roomMapper.roomRequestToRoomEntity(id, request))));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        roomService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter-by")
    public ResponseEntity<RoomResponseList> filterBy(@Valid RoomFilter filter) {
        return ResponseEntity.ok(roomMapper.roomListToRoomResponseList(roomService.filterBy(filter)));
    }


}
