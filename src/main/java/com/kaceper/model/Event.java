package com.kaceper.model;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Event")
public class Event {

    @Id
    @Column(name = "eventId")
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private int eventId;

    @Column(name = "eventDate")
    @NotEmpty
    private String eventDate;

    @Column(name = "tittle")
    @NotEmpty
    private String tittle;

    @Column(name = "details")
    @NotEmpty
    private String details;

    @Column(name = "countOfGuests")
    @NotNull
    private int countOfGuests;

    @Column(name = "price")
    @NotNull
    private int price;

    @Column(name = "firstname")
    @NotEmpty
    private String firstname;

    @Column(name = "lastname")
    @NotEmpty
    private String lastname;

    @Column(name = "phone")
    @NotNull
    private int phone;

    @Column(name = "paymentMethod")
    @NotEmpty
    private String paymentMethod;



    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    public String getTittle() {
        return tittle;
    }
    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public int getCountOfGuests() {
        return countOfGuests;
    }
    public void setCountOfGuests(int countOfGuests) {
        this.countOfGuests = countOfGuests;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
