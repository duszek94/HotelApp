package com.kaceper.dto;

import com.kaceper.model.Event;

public class EventDto {
    private int eventId;
    private String eventDate;
    private String tittle;
    private String details;
    private int countOfGuests;
    private int price;
    private String firstname;
    private String lastname;
    private int phone;
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

    public void wrap(Event event){

        this.eventId = event.getEventId();
        this.eventDate = event.getEventDate();
        this.tittle = event.getTittle();
        this.details = event.getDetails();
        this.countOfGuests = event.getCountOfGuests();
        this.price = event.getPrice();
        this.firstname = event.getFirstname();
        this.lastname = event.getLastname();
        this.phone = event.getPhone();
        this.paymentMethod = event.getPaymentMethod();
    }

    public static EventDto make(Event event){
        EventDto dto = new EventDto();
        dto.wrap(event);

        return dto;
    }
}
