package com.study.hotelland.web.controller;

import com.study.hotelland.entity.RoleType;
import com.study.hotelland.mapper.VisitorMapper;
import com.study.hotelland.service.VisitorService;
import com.study.hotelland.web.dto.visitor.VisitorRequest;
import com.study.hotelland.web.dto.visitor.VisitorResponse;
import com.study.hotelland.web.dto.visitor.VisitorResponseList;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotelland/visitor")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    private final VisitorMapper mapper;

    @GetMapping
    public ResponseEntity<VisitorResponseList> findAll() {
        return ResponseEntity.ok(mapper.visitorListToVisitorResponseList(visitorService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitorResponse> findById(@RequestParam("id") @NotNull Long visitorId) {
        return ResponseEntity.ok(mapper.visitorEntityToVisitorResponse(visitorService.findById(visitorId)));
    }

    @PostMapping
    public ResponseEntity<VisitorResponse> create(@RequestBody VisitorRequest request,
                                                  @RequestParam @NotNull RoleType type) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.visitorEntityToVisitorResponse
                        (visitorService.create(mapper.visitorRequestToVisitorEntity(request), type)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitorResponse> update(@RequestParam("id") @NotNull Long visitorId,
                                                  @RequestBody VisitorRequest request) {
        return ResponseEntity.ok(
                mapper.visitorEntityToVisitorResponse(
                        visitorService.update(mapper.visitorRequestToVisitorEntity(visitorId, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestParam("id") @NotNull Long visitorId) {
        visitorService.delete(visitorId);

        return ResponseEntity.noContent().build();
    }




}
