package com.kaceper.dto;

import com.kaceper.model.Reservation;
import com.kaceper.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomDto {
    public int roomId;
    public String roomType;
    public List<ReservationDto> reservations = new ArrayList<>();
    public int price;
    public int roomNumber;
    public boolean isFree;

    public boolean getIsFree() {
        return isFree;
    }
    public void setIsFree(boolean free) {
        isFree = free;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public List<ReservationDto> getReservations() {
        return reservations;
    }
    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }


    public void wrap(Room room){

        this.roomId = room.getRoomId();
        this.roomType = room.getRoomType();
        this.price = room.getPrice();
        this.reservations = room.getReservationDtoList(room.getReservationList());
        this.roomNumber = room.getRoomNumber();
    }

    public static RoomDto make(Room room){
        RoomDto dto = new RoomDto();
        dto.wrap(room);

        return dto;
    }
}
