package com.belavus.sportsresult.model;

import javax.persistence.*;
import lombok.Data;


//@Data // TODO: n.kvetko: ???
@Entity
@Table(name = "athletes")
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id") // TODO: n.kvetko: perform code formatting
    private Team oneTeam;

    public Athlete() {
    }

    public Athlete(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getOneTeam() {
        return oneTeam;
    }

    public void setOneTeam(Team oneTeam) {
        this.oneTeam = oneTeam;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", oneTeam=" + oneTeam +
                '}';
    }
}
