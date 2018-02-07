package com.kaceper.service;


import com.kaceper.dto.ReservationDto;
import com.kaceper.model.Reservation;
import com.kaceper.repository.ReservationRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Service("reservationService")
@Transactional
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    EntityManager em;

    @Autowired
    ReservationRepository reservationRepository;

    public Reservation createReservation(ReservationDto reservationDto){
        Reservation reservation = new Reservation();

        reservation.setDateFrom(reservationDto.getDateFrom());
        reservation.setDateTo(reservationDto.getDateTo());
        reservation.setDetails(reservationDto.getDetails());
        reservation.setPaidone(reservationDto.getPaidone());
        reservation.setPaymentMethod(reservationDto.getPaymentMethod());
        reservation.setReservationState(reservationDto.getReservationState());
        reservation.setPrice(reservationDto.getPrice());
        reservation.setPaymentDate(reservationDto.getPaymentDate());
        reservation.setRoomId(reservationDto.getRoomId());
        reservation.setUserId(reservationDto.getUserId());

        reservationRepository.save(reservation);

        return reservation;
    }


    @SuppressWarnings("unchecked")
    public List< HashMap<String , String> > getAll(){
        return em.createQuery("select new map(r.reservationId as reservationId, r.paidone as paidone, " +
                "r.reservationState as reservationState, r.paymentMethod as paymentMethod, r.details as details, r.paymentDate as paymentDate, " +
                "r.dateFrom as dateFrom, r.dateTo as dateTo, r.price as price, rm.roomId as roomId, " +
                "rm.roomType as roomType, u.userId as userId, u.firstname as firstname, u.lastname as lastname, " +
                "u.phone as phone, u.email as email) " +
                "from Room rm join Reservation r " +
                "on rm.roomId = r.roomId.roomId join User u " +
                "on r.userId.userId = u.userId " +
                "where r.roomId is not null")
                .getResultList();
    }

    public ReservationDto getOne(int reservationId) throws NotFoundException {
        Reservation r = reservationRepository.findOne(reservationId);
        if(r == null) {
            throw new NotFoundException("reservation");
        }
        return ReservationDto.make(r);
    }

    public boolean deleteReservation(Integer id){
        Reservation r = em.createQuery("select r from Reservation r " +
                "where r.reservationId = :id", Reservation.class)
                .setParameter("id", id)
                .getSingleResult();

        em.remove(r);

        return true;
    }

    public boolean confirmReservation(int id){
        String c = em.createQuery("select r.reservationState from Reservation r " +
                "where r.reservationId = :id", String.class)
                .setParameter("id", id)
                .getSingleResult();
        if(c.equals("t")){
            return false;
        }else {
            Query q = em.createQuery("update Reservation r " +
                    "set r.reservationState = 't' " +
                    "where r.reservationId = :reservationId")
                    .setParameter("reservationId", id);
            q.executeUpdate();

            return true;
        }
    }

    public Reservation updateReservation(ReservationDto reservationDto){
        Reservation r = reservationRepository.findOne(reservationDto.getReservationId());
        if(r != null){
            r.setDateFrom(reservationDto.getDateFrom());
            r.setDateTo(reservationDto.getDateTo());
            r.setDetails(reservationDto.getDetails());
            r.setPaidone(reservationDto.getPaidone());
            r.setPaymentMethod(reservationDto.getPaymentMethod());
            r.setPrice(reservationDto.getPrice());
            r.setPaymentDate(reservationDto.getPaymentDate());
            r.setRoomId(reservationDto.getRoomId());
            r.setUserId(reservationDto.getUserId());
            reservationRepository.save(r);
        }
        return r;
    }
}
