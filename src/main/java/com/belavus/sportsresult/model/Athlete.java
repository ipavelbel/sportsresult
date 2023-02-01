package com.belavus.sportsresult.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//import lombok.Data;
//import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "teams_athletes",
            joinColumns = @JoinColumn(name = "athletes_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private Set<Team> teams = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "athletes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Event> events = new LinkedHashSet<>();

    public Athlete() {
    }

    public Athlete(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
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

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        teams.add(team);
        team.getAthletes().add(this);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
        team.setAthletes(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Athlete)) return false;
        Athlete athlete = (Athlete) o;
        return Objects.equals(id, athlete.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
