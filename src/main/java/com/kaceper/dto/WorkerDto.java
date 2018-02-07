package com.kaceper.dto;

import com.kaceper.model.Role;
import com.kaceper.model.Worker;

import java.util.*;

public class WorkerDto {
    private int workerId;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String adress;
    private Integer phone;
    private Boolean enabled;
    private String confirmationToken;
    private Collection<Role> roles;


    public Collection<Role> getRoles() {
        return roles;
    }
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getWorkerId() {
        return workerId;
    }
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public String getConfirmationToken() {
        return confirmationToken;
    }
    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }


    public void wrap(Worker worker){
        this.adress = worker.getAdress();
        this.confirmationToken = worker.getConfirmationToken();
        this.enabled = worker.isEnabled();
        this.firstname = worker.getFirstname();
        this.lastname = worker.getLastname();
        this.password = worker.getPassword();
        this.phone = worker.getPhone();
        this.workerId = worker.getWorkerId();
        this.username = worker.getUsername();
        this.email = worker.getEmail();
        this.roles = worker.getRoles();
    }

    public static WorkerDto make(Worker worker){
        WorkerDto dto = new WorkerDto();
        dto.wrap(worker);

        return dto;
    }
}
