package com.kaceper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaceper on 2017-11-26.
 */

@Entity
@Table(name="User")
public class User {

    public User(){}

    public User(int userId){
        this.userId = userId;
    }

    @Id
    @Column(name = "userId")
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private int userId;

    @Column(name = "firstname")
    @NotEmpty(message = "Proszę podać imię")
    private String firstname;

    @Column(name = "lastname")
    @NotEmpty(message = "Proszę podać nazwisko")
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Proszę podać poprawny e-mail")
    @NotEmpty(message = "Proszę podać e-mail")
    private String email;

    @JsonManagedReference(value = "userId-ref")
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<Reservation> reservationList = new ArrayList<>();

    @Column(name = "phone")
    @NotNull
    private int phone;


    public void setUserId(int userId) {
        this.userId = userId;
    }
    public List<Reservation> getReservationList() {
        return reservationList;
    }
    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
    public int getUserId(){ return userId; }
    private void serUserId(int userId) { this.userId = userId; }
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
}