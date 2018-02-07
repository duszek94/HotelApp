package com.kaceper.service;

import com.kaceper.dto.ReservationDto;
import com.kaceper.model.Reservation;
import javassist.NotFoundException;

import java.util.HashMap;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(ReservationDto reservationDto);
    List< HashMap<String , String> > getAll();
    ReservationDto getOne(int reservationId) throws NotFoundException;
    boolean deleteReservation(Integer id);
    Reservation updateReservation(ReservationDto reservationDto);
    boolean confirmReservation(int id);
}
