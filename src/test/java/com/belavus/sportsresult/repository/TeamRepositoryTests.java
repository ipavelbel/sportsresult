package com.belavus.sportsresult.repository;

import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TeamRepositoryTests {


    private TeamRepository teamRepository;

    private EventRepository eventRepository;

    @Autowired
    public TeamRepositoryTests(TeamRepository teamRepository, EventRepository eventRepository) {
        this.teamRepository = teamRepository;
        this.eventRepository = eventRepository;
    }

//    @Test
//    public void testCreateTeam() {
//        Team team = new Team("name", "name");
//        teamRepository.save(team);
//
//    }
//
//    @Test
//    public void testAddTeamsInEvent() {
//        Event event = new Event("tur1", "city2");
//        Team team1 = new Team("Team1", "coach1");
//        Team team2 = new Team("Team2", "coach2");
//
//        event.setTeams(new LinkedHashSet<>(List.of(team1,team2)));
//        team1.setEvents(new LinkedHashSet<>(Collections.singletonList(event)));
//        team2.setEvents(new LinkedHashSet<>(Collections.singletonList(event)));
//        teamRepository.save(team2);
//
//    }
//
//    @Test
//    public void testAddTeamsInEventVersion2() {
//        Event event = new Event("Frendly", "Dubai");
//        Event event2 = new Event("secondTurnament", "Abu-dabi");
//        Team team1 = new Team("Achabat", "Levandowski");
//        Team team2 = new Team("Hot Paper", "Ship");
//        Team team3 = new Team("mad Dogs", "Holush");
//
//
//        event.getTeams().add(team1);
//        event.getTeams().add(team2);
//        event2.getTeams().add(team3);
//
//        team1.getEvents().add(event);
//        team2.getEvents().add(event);
//        team3.getEvents().add(event2);
//
//
//        eventRepository.save(event);
//        eventRepository.save(event2);
//    }

    @Test
    public void testRemoveTeamsInEvent() {
        Event event = new Event();
        Team team = new Team();
        team.getId();
        event.getTeams().remove(team);


//        teamRepository.deleteById(15);



    }




}
