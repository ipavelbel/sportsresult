package com.belavus.sportsresult.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(message = "Name should be have from 1 to 50 characters", min = 1, max = 50)
    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String name;

    @Size(message = "City should be have from 1 to 50 characters", min = 1, max = 50)
    @NotEmpty(message = "City should not be empty")
    @Column(name = "place")
    private String place;

    @NotNull(message = "Date should not be empty")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_event")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dateOfEvent;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "events_teams",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"))
    private Set<Team> teams = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "events_athletes",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "athletes_id", referencedColumnName = "id"))
    private Set<Athlete> athletes = new LinkedHashSet<>();


    public Event() {
    }

    public Event(String name, String place) {
        this.name = name;
        this.place = place;
    }

    public Event(int id, String name, String place) {
        this.id = id;
        this.name = name;
        this.place = place;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }

    public Date getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(Date dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public void addAthlete(Athlete athlete) {
        athletes.add(athlete);
        athlete.getEvents().add(this);
    }

    public void removeAthlete(Athlete athlete) {
        athletes.remove(athlete);
        athlete.getEvents().remove(this);
    }

    public void addTeam(Team team) {
        teams.add(team);
        team.getEvents().add(this);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
        team.getEvents().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
