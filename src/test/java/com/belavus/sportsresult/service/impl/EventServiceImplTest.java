package com.belavus.sportsresult.service.impl;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.EventRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {


    @Mock
    EventRepository eventRepository;

    @Mock
    AthleteRepository athleteRepository;

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    EventServiceImpl eventService;


    @Test
    void testFindAllEvents() {

        // Given
        Event event1 = new Event(3, "Tournament", "City1");
        Event event2 = new Event(2, "Tournament", "City2");
        List<Event> events = Arrays.asList(event1, event2);
        when(eventRepository.findAll()).thenReturn(events);

        // When
        List<Event> eventList = eventService.findAll();

        // Then
        assertNotNull(eventList);
        assertEquals(2, eventList.size());
        assertTrue(events.size() == eventList.size() && events.containsAll(eventList) && eventList.containsAll(events) );
        verify(eventRepository, times(1)).findAll();

    }

    @Test
    void testSave() {

        // Given
        Event event = new Event(1, "Tournament", "City");

        // When
        eventService.save(event);

        // Then
        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(argumentCaptor.capture());

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
    void testFindOneEvent() {

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
    void testFindOneWhenEventNotFound() {
        // Given,When,Then
        assertThrows(EntityNotFoundException.class,()->eventService.findOne(2));
    }



    @Test
    void testGetTeamsByEventId() {

        // Given
        Event event = new Event(2, "name", "place");
        Team team1 = new Team(1, "Name1", "Coach1");
        Team team2 = new Team(2, "Name2", "Coach2");
        event.addTeam(team1);
        event.addTeam(team2);
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));

        // When
        eventService.getTeamsByEventId(2);

        // Then
        verify(eventRepository).findById(anyInt());

    }

    @Test
    void testGetTeamsByEventIdEventNotFound() {
        // Given,When,Then
        assertThrows(EntityNotFoundException.class,()->eventService.getTeamsByEventId(2));
    }

    @Test
    void testUpdate() {

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
    void testAssignAthlete() {

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
    void testGetAthletesByEventId() {

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
    void testGetAthletesByEventIdEventNotFound() {
        // Given,When,Then
        assertThrows(EntityNotFoundException.class,()->eventService.getAthletesByEventId(2));
    }

    @Test
    void testReleaseAthlete() {

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
    void testAssignTeam() {

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
    void testReleaseTeam() {

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


    @Test
    void testGetEventsByAthleteId() {

        // Given
        Event event = new Event(2, "name", "place");
        Athlete athlete = mock(Athlete.class);
        event.addAthlete(athlete);
        eventRepository.save(event);

        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));

        // When
        eventService.getAthletesByEventId(2);
        Set<Athlete> athleteSet= event.getAthletes();
        // Then
        verify(eventRepository).findById(anyInt());
        assertEquals(1,athleteSet.size());
    }

    @Test
    void testGetEventsByAthleteIdEventNotFound() {
        // Given,When,Then
        assertThrows(EntityNotFoundException.class,()->eventService.getEventsByAthleteId(2));
    }

}