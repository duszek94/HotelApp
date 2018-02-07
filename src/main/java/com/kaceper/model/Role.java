package com.kaceper.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="Role")
public class Role {

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    @Id
    @Column(name = "roleId")
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private int roleId;


    @Column(name = "role")
    @NotEmpty
    private String role;

    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + roleId +
                ", name='" + role + '\'' +
                '}';
    }
}