package com.kaceper.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "Reservation")
public class Reservation {

    @Id
    @Column(name = "reservationId")
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private int reservationId;

    @Column(name = "dateFrom")
    @NotNull
    private String dateFrom;

    @Column(name = "dateTo")
    @NotNull
    private String dateTo;

    @Column(name = "reservationState")
    @NotNull
    private String reservationState;

    @Column(name = "paidone")
    @NotNull
    private String paidone;

    @Column(name="paymentMethod")
    @NotNull
    private String paymentMethod;

    @Column(name="details")
    @NotNull
    private String details;

    @Column(name="price")
    @NotNull
    private String price;

    @Column(name="paymentDate")
    @NotNull
    private String paymentDate;

    @JsonBackReference(value = "roomId-ref")
    @ManyToOne
    @JoinColumn(name="roomId")
    private Room roomId;

    @JsonBackReference(value = "userId-ref")
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;


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
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public Room getRoomId() {
        return roomId;
    }
    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }
    public User getUserId() {
        return userId;
    }
    public void setUserId(User userId) {
        this.userId = userId;
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
}
