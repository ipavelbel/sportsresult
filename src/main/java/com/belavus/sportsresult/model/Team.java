package com.belavus.sportsresult.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "teams")
public class Team {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(message = "Name should be have from 1 to 50 characters", min = 1, max = 50)
    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String name;

    @Size(message = "Field should be have from 1 to 100 characters", min = 1, max = 100)
    @NotEmpty(message = "Coach name should not be empty")
    @Column(name = "coach")
    private String coach;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "teams_athletes",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "athletes_id", referencedColumnName = "id"))
    private Set<Athlete> athletes = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "teams", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Event> events = new LinkedHashSet<>();

    public Team() {
    }

    public Team(int id, String name, String coach) {
        this.id = id;
        this.name = name;
        this.coach = coach;
    }

    public Team(String name, String coach) {

        this.name = name;
        this.coach = coach;
    }

    public Team(int id, String name, String coach, Set<Athlete> athletes) {
        this.id = id;
        this.name = name;
        this.coach = coach;
        this.athletes = athletes;
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

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public void addEvents(Event eventsID) {
        events.add(eventsID);
        eventsID.getTeams().add(this);
    }

    public void removeEvents(Event eventsID) {
        events.remove(eventsID);
        eventsID.getTeams().remove(this);
    }

    public void addAthletes(Athlete athlete) {
        athletes.add(athlete);
        athlete.getTeams().add(this);
    }

    public void removeAthletes(Athlete athlete) {
        athletes.remove(athlete);
        athlete.setTeams(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}


