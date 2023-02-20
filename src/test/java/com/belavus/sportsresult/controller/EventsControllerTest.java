package com.belavus.sportsresult.controller;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.service.AthleteService;
import com.belavus.sportsresult.service.EventService;
import com.belavus.sportsresult.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class EventsControllerTest {


    @MockBean
    private EventService eventService;

    @MockBean
    private TeamService teamService;

    @MockBean
    private AthleteService athleteService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testFindAllEvents() throws Exception {
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        List<Event> events = Arrays.asList(event1, event2);
        when(eventService.findAllEvents()).thenReturn(events);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("event/event-list"))
                .andExpect(model().attribute("events", hasSize(2)));

        verify(eventService, times(1)).findAllEvents();
    }

    @Test
    void testCreateEventForm() throws Exception {

        mockMvc.perform(get("/events/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("event/event-create"))
                .andExpect(model().attributeExists("event"));
    }

    @Test
    void testCreateEvent() throws Exception {

        Event event = new Event("name", "City");

        mockMvc.perform(post("/events")
                        .param("name", event.getName())
                        .param("place", event.getPlace())
                        .param("dateOfEvent", "2022-02-20")
                        .flashAttr("event", new Event()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events/"));

        verify(eventService, times(1)).saveEvent(any(Event.class));

    }

    @Test
    void testCreateEventWhenValueOfFieldsNoValid() throws Exception {

        Event event_1 = new Event("", "");

        mockMvc.perform(post("/events")
                        .param("name", event_1.getName())
                        .param("place", event_1.getPlace())
                        .param("dateOfEvent", "2022/02/20")
                        .flashAttr("event", new Event()))
                .andExpect(status().isOk())
                .andExpect(view().name("event/event-create"));

        verify(eventService, times(0)).saveEvent(any(Event.class));

    }

    @Test
    void testEditEventForm() throws Exception {
        Event event = new Event("name", "City");
        event.setId(1);

        when(eventService.findOneEvent(anyInt())).thenReturn(event);

        mockMvc.perform(get("/events/{id}/edit", event.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("event"))
                .andExpect(view().name("event/event-update"));


    }

    @Test
    void testUpdateEvent() throws Exception {
        Event event = new Event("name", "City");
        event.setId(1);
        Event updatedEvent = new Event("NewName", "NewCity");

        RequestBuilder requestBuilder = patch("/events/{id}", event.getId())
                .param("name", updatedEvent.getName())
                .param("place", updatedEvent.getPlace())
                .param("dateOfEvent", "2022-02-20")
                .flashAttr("event", new Event());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events/"));

        verify(eventService, times(1)).updateEvent(anyInt(), any(Event.class));

    }

    @Test
    void testUpdateEventWhenValueOfFieldsNoValid() throws Exception {
        Event event = new Event("name", "City");
        event.setId(1);
        Event updatedEvent = new Event("", "");

        RequestBuilder requestBuilder = patch("/events/{id}", event.getId())
                .param("name", updatedEvent.getName())
                .param("place", updatedEvent.getPlace())
                .param("dateOfEvent", "2022.02.20")
                .flashAttr("event", new Event());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("event/event-update"));

        verify(eventService, times(0)).updateEvent(anyInt(), any(Event.class));

    }

    @Test
    void testDeleteEvent() throws Exception {
        Event event = new Event("name", "City");
        event.setId(1);

        mockMvc.perform(delete("/events/{id}", event.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events/"));

        verify(eventService, times(1)).deleteEventById(anyInt());

    }

    @Test
    void testShowOneEvent() throws Exception {
        Event event = new Event("name", "City");
        event.setId(1);
        Team team1 = mock(Team.class);
        Team team2 = mock(Team.class);
        Set<Team> teams = Set.of(team1, team2);
        List<Team> teamList = Arrays.asList(team1, team2);
        Athlete athlete = mock(Athlete.class);
        List<Athlete> athleteList = Collections.singletonList(athlete);
        Set<Athlete> athletes = Set.of(athlete);

        when(eventService.findOneEvent(anyInt())).thenReturn(event);
        when(eventService.getAthletesByEventId(anyInt())).thenReturn(athletes);
        when(eventService.getTeamsByEventId(anyInt())).thenReturn(teams);
        when(teamService.findAllTeams()).thenReturn(teamList);
        when(athleteService.findAllAthletes()).thenReturn(athleteList);


        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("event/show"))
                .andExpect(model().attributeExists("event"))
                .andExpect(model().attributeExists("teams"))
                .andExpect(model().attributeExists("teamsInEvent"))
                .andExpect(model().attributeExists("athletes"))
                .andExpect(model().attributeExists("athletesInEvents"));


        verify(eventService, times(1)).findOneEvent(any());
        verify(eventService, times(1)).getTeamsByEventId(any());
        verify(eventService, times(1)).getAthletesByEventId(any());
        verify(teamService, times(1)).findAllTeams();
        verify(athleteService, times(1)).findAllAthletes();
    }

    @Test
    void testAddAthleteToEvent() throws Exception {
        Athlete athlete = new Athlete("name", "surname", 34);
        athlete.setId(2);
        Event event = new Event();
        event.setId(3);

        mockMvc.perform(patch("/events/{id}/assignAthlete", event.getId())
                        .param("id", String.valueOf(athlete.getId()))
                        .param("name", athlete.getName())
                        .param("surname", athlete.getSurname())
                        .flashAttr("athleteId", athlete))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events/" + event.getId()));

        verify(eventService, times(1)).assignAthleteToEvent(anyInt(), any(Athlete.class));



    }

    @Test
    void testReleaseAthleteFromEvent() throws Exception {

        Athlete athlete = new Athlete();
        athlete.setId(1);
        Event event = new Event();
        event.setId(2);


        mockMvc.perform(patch("/events/{id}/{athleteId}/releaseAthlete", event.getId(), athlete.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events/" + event.getId()));

        verify(eventService, times(1)).releaseAthleteFromEvent(anyInt(), anyInt());


    }

    @Test
    void testAddTeamToEvent() throws Exception {
        Team team = new Team();
        team.setId(1);
        Event event = new Event();
        event.setId(3);

        mockMvc.perform(patch("/events/{id}/assignTeam", event.getId())
                        .param("id", String.valueOf(team.getId()))
                        .param("name", team.getName())
                        .param("coach", team.getCoach())
                        .flashAttr("athleteId", team))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events/" + event.getId()));

        verify(eventService, times(1)).assignTeamToEvent(anyInt(), any(Team.class));


    }

    @Test
    void testReleaseTeamFromEvent() throws Exception {
        Team team = new Team();
        team.setId(1);
        Event event = new Event();
        event.setId(2);

        mockMvc.perform(patch("/events/{id}/{teamId}/releaseTeam", event.getId(), team.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/events/" + event.getId()));

        verify(eventService, times(1)).releaseTeamFromEvent(anyInt(), anyInt());

    }
}