package com.kaceper.dto;

import com.kaceper.model.Room;

import java.util.List;
import java.util.stream.Collectors;

public class FreeRoomsDto {

    public RoomDto room;
    public List<ReservationDto> reservations;

    public RoomDto getRoomDto() {
        return room;
    }
    public void setRoomDto(RoomDto room) {
        this.room = room;
    }
    public List<ReservationDto> getReservations() {
        return reservations;
    }
    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }

    public void wrap(Room r){
        this.room = RoomDto.make(r);
        this.reservations = r.getReservationList()
                .stream()
                .map(ReservationDto::make)
                .collect(Collectors.toList());
    }

    public static FreeRoomsDto make(Room r){
        FreeRoomsDto dto = new FreeRoomsDto();
        dto.wrap(r);

        return dto;
    }
}
