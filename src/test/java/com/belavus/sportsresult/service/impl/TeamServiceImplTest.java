package com.belavus.sportsresult.service.impl;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.AthleteRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {


    @Mock
    TeamRepository teamRepository;

    @Mock
    AthleteRepository athleteRepository;

    @InjectMocks
    TeamServiceImpl teamService;

    @Test
    void testFindAll() {

        Team team1 = new Team("Name", "Trainer");
        Team team2 = new Team("Name2", "Trainer2");
        List<Team> teams = Arrays.asList(team1, team2);
        when(teamRepository.findAll()).thenReturn(teams);

        List<Team> teamList = teamService.findAll();

        assertNotNull(teamList);
        assertEquals(2, teamList.size());
        assertTrue(teams.size() == teamList.size() && teams.containsAll(teamList) && teamList.containsAll(teams));
        verify(teamRepository, times(1)).findAll();

    }

    @Test
    void testSaveTeam() {

        Team team = new Team("name", "Trainer");

        teamService.save(team);

        ArgumentCaptor<Team> argumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository).save(argumentCaptor.capture());

    }

    @Test
    void testDeleteById() {

        Team team = new Team("Name", "Trainer");
        team.setId(1);

        teamService.deleteById(team.getId());

        verify(teamRepository).deleteById(anyInt());
    }

    @Test
    void testFindOneTeam() {

        Team team = new Team("Name", "Trainer");
        team.setId(1);
        when(teamRepository.findById(anyInt())).thenReturn(Optional.of(team));

        Team actualTeam = teamService.findOne(1);

        assertNotNull(actualTeam);
        assertEquals(team, actualTeam);
    }

    @Test
    void testFindOneTeamWhenTeamNotFound() {
        assertThrows(EntityNotFoundException.class, () -> teamService.findOne(5));
    }

    @Test
    void testUpdate() {
        Team team = new Team("Name", "Trainer");
        team.setId(1);
        when(teamRepository.findById(anyInt())).thenReturn(Optional.of(team));
        Team updatedTeamForSave = new Team("UpdatedName", "UpdatedTrainer");
        when(teamRepository.save(any())).thenReturn(team);

        teamService.update(team.getId(), updatedTeamForSave);

        ArgumentCaptor<Team> argumentCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepository).findById(team.getId());
        verify(teamRepository).save(argumentCaptor.capture());
        Team captureValue = argumentCaptor.getValue();
        assertEquals(team, captureValue);


    }

    @Test
    void testGetEventsByTeamId() {

        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        Team team = new Team("Name", "Trainer");
        team.setId(1);
        team.addEvents(event1);
        team.addEvents(event2);
        teamRepository.save(team);

        when(teamRepository.findById(anyInt())).thenReturn(Optional.of(team));

        teamService.getAthletesByTeamId(1);
        Set<Event> eventsSet = team.getEvents();

        verify(teamRepository).findById(anyInt());
        assertEquals(2, eventsSet.size());


    }

    @Test
    void testAssignAthleteInTeam() {

        Team team = new Team("Name", "Trainer");
        team.setId(2);
        Athlete athleteForAdd = new Athlete("Name", "Surname", 30);
        athleteForAdd.setId(1);

        when(teamRepository.findTeamWithAthletesById(anyInt())).thenReturn(Optional.of(team));
        when(athleteRepository.findById(anyInt())).thenReturn(Optional.of(athleteForAdd));
        when(teamRepository.save(team)).thenReturn(team);

        teamService.assignAthleteInTeam(2, athleteForAdd);


        verify(teamRepository).findTeamWithAthletesById(anyInt());
        verify(athleteRepository).findById(anyInt());
        verify(teamRepository).save(any());

    }

    @Test
    void testReleaseAthleteFromTeam() {
        Team team = new Team("Name", "Trainer");
        team.setId(1);
        Athlete athlete = new Athlete("Name", "Surname", 30);
        athlete.setId(2);
        athlete.addTeam(team);
        when(teamRepository.findTeamWithAthletesById(anyInt())).thenReturn(Optional.of(team));
        when(teamRepository.save(any())).thenReturn(team);

        teamService.releaseAthleteFromTeam(team.getId(), athlete.getId());

        verify(teamRepository).findTeamWithAthletesById(anyInt());
        verify(teamRepository).save(any());
    }

    @Test
    void testGetAthletesByTeamId() {

        Athlete athlete1 = mock(Athlete.class);
        Athlete athlete2 = mock(Athlete.class);
        Set<Athlete> givenAthleteSet = Set.of(athlete1, athlete2);
        Team team = new Team("Name", "Trainer");
        team.setId(1);
        team.addAthletes(athlete1);
        team.addAthletes(athlete2);
        teamRepository.save(team);

        when(teamRepository.findById(anyInt())).thenReturn(Optional.of(team));

        teamService.getAthletesByTeamId(1);
        Set<Athlete> athleteSet = team.getAthletes();

        verify(teamRepository).findById(anyInt());
        assertEquals(2, athleteSet.size());
        assertEquals(givenAthleteSet,athleteSet);

    }

    @Test
    void testGetAthletesByTeamIdTeamNotFound() {
        assertThrows(EntityNotFoundException.class, () -> teamService.getAthletesByTeamId(5));
    }

    @Test
    void testReleaseEventFromTeam() {

        Team team = new Team("Name", "Trainer");
        team.setId(1);
        Event event = new Event("Name Event", "City");
        event.setId(2);
        event.addTeam(team);
        when(teamRepository.findTeamWithEventsById(anyInt())).thenReturn(Optional.of(team));
        when(teamRepository.save(any())).thenReturn(team);

        teamService.releaseEventFromTeam(team.getId(), event.getId());

        verify(teamRepository).findTeamWithEventsById(anyInt());
        verify(teamRepository).save(any());


    }
}