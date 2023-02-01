package com.belavus.sportsresult.service;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.EventRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {


    @Mock
    EventRepository eventRepository;

    @Mock
    AthleteRepository athleteRepository;

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    EventService eventService;


    @Test
    void findAll() {

        // Given
        Event event1 = new Event(3, "Turnament", "Grodno");
        Event event2 = new Event(2, "Turnament", "Grodno");
        List<Event> events = Arrays.asList(event1, event2);
        when(eventRepository.findAll()).thenReturn(events);

        // When
        List<Event> eventList = eventService.findAll();

        // Then
        assertNotNull(eventList);
        assertEquals(2, eventList.size());
//        assertArrayEquals(Arrays.asList(event2,event1), );
        verify(eventRepository, times(1)).findAll();

    }

    @Test
    void save() {

        // Given
        Event event = new Event(1, "Tournament", "City");

        // When
        eventService.save(event);

        // Then
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(argumentCaptor.capture());

    }

    @Test
    void findOne() {

        // Given
        Event event = new Event(1, "Tournament", "City");
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));

        // When
        Event actualEvent = eventService.findOne(1);

        // Then
        assertNotNull(actualEvent);
        assertEquals(event,actualEvent);
    }

    @Test
    void findOneWhenNotFound() {
        assertThrows(EntityNotFoundException.class,()->eventService.findOne(2));
//        when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());
//
//        Event event = eventService.findOne(1);
//
//        assertNull(event);
    }

    @Test
    void testDeleteById() {

        // Given
        Event event = new Event(1, "Tournament", "City");

        // When
        eventService.deleteById(event.getId());

        // Then
        verify(eventRepository).deleteById(anyInt());
    }

    @Test
    void getTeamsByEventId() {

        // Given
        Event event = new Event(2, "name", "place");
        Team team1 = new Team(1, "Name1", "Coach1");
        Team team2 = new Team(2, "Name2", "Coach2");
        event.addTeam(team1);
        event.addTeam(team2);
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));

        // When
        eventService.getAthletesByEventId(2);

        // Then
        verify(eventRepository).findById(anyInt());

    }

    @Test
    void update() {

        // Given
        Event event = new Event(2,"New tournament", "New Place");
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
        Event updatedEventForSave = new Event("Update tournament", "Update Place");
        when(eventRepository.save(any())).thenReturn(event);

        // When
        eventService.update(event.getId(), updatedEventForSave);

        // Then
        ArgumentCaptor <Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).findById(event.getId());
        verify(eventRepository).save(argumentCaptor.capture());
        Event captureValue = argumentCaptor.getValue();
        assertEquals(event,captureValue);

    }

    @Test
    void assignAthlete() {

        // Given
        Event event = new Event(2, "newTournament", "newPlace");
        Athlete athlete1 = new Athlete(1, "Name", "Surname", 32);
        Athlete athlete2 = new Athlete(2, "Name", "Surname", 32);
        event.addAthlete(athlete1);
        event.addAthlete(athlete2);
        Athlete athlete = new Athlete();
        athlete.setId(4);
        when(eventRepository.findEventWithAthletesById(anyInt())).thenReturn(Optional.of(event));
        when(athleteRepository.findById(anyInt())).thenReturn(Optional.of(athlete));
        when(eventRepository.save(event)).thenReturn(event);

        // When
        eventService.assignAthlete(event.getId(), athlete);

        // Then
        verify(eventRepository).findEventWithAthletesById(2);
        verify(athleteRepository).findById(4);
        verify(eventRepository).save(event);

    }

    @Test
    void getAthletesByEventId() {

        // Given
        Event event = new Event(2, "name", "place");
        Athlete athlete1 = new Athlete(1, "Name1", "Surname1", 32);
        Athlete athlete2 = new Athlete(2, "Name2", "Surname2", 33);
        event.addAthlete(athlete1);
        event.addAthlete(athlete2);

        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));

        // When
        eventService.getAthletesByEventId(event.getId());

        // Then
        verify(eventRepository).findById(anyInt());

    }

    @Test
    void releaseAthlete() {
        // Give
        Event event = new Event(2, "newTournament", "newPlace");
        Athlete athlete = new Athlete(1, "Name", "Surname", 23);
        event.addAthlete(athlete);

        when(eventRepository.findEventWithAthletesById(anyInt())).thenReturn(Optional.of(event));
        when(eventRepository.save(any())).thenReturn(event);

        // When
        eventService.releaseAthlete(event.getId(),athlete.getId());

        // Then
        verify(eventRepository).findEventWithAthletesById(anyInt());
        verify(eventRepository).save(any());
    }

    @Test
    void assignTeam() {

        // Give
        Event event = new Event(2, "newTournament", "newPlace");
        Team teamForAdd = new Team(3, "Team3", "Coach3");

        when(eventRepository.findEventWithTeamsById(anyInt())).thenReturn(Optional.of(event));
        when(teamRepository.findById(anyInt())).thenReturn(Optional.of(teamForAdd));
        when(eventRepository.save(event)).thenReturn(event);

        // When
        eventService.assignTeam(2, teamForAdd);

        // Then
        verify(eventRepository).findEventWithTeamsById(anyInt());
        verify(teamRepository).findById(anyInt());
        verify(eventRepository).save(any());

    }

    @Test
    void releaseTeam() {

        // Give
        Event event = new Event(2, "newTournament", "newPlace");
        Team team1 = new Team(2, "Team2", "Coach2");
        event.addTeam(team1);

        when(eventRepository.findEventWithTeamsById(anyInt())).thenReturn(Optional.of(event));
        when(eventRepository.save(any())).thenReturn(event);

        // When
        eventService.releaseTeam(event.getId(),team1.getId());

        // Then
        verify(eventRepository).findEventWithTeamsById(anyInt());
        verify(eventRepository).save(any());

    }
}