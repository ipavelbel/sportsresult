package com.belavus.sportsresult.model;

import javax.persistence.*;

import lombok.Data;

import java.util.List;


//@Data
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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE},
            mappedBy = "teams")
    private List<Event> events;

        @OneToMany(mappedBy = "oneTeam")
    private List<Athlete> someAthletes;


    public Team() {
    }

    public Team( String name, String coach, List<Event> events) {

        this.name = name;
        this.coach = coach;
        this.events = events;
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}



// TODO: n.kvetko: should be eventId (don't forget to change this as well where it is used)
// TODO: n.kvetko perform code formatting
