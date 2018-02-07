package com.kaceper.dto;

import com.kaceper.model.Reservation;
import com.kaceper.model.Room;
import com.kaceper.model.User;

import java.sql.Date;

public class ReservationDto {

    private int reservationId;
    private String dateFrom;
    private String dateTo;
    private String reservationState;
    private String paidone;
    private String paymentMethod;
    private String details;
    private String price;
    private String paymentDate;
    private static Room roomId;
    private static User userId;


    public String getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public Room getRoomId() {
        return roomId;
    }
    public void setRoomId(Room roomId) {
        ReservationDto.roomId = roomId;
    }
    public User getUserId() {
        return userId;
    }
    public void setUserId(User userId) {
        ReservationDto.userId = userId;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public int getReservationId() {
        return reservationId;
    }
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    public String getDateFrom() {
        return dateFrom;
    }
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }
    public String getDateTo() {
        return dateTo;
    }
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
    public String getReservationState() {
        return reservationState;
    }
    public void setReservationState(String reservationState) {
        this.reservationState = reservationState;
    }
    public String getPaidone() {
        return paidone;
    }
    public void setPaidone(String paidone) {
        this.paidone = paidone;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void wrap(Reservation reservation){
        this.reservationId = reservation.getReservationId();
        this.dateFrom = reservation.getDateFrom();
        this.dateTo = reservation.getDateTo();
        this.reservationState = reservation.getReservationState();
        this.paidone = reservation.getPaidone();
        this.paymentMethod = reservation.getPaymentMethod();
        this.details = reservation.getDetails();
        this.price = reservation.getPrice();
        this.roomId = reservation.getRoomId();
        this.userId = reservation.getUserId();
    }

    public static ReservationDto make(Reservation reservation){
        ReservationDto dto = new ReservationDto();
        dto.wrap(reservation);

        return dto;
    }
}
