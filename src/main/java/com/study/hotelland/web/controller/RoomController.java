package com.study.hotelland.web.controller;

import com.study.hotelland.mapper.RoomMapper;
import com.study.hotelland.service.RoomService;
import com.study.hotelland.web.dto.room.RoomRequest;
import com.study.hotelland.web.dto.room.RoomResponse;
import com.study.hotelland.web.dto.room.RoomResponseList;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotelland/room")
@RequiredArgsConstructor
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
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.roomEntityToRoomResponse(roomService.create(roomMapper.roomRequestToRoomEntity(request))));
    }

    @PutMapping("{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable @NotNull Long id, @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomMapper.roomEntityToRoomResponse
                (roomService.update(roomMapper.roomRequestToRoomEntity(id,request))));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        roomService.delete(id);

        return ResponseEntity.noContent().build();
    }



}
