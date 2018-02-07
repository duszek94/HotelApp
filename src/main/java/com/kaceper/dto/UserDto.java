package com.kaceper.dto;

import com.kaceper.model.User;

public class UserDto {
    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private int phone;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void wrap(User user){

        this.userId = user.getUserId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }

    public static UserDto make(User user){
        UserDto dto = new UserDto();
        dto.wrap(user);

        return dto;
    }
}
