package com.study.hotelland.service.impl;

import com.study.hotelland.entity.Room;
import com.study.hotelland.exception.ListIsEmptyByFilter;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.repository.ReservationRepository;
import com.study.hotelland.repository.RoomRepository;
import com.study.hotelland.repository.specification.RoomSpecification;
import com.study.hotelland.service.RoomService;
import com.study.hotelland.web.dto.filter.RoomFilter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final ReservationRepository reservationRepository;


    @Override
    public List<Room> findAll() {
        return repository.findAll();
    }

    @Override
    public Room findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->  new NotFoundEntityException(MessageFormat.format("Room with id {0} not found", id)));
    }

    @Override
    public Room create(Room room) {
        return repository.save(room);
    }

    @Override
    public Room update(Room room) {
        Room roomFromDb = findById(room.getId());
        if (StringUtils.hasText(room.getName())) {
            roomFromDb.setName(room.getName());
        }
        if (StringUtils.hasText(room.getDescription())) {
            roomFromDb.setDescription(room.getDescription());
        }
        if (StringUtils.hasText(room.getDescription())) {
            roomFromDb.setDescription(room.getDescription());
        }
        if (Objects.nonNull(room.getNumber())) {
            roomFromDb.setNumber(room.getNumber());
        }
        if (Objects.nonNull(room.getPrice())) {
            roomFromDb.setPrice(room.getPrice());
        }
        if (Objects.nonNull(room.getMaxPeople())) {
            roomFromDb.setMaxPeople(room.getMaxPeople());
        }
        if (Objects.nonNull(room.getBlockDates()) || !room.getBlockDates().isEmpty()) {
            roomFromDb.setBlockDates(room.getBlockDates());
        }
        if (Objects.nonNull(room.getHotel())) {
            roomFromDb.setHotel(room.getHotel());
        }

        return repository.save(roomFromDb);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteByRoomId(id);
        repository.deleteById(id);
    }

    @Override
    public List<Room> filterBy(RoomFilter filter) {
        Page<Room> listByRequestFilterRoom = repository.findAll(
                RoomSpecification.filterBy(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        if (listByRequestFilterRoom.getContent().isEmpty()) {
            throw new ListIsEmptyByFilter("Rooms");
        }
        return listByRequestFilterRoom.getContent();
    }
}
