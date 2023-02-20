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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TeamControllerTest {

    @MockBean
    private EventService eventService;

    @MockBean
    private TeamService teamService;

    @MockBean
    private AthleteService athleteService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindAllTeam() throws Exception {
        List<Team> teams = Arrays.asList(mock(Team.class), mock(Team.class));
        when(teamService.findAllTeams()).thenReturn(teams);

        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/team-list"))
                .andExpect(model().attribute("teams", hasSize(2)));

        verify(teamService, times(1)).findAllTeams();
    }

    @Test
    void testCreateTeamForm() throws Exception {

        mockMvc.perform(get("/teams/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("team/team-create"))
                .andExpect(model().attributeExists("team"));
    }

    @Test
    void testCreateTeam() throws Exception {

        Team team = new Team("name", "trainer");

        mockMvc.perform(post("/teams")
                        .param("name", team.getName())
                        .param("coach", team.getCoach())
                        .flashAttr("team", new Team()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teams/"));

        verify(teamService, times(1)).saveTeam(any(Team.class));

    }

    @Test
    void testCreateTeamWhenValueOfFieldsNoValid() throws Exception {

        Team team = new Team("name", "");

        mockMvc.perform(post("/teams")
                        .param("name", team.getName())
                        .param("coach", team.getCoach())
                        .flashAttr("team", new Team()))
                .andExpect(status().isOk())
                .andExpect(view().name("team/team-create"));

        verify(teamService, times(0)).saveTeam(any(Team.class));

    }

    @Test
    void testEditTeamForm() throws Exception {

        Team team = new Team("name", "trainer");
        team.setId(1);

        when(teamService.findOneTeam(anyInt())).thenReturn(team);

        mockMvc.perform(get("/teams/{id}/edit", team.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("team"))
                .andExpect(view().name("team/team-update"));

    }

    @Test
    void testUpdateTeam() throws Exception {
        Team team = new Team("Name", "Surname");
        team.setId(2);
        Team updatedEvent = new Team("NewName", "NewSurname");

        RequestBuilder requestBuilder = patch("/teams/{id}", team.getId())
                .param("name", updatedEvent.getName())
                .param("coach", updatedEvent.getCoach())
                .flashAttr("event", new Team());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teams/"));

        verify(teamService, times(1)).updateTeam(anyInt(), any(Team.class));
    }

    @Test
    void testUpdateTeamWhenValueOfFieldsNoValid() throws Exception {
        Team team = new Team("Name", "Surname");
        team.setId(2);
        Team updatedEvent = new Team("NewName", "");

        RequestBuilder requestBuilder = patch("/teams/{id}", team.getId())
                .param("name", updatedEvent.getName())
                .param("coach", updatedEvent.getCoach())
                .flashAttr("event", new Team());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("team/team-update"));

        verify(teamService, times(0)).updateTeam(anyInt(), any(Team.class));
    }

    @Test
    void testDeleteTeam() throws Exception {
        Team team = new Team("Name", "Surname");
        team.setId(1);

        mockMvc.perform(delete("/teams/{id}", team.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teams/"));

        verify(teamService, times(1)).deleteTeamById(anyInt());
    }

    @Test
    void testShowOneTeam() throws Exception {
        Team team = new Team("Name", "Surname");
        team.setId(2);
        Set<Event> eventSet = Set.of(mock(Event.class));
        Set<Athlete> athletes = Set.of(mock(Athlete.class));
        List<Event> eventList = Arrays.asList(mock(Event.class),mock(Event.class));
        List<Athlete> athleteList = Arrays.asList(mock(Athlete.class),mock(Athlete.class));

        when(teamService.findOneTeam(anyInt())).thenReturn(team);
        when(teamService.getEventsByTeamId(anyInt())).thenReturn(eventSet);
        when(teamService.getAthletesByTeamId(anyInt())).thenReturn(athletes);
        when(eventService.findAllEvents()).thenReturn(eventList);
        when(athleteService.findAllAthletes()).thenReturn(athleteList);

        mockMvc.perform(get("/teams/{id}", team.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("team/showTeam"))
                .andExpect(model().attributeExists("team"))
                .andExpect(model().attributeExists("teamsInEvent"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("athletes"))
                .andExpect(model().attributeExists("athletesInTeams"));

        verify(teamService, times(1)).findOneTeam(any());
        verify(teamService, times(1)).getEventsByTeamId(any());
        verify(teamService, times(1)).getAthletesByTeamId(any());
        verify(eventService, times(1)).findAllEvents();
        verify(athleteService, times(1)).findAllAthletes();
    }

    @Test
    void testAddAthleteToTeam() throws Exception {
        Athlete athlete = new Athlete("Name", "Surname",15);
        athlete.setId(2);
        Team team = new Team();
        team.setId(1);

        mockMvc.perform(patch("/teams/{id}/assignAthlete", team.getId())
                        .param("id", String.valueOf(athlete.getId()))
                        .param("name", athlete.getName())
                        .param("surname", athlete.getSurname())
                        .param("age", String.valueOf(athlete.getAge()))
                        .flashAttr("athlete", new Athlete()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teams/" + team.getId()));

        verify(teamService, times(1)).assignAthleteToTeam(anyInt(), any(Athlete.class));



    }

    @Test
    void teatReleaseAthleteFromTeam() throws Exception {
        Athlete athlete = new Athlete();
        athlete.setId(3);
        Team team = new Team();
        team.setId(1);

        mockMvc.perform(patch("/teams/{id}/{athleteId}/releaseAthlete", team.getId(), athlete.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teams/" + team.getId()));

        verify(teamService, times(1)).releaseAthleteFromTeam(anyInt(), anyInt());

    }

    @Test
    void testReleaseEventFromTeam() throws Exception {

        Event event = new Event();
        event.setId(2);
        Team team = new Team();
        team.setId(1);

        mockMvc.perform(patch("/teams/{id}/{eventId}/releaseEvent", team.getId(), event.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teams/" + team.getId()));

        verify(teamService, times(1)).releaseEventFromTeam(anyInt(), anyInt());


    }
}