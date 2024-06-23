package com.study.hotelland.web.controller;

import com.study.hotelland.exception.IncorrectRequestFilterFiledByArrivalOrDepartureDate;
import com.study.hotelland.exception.ListIsEmptyByFilter;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.exception.NotPossibleReservationRoom;
import com.study.hotelland.web.dto.exception.ExceptionMessage;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ExceptionMessage> exceptionEntityNotFound(NotFoundEntityException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionMessage(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NotPossibleReservationRoom.class)
    public ResponseEntity<ExceptionMessage> exceptionIfNotPossibleReservationRoomByChooseDates(NotPossibleReservationRoom ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(HttpServerErrorException.BadGateway.class)
    public ResponseEntity<ExceptionMessage> exceptionServerBadGateway500(HttpServerErrorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ExceptionMessage(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(ListIsEmptyByFilter.class)
    public ResponseEntity<ExceptionMessage> exceptionServerBadGateway500(ListIsEmptyByFilter ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(IncorrectRequestFilterFiledByArrivalOrDepartureDate.class)
    public ResponseEntity<ExceptionMessage> exceptionIfIncorrectFieldsArriveOrDeparture
            (IncorrectRequestFilterFiledByArrivalOrDepartureDate ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionMessage> validationNameOrEmailAlreadyExists
            (ValidationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionMessage(ex.getLocalizedMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> exceptionNotValidParams(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<String> messages = bindingResult
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String exceptionMessage = String.join("; ", messages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(exceptionMessage));
    }


}
