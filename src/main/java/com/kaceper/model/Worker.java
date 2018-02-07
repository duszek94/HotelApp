package com.kaceper.model;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name="Worker")
public class Worker implements UserDetails {

    public Worker() {
    }

    @Id
    @Column(name = "workerId")
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private int workerId;

    @Column(name = "login")
    @NotEmpty
    private String username;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "firstname")
    @NotEmpty
    private String firstname;

    @Column(name = "lastname")
    @NotEmpty
    private String lastname;

    @Column(name = "adress")
    @NotEmpty
    private String adress;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotEmpty
    private String email;

    @Column(name = "phone")
    @NotNull
    private Integer phone;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "token")
    private String confirmationToken;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "WorkersRoles",
            joinColumns = @JoinColumn(
                    name = "workerId", referencedColumnName = "workerId"),
            inverseJoinColumns = @JoinColumn(
                    name = "roleId", referencedColumnName = "roleId"))
    private Collection<Role> roles;



    public Collection<Role> getRoles() {
        return roles;
    }
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    public int getWorkerId() {
        return workerId;
    }
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
    public void setEnabled(boolean value) {
        this.enabled = value;
    }
    public String getConfirmationToken() { return confirmationToken; }
    public void setConfirmationToken(String confirmationToken) { this.confirmationToken = confirmationToken; }
    public int getUserId(){ return workerId; }
    private void serUserId(int userId) { this.workerId = userId; }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public Integer getPhone() {
        return phone;
    }
    public void setPhone(Integer phone) {
        this.phone = phone;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
