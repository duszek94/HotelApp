package com.kaceper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kaceper.dto.ReservationDto;
import com.kaceper.dto.RoomDto;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Room")
public class Room {

    public Room(){}

    public Room(int roomId){
        this.roomId = roomId;
    }

    @Id
    @Column(name = "roomId")
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private int roomId;

    @Column(name = "roomType")
    @NotEmpty
    private String roomType;

    @Column(name = "price")
    @NotNull
    private int price;

    @Column(name = "roomNumber")
    @NotNull
    private int roomNumber;

    @JsonManagedReference(value = "roomId-ref")
    @OneToMany(mappedBy = "roomId", fetch = FetchType.LAZY)
    private List<Reservation> reservationList = new ArrayList<>();


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
    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public List<Reservation> getReservationList() {
        return reservationList;
    }
    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public List<ReservationDto> getReservationDtoList(List<Reservation> reservationList ){
        List<ReservationDto> list = new ArrayList<>();

        for(Reservation r : reservationList){
            ReservationDto dto = new ReservationDto();

            dto.setDateFrom(r.getDateFrom());
            dto.setDateTo(r.getDateTo());
            dto.setPaidone(r.getPaidone());
            dto.setPaymentMethod(r.getPaymentMethod());
            dto.setReservationId(r.getReservationId());
            dto.setDetails(r.getDetails());

            list.add(dto);
        }

        return list;
    }

    public RoomDto getRoomDto(Room r){
        RoomDto dto = new RoomDto();

        dto.setRoomId(r.getRoomId());
        dto.setRoomType(r.getRoomType());
        dto.setReservations(r.getReservationDtoList(r.getReservationList()));

        return dto;
    }
}
