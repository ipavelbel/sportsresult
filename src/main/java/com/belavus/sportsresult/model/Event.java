package com.belavus.sportsresult.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


//@Data // TODO: n.kvetko: ???
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

    @ManyToMany(mappedBy = "events", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Team> teams = new LinkedHashSet<>();


//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE})
//    @JoinTable(
//            name = "events_teams",
//            joinColumns = @JoinColumn(name = "event_id"),
//            inverseJoinColumns = @JoinColumn(name = "team_id"))
//    private Set<Team> teams = new HashSet<>();

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


//    public Set<Team> getTeams() {
//        return teams;
//    }
//
//    public void setTeams(Set<Team> teams) {
//        this.teams = teams;
//    }
//    public void addTeam(Team team) {
//        this.teams.add(team);
//        team.getEvents().add(this);
//    }
//
//    public void removeTeams(int teamId) {
//        Team team = this.teams.stream().filter(t -> t.getId() == teamId).findFirst().orElse(null);
//        if (team != null) {
//            this.teams.remove(team);
//            team.getEvents().remove(this);
//        }
//    }
}
