package com.study.hotelland.service.impl;

import com.study.hotelland.entity.Hotel;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.repository.HotelRepository;
import com.study.hotelland.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;

    @Override
    public List<Hotel> findAll(Integer pageSize, Integer pageNumber) {
        return repository.findAll(PageRequest.of(pageNumber,pageSize)).getContent();
    }

    @Override
    public Hotel findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundEntityException(MessageFormat.format("Hotel with id {0} not found", id)));
    }

    @Override
    public Hotel create(Hotel hotel) {
        return repository.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        Hotel hotelFromDBForUpdate = findById(hotel.getId());

        if (StringUtils.hasText(hotel.getAddress())) {
            hotelFromDBForUpdate.setAddress(hotel.getAddress());
        }
        if (StringUtils.hasText(hotel.getCity())) {
            hotelFromDBForUpdate.setCity(hotel.getCity());
        }
        if (StringUtils.hasText(hotel.getName())) {
            hotelFromDBForUpdate.setName(hotel.getName());
        }
        if (StringUtils.hasText(hotel.getHeadline())) {
            hotelFromDBForUpdate.setHeadline(hotel.getHeadline());
        }
        if (Objects.nonNull(hotel.getDistanceToCenter())) {
            hotelFromDBForUpdate.setDistanceToCenter(hotel.getDistanceToCenter());
        }
        if (Objects.nonNull(hotel.getHotelRating())) {
            hotelFromDBForUpdate.setHotelRating(hotel.getHotelRating());
        }
        if (Objects.nonNull(hotel.getNumberRatings())) {
            hotelFromDBForUpdate.setNumberRatings(hotel.getNumberRatings());
        }

        return repository.save(hotelFromDBForUpdate);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
