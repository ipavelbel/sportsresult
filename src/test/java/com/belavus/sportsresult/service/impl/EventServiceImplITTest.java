package com.belavus.sportsresult.service.impl;

import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.EventRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class EventServiceImplITTest {

    @Autowired
    EventServiceImpl eventService;
    @Autowired
    EventRepository eventRepository;

    @Autowired
    TeamRepository teamRepository;


    @Test
    void findAll() {

        // Given
        Event event = eventRepository.save(new Event("Event", "City"));
        Event event1 = eventRepository.save(new Event("Event1", "City2"));
        List<Event> events = Arrays.asList(event, event1);

        // When
        List <Event> eventsList = eventService.findAll();

        // Then
        assertEquals(2, eventsList.size());
        assertEquals(events,eventsList);
        then(event.getId()).isNotNull();
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {

//        // Given
//        Event event = eventRepository.save(new Event("name", "city"));
//        Event event1 = eventRepository.save(new Event("Event1", "City2"));
//        List<Event> eventList = Arrays.asList(event, event1);
//
//
//        // When
//        eventService.deleteById(event.getId());
//
//        assertEquals(2, eventsList.size());


    }

    @Test
    void findOne() {

        // Given
        Event event = eventRepository.save(new Event("name", "city"));

        // When
        Event eventExpected = eventService.findOne(event.getId());

        // Then
        assertEquals(eventExpected, event);

    }

    @Test
    void findOneNotFound() {

        // Given
        int id = 3;

        // When, Then
        assertThrows(EntityNotFoundException.class,()->eventService.findOne(2));

    }

    @Test
    void getTeamsByEventId() {
        // Given
        Team team1 = teamRepository.save(new Team("name1", "CoachName1"));
        Team team2 = teamRepository.save(new Team("name2", "CoachName2"));
        Event event = new Event("Name", "City");
        event.addTeam(team1);
        event.addTeam(team2);
        eventRepository.save(event);

        // When
        Event foundEvent = eventService.findOne(event.getId());
        Set<Team> teamSet = foundEvent.getTeams();

        // Then
        assertEquals(2, teamSet.size());
    }

    @Test
    void update() {
    }

    @Test
    void assignAthlete() {
    }

    @Test
    void getAthletesByEventId() {
    }

    @Test
    void releaseAthlete() {
    }

    @Test
    void assignTeam() {
    }

    @Test
    void releaseTeam() {
    }

    @Test
    void getEventsByAthleteId() {
    }
}