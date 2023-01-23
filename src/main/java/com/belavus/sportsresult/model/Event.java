package com.belavus.sportsresult.model;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "place")
    private String place;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "events_teams",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"))
    private Set<Team> teams = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
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

//    public void addTeam(Team team) {
//        this.teamsInEvent.add(team);
//        team.getEvents().add(this);
//    }
//
//    public void removeTeams(int teamId) {
//        Team team = this.teamsInEvent.stream().filter(t -> t.getId() == teamId).findFirst().orElse(null);
//        if (team != null) {
//            this.teamsInEvent.remove(team);
//            team.getEvents().remove(this);
//        }
//    }
}
