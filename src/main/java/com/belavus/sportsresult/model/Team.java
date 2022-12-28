package com.belavus.sportsresult.model;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
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

    @ManyToOne
    @JoinColumn(name = "event_id",referencedColumnName = "id")
    private Event eventID;

    @OneToMany(mappedBy = "oneTeam")
    private List<Athlete> someAthletes;


}
