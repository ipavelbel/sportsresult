package com.belavus.sportsresult.model;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "athletes")
public class Athlete {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(message = "Name should be have from 1 to 50 characters", min = 1, max = 50)
    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String name;

    @Size(message = "Surname should be have from 1 to 50 characters", min = 1, max = 50)
    @NotEmpty(message = "Surname should not be empty")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "Field cannot be empty")
    @Min(message = "Age should be greater than 0", value = 1)
    @Column(name = "age", nullable = false)
    private Integer age;

    @ManyToMany(mappedBy = "athletes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Team> teams = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "athletes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Event> events = new LinkedHashSet<>();

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Athlete() {
    }

    public Athlete(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Athlete(int id, String name, String surname, int age) {
        this.id = id;
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
        team.getAthletes().remove(this);
    }

    public void removeAthlete(Event event) {
        events.remove(event);
        event.getAthletes().remove(this);

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
