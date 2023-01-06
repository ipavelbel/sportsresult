package com.belavus.sportsresult.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not empty")
    @Size(min = 2, max = 100, message = "Name have to from 2 to 100 length  symbol") // TODO: n.kvetko grammar
    @Column(name = "username")
    private String username;

    @Min(value = 1900, message = " Year have to bigger then 1900") // TODO: n.kvetko: grammar
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @Size(min = 4, message = "Password have to bigger then 4 symbols")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    public Person() {
    }

    public Person(String username, int yearOfBirth) {
        this.username = username;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
