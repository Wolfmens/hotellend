package com.study.hotelland.service.impl;

import com.study.hotelland.entity.Reservation;
import com.study.hotelland.entity.Room;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.exception.NotPossibleReservationRoom;
import com.study.hotelland.repository.ReservationRepository;
import com.study.hotelland.service.ReservationService;
import com.study.hotelland.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    private final RoomService roomService;

    @Override
    public List<Reservation> findAll() {
        return repository.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundEntityException(MessageFormat.format("Reservation with id {0} not found", id)));
    }

    @Override
    @Transactional
    public Reservation create(Reservation reservation) {
        Room reservationRoom = roomService.findById(reservation.getRoom().getId());

        List<LocalDate> listDatesWhichNeedToReservation = reservation.getArrival()
                .datesUntil(reservation.getDeparture().plusDays(1)).toList();

        isPossibleReservationRoom(reservationRoom.getBlockDates(), listDatesWhichNeedToReservation);

        listDatesWhichNeedToReservation.forEach(reservationRoom.getBlockDates()::add);

        roomService.update(reservationRoom);

        return repository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation update(Reservation reservation) {
        Reservation reservationFromBd = findById(reservation.getId());

        if (Objects.nonNull(reservation.getRoom()) && !reservation.getRoom().equals(reservationFromBd.getRoom())) {
            reservationFromBd.setRoom(reservation.getRoom());
        }

        if (StringUtils.hasText(reservation.getArrival().toString()) &&
                StringUtils.hasText(reservation.getDeparture().toString())) {

            Room reservationRoom = roomService.findById(reservation.getRoom().getId());
            List<LocalDate> listDatesWhichNeedToReservation = reservation.getArrival()
                    .datesUntil(reservation.getDeparture().plusDays(1)).toList();

            isPossibleReservationRoom(reservationRoom.getBlockDates(), listDatesWhichNeedToReservation);
            listDatesWhichNeedToReservation.forEach(reservationRoom.getBlockDates()::add);

            roomService.update(reservationRoom);
        }
        if (Objects.nonNull(reservation.getVisitor())) {
            reservationFromBd.setVisitor(reservation.getVisitor());
        }
            return repository.save(reservation);
    }

    @Override
    public void delete(Long id) {
        Reservation reservationFromBd = findById(id);
        List<LocalDate> listDatesWhichNeedToReservation = reservationFromBd.getArrival()
                .datesUntil(reservationFromBd.getDeparture().plusDays(1)).toList();

        Room reservationRoom = roomService.findById(reservationFromBd.getRoom().getId());
        listDatesWhichNeedToReservation.forEach(reservationRoom.getBlockDates()::remove);
        roomService.update(reservationRoom);

        repository.deleteById(id);
    }

    private void isPossibleReservationRoom(CopyOnWriteArrayList<LocalDate> blockDates,
                                           List<LocalDate> listDatesWhichNeedToReservation) {

        boolean hasReservationDatedFromChooses =
                blockDates
                        .stream()
                        .anyMatch(listDatesWhichNeedToReservation::contains);

        if (hasReservationDatedFromChooses) {
            throw new NotPossibleReservationRoom
                    ("Block reservation a room on these dates. " +
                            "Already booked!!! Please, choose another dates arrive and departure");
        }
    }


}
