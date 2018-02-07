package com.kaceper.repository;

import com.kaceper.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
}
