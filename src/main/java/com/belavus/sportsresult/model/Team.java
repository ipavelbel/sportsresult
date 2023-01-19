package com.belavus.sportsresult.model;

import javax.persistence.*;

import lombok.Data;

import java.util.*;



@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "coach")
    private String coach;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE},
//            mappedBy = "teams")
//    private Set<Event> events = new HashSet<Event>();

    @ManyToMany(mappedBy = "teams", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Athlete> athletes = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "events_teams",
            joinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private Set<Event> events = new LinkedHashSet<>();

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }


    public Team() {
    }

    public Team( String name, String coach) {

        this.name = name;
        this.coach = coach;
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

    public void addEvents(Event eventsID) {
        events.add(eventsID);
        eventsID.getTeams().add(this);
    }

    public void removeEvents(Event eventsID) {
        events.remove(eventsID);
        eventsID.getTeams().remove(this);
    }

    public void addSomeAthletes(Athlete athlete) {
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
    //    public Set<Event> getEvents() {
//        return events;
//    }
//
//    public void setEvents(Set<Event> events) {
//        this.events = events;
//    }

//    public List<Athlete> getSomeAthletes() {
//        return someAthletes;
//    }
//
//    public void setSomeAthletes(List<Athlete> someAthletes) {
//        this.someAthletes = someAthletes;
//    }




}



// TODO: n.kvetko: should be eventId (don't forget to change this as well where it is used)
// TODO: n.kvetko perform code formatting
