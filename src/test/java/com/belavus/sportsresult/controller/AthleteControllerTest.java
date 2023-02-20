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
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AthleteControllerTest {
    @MockBean
    private EventService eventService;

    @MockBean
    private TeamService teamService;

    @MockBean
    private AthleteService athleteService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testFindAllAthletes() throws Exception {
        // Give
        Athlete athlete1 = new Athlete(1, "Name", "Surname", 25);
        Athlete athlete2 = new Athlete(2, "Name", "Surname", 25);
        List<Athlete> athletes = Arrays.asList(athlete1, athlete2);
        when(athleteService.findAllAthletes()).thenReturn(athletes);

        // When
        mockMvc.perform(get("/athletes"))
                .andExpect(status().isOk())
                .andExpect(view().name("athlete/athlete-list"))
                .andExpect(model().attribute("athletes", hasSize(2)));

        // Then
        verify(athleteService, times(1)).findAllAthletes();
    }

    @Test
    void testCreateAthleteForm() throws Exception {

        mockMvc.perform(get("/athletes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("athlete/athlete-create"))
                .andExpect(model().attributeExists("athlete"));


    }

    @Test
    void testCreateAthlete() throws Exception {

        Athlete athlete = new Athlete(1, "name", "surname",1);

        mockMvc.perform(post("/athletes")
                        .param("name", athlete.getName())
                        .param("surname", athlete.getSurname())
                        .param("age", String.valueOf(athlete.getAge()))
                        .flashAttr("athlete", new Athlete()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/athletes/"));

        verify(athleteService, times(1)).saveAthlete(any(Athlete.class));
    }

    @Test
    void testCreateAthleteWhenValueOfFieldsNoValid() throws Exception {

        Athlete athlete = new Athlete(1, "", "",0);

        mockMvc.perform(post("/athletes")
                        .param("name", athlete.getName())
                        .param("surname", athlete.getSurname())
                        .param("age", String.valueOf(athlete.getAge()))
                        .flashAttr("athlete", new Athlete()))
                .andExpect(status().isOk())
                .andExpect(view().name("athlete/athlete-create"));

        verify(athleteService, times(0)).saveAthlete(any(Athlete.class));
    }

    @Test
    void testEditAthleteForm() throws Exception {

        Athlete athlete = new Athlete(1, "name", "surname", 34);

        when(athleteService.findOneAthlete(anyInt())).thenReturn(athlete);

        mockMvc.perform(get("/athletes/{id}/edit", athlete.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("athlete"))
                .andExpect(view().name("athlete/athlete-update"));

    }

    @Test
    void testUpdateAthleteWhenValueOfFieldsNoValid() throws Exception {

        Athlete athlete = new Athlete(1, "name", "surname", 34);
        Athlete athlete1 = new Athlete(2, "name2", "surname2", 0);

//        doNothing().when(athleteService).update(athlete.getId(), athlete1);

        RequestBuilder requestBuilder = patch("/athletes/{id}", athlete.getId())
                .param("name", athlete1.getName())
                .param("surname", athlete1.getSurname())
                .param("age", String.valueOf(athlete1.getAge()))
                .flashAttr("athlete", new Athlete());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("athlete/athlete-update"));

        verify(athleteService, times(0)).updateAthlete(anyInt(),any(Athlete.class));

    }

    @Test
    void testUpdateAthlete() throws Exception {

        Athlete athlete = new Athlete(1, "name", "surname", 34);
        Athlete athlete1 = new Athlete(2, "name2", "surname2", 35);

//        doNothing().when(athleteService).update(athlete.getId(), athlete1);

        RequestBuilder requestBuilder = patch("/athletes/{id}", athlete.getId())
                .param("name", athlete1.getName())
                .param("surname", athlete1.getSurname())
                .param("age", String.valueOf(athlete1.getAge()))
                .flashAttr("athlete", new Athlete());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/athletes/"));

        verify(athleteService, times(1)).updateAthlete(anyInt(),any(Athlete.class));

    }



    @Test
    void testDeleteAthlete() throws Exception {
        Athlete athlete = new Athlete(1, "name", "surname", 34);

        mockMvc.perform(delete("/athletes/{id}", athlete.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/athletes/"));

        verify(athleteService, times(1)).deleteAthleteById(anyInt());
    }

    @Test
    void testShowOneAthlete() throws Exception {

        Athlete athlete = new Athlete(1, "name", "surname", 34);
        Team team1 = mock(Team.class);
        Team team2 = mock(Team.class);
        Set<Team> teams = Set.of(team1, team2);
        List<Team> teamList = Arrays.asList(team1, team2);
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        Set<Event> events = Set.of(event1, event2);

        when(athleteService.findOneAthlete(any())).thenReturn(athlete);
        when(athleteService.getTeamsByAthleteId(any())).thenReturn(teams);
        when(teamService.findAllTeams()).thenReturn(teamList);
        when(eventService.getEventsByAthleteId(any())).thenReturn(events);

        mockMvc.perform(get("/athletes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("athlete/showAthlete"))
                .andExpect(model().attributeExists("athlete"))
                .andExpect(model().attributeExists("teamsInAthlete"))
                .andExpect(model().attributeExists("teams"))
                .andExpect(model().attributeExists("eventsInAthlete"));

        verify(athleteService, times(1)).findOneAthlete(any());
        verify(athleteService, times(1)).getTeamsByAthleteId(any());
        verify(teamService, times(1)).findAllTeams();
        verify(eventService, times(1)).getEventsByAthleteId(any());
    }

    @Test
    void testAddAthleteToTeam() throws Exception {

        Athlete athlete = new Athlete(1, "name", "surname", 34);
        Team team = new Team();
        team.setId(3);

//        doNothing().when(athleteService).assignTeam(anyInt(), any(Team.class));

        mockMvc.perform(patch("/athletes//{id}/assign", athlete.getId())
                        .param("id", String.valueOf(team.getId()))
                        .param("name", team.getName())
                        .param("coach", team.getCoach())
                        .flashAttr("teamId", team))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/athletes/" + athlete.getId()));

        verify(athleteService, times(1)).assignTeamToAthlete(anyInt(), any(Team.class));


    }

    @Test
    void testReleaseAthleteFromTeam() throws Exception {

        Athlete athlete = new Athlete(1, "name", "surname", 34);
        Team team = new Team();
        team.setId(2);

        doNothing().when(athleteService).releaseAthleteFromTeam(anyInt(), anyInt());


        mockMvc.perform(patch("/athletes/{id}/{teamId}/releaseAthlete", athlete.getId(), team.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/athletes/" + athlete.getId()));

        verify(athleteService, times(1)).releaseAthleteFromTeam(anyInt(), anyInt());
    }

    @Test
    void testReleaseAthleteFromEvent() throws Exception {

        Athlete athlete = new Athlete(1, "name", "surname", 34);
        Event event = new Event();
        event.setId(2);

        doNothing().when(athleteService).releaseAthleteFromEvent(anyInt(), anyInt());


        mockMvc.perform(patch("/athletes/{id}/{eventId}/releaseEvent", athlete.getId(), event.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/athletes/" + athlete.getId()));

        verify(athleteService, times(1)).releaseAthleteFromEvent(anyInt(), anyInt());
    }
}