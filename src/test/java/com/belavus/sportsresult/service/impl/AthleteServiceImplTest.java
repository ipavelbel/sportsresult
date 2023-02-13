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
class AthleteServiceImplTest {

    @Mock
    AthleteRepository athleteRepository;
    @Mock
    TeamRepository teamRepository;
    @InjectMocks
    AthleteServiceImpl athleteService;

    @Test
    void testFindAllAthletes() {

        Athlete athlete1 = new Athlete("Name", "Surname", 30);
        Athlete athlete2 = new Athlete("Name1", "Surname1", 30);
        List<Athlete> athletes = Arrays.asList(athlete1, athlete2);
        when(athleteRepository.findAll()).thenReturn(athletes);

        List<Athlete> athleteList = athleteService.findAll();

        assertNotNull(athleteList);
        assertEquals(2, athleteList.size());
        assertTrue(athletes.size() == athleteList.size() && athletes.containsAll(athleteList) && athleteList.containsAll(athletes));
        verify(athleteRepository, times(1)).findAll();
    }

    @Test
    void testSaveAthlete() {

        Athlete athlete = new Athlete("Name", "Surname", 30);

        athleteService.save(athlete);

        ArgumentCaptor<Athlete> argumentCaptor = ArgumentCaptor.forClass(Athlete.class);
        verify(athleteRepository).save(argumentCaptor.capture());

    }

    @Test
    void testDeleteById() {

        Athlete athlete = new Athlete("Name", "Surname", 30);
        athlete.setId(2);

        athleteService.deleteById(athlete.getId());

        verify(athleteRepository).deleteById(anyInt());

    }

    @Test
    void testFindOneAthlete() {

        Athlete athlete = new Athlete("Name", "Surname", 30);
        athlete.setId(2);
        when(athleteRepository.findById(anyInt())).thenReturn(Optional.of(athlete));

        Athlete actualAthlete = athleteService.findOne(2);

        assertNotNull(actualAthlete);
        assertEquals(athlete, actualAthlete);

    }

    @Test
    void testFindOneWhenAthleteNotFound(){
        assertThrows(EntityNotFoundException.class, () -> athleteService.findOne(3));
    }

    @Test
    void testUpdate() {
        Athlete athlete = new Athlete("Name", "Surname", 30);
        athlete.setId(1);
        when(athleteRepository.findById(anyInt())).thenReturn(Optional.of(athlete));
        Athlete updatedAthleteForSave = new Athlete("Updated Name", "Updated Surname", 21);
        when(athleteRepository.save(any())).thenReturn(athlete);

        athleteService.update(athlete.getId(), updatedAthleteForSave);

        ArgumentCaptor<Athlete> argumentCaptor = ArgumentCaptor.forClass(Athlete.class);
        verify(athleteRepository).findById(athlete.getId());
        verify(athleteRepository).save(argumentCaptor.capture());
        Athlete captureValue = argumentCaptor.getValue();
        assertEquals(athlete, captureValue);

    }

    @Test
    void testGetTeamsByAthleteId() {

        Team team = mock(Team.class);
        Athlete athlete = new Athlete("Name", "Surname", 30);
        athlete.setId(1);
        athlete.addTeam(team);
        athleteRepository.save(athlete);

        when(athleteRepository.findById(anyInt())).thenReturn(Optional.of(athlete));

        athleteService.getTeamsByAthleteId(1);
        Set<Team> athleteSet = athlete.getTeams();

        verify(athleteRepository).findById(anyInt());
        assertEquals(1,athleteSet.size());

    }

    @Test
    void testGetTeamsByAthleteIdAthleteNotFound() {
        assertThrows(EntityNotFoundException.class, () -> athleteService.getTeamsByAthleteId(2));
    }

    @Test
    void testAssignTeam() {

        Athlete athlete = new Athlete("Name", "Surname", 30);
        athlete.setId(1);
        Team teamForAdd = new Team("Name", "Trainer");
        teamForAdd.setId(2);

        when(athleteRepository.findAthleteWithTeamsById(anyInt())).thenReturn(Optional.of(athlete));
        when(teamRepository.findById(anyInt())).thenReturn(Optional.of(teamForAdd));
        when(athleteRepository.save(athlete)).thenReturn(athlete);

        athleteService.assignTeam(1,teamForAdd);

        verify(athleteRepository).findAthleteWithTeamsById(anyInt());
        verify(teamRepository).findById(anyInt());
        verify(athleteRepository).save(any());



    }

    @Test
    void testReleaseAthleteFromTeam() {

        Athlete athlete = new Athlete("Name", "Surname", 30);
        athlete.setId(1);
        Team team = mock(Team.class);
        athlete.addTeam(team);

        when(athleteRepository.findAthleteWithTeamsById(anyInt())).thenReturn(Optional.of(athlete));
        when(athleteRepository.save(any())).thenReturn(athlete);

        athleteService.releaseAthleteFromTeam(athlete.getId(), team.getId());

        verify(athleteRepository).findAthleteWithTeamsById(anyInt());
        verify(athleteRepository).save(any());
    }

    @Test
    void testReleaseAthleteFromEvent() {
        Athlete athlete = new Athlete("Name", "Surname", 30);
        athlete.setId(1);
        Event event = new Event("Name Event", "City");
        event.setId(2);
        event.addAthlete(athlete);
        when(athleteRepository.findAthleteWithEventsById(anyInt())).thenReturn(Optional.of(athlete));
        when(athleteRepository.save(any())).thenReturn(athlete);

        athleteService.releaseAthleteFromEvent(athlete.getId(), event.getId());

        verify(athleteRepository).findAthleteWithEventsById(anyInt());
        verify(athleteRepository).save(any());
    }
}