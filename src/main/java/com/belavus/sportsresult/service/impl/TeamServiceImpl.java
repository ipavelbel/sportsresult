package com.belavus.sportsresult.service.impl;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import com.belavus.sportsresult.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class TeamServiceImpl implements TeamService { // TODO:  perform code formatting

    private final TeamRepository teamRepository;
    private final AthleteRepository athleteRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, AthleteRepository athleteRepository) {
        this.teamRepository = teamRepository;
        this.athleteRepository = athleteRepository;
    }


    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public void save(Team team) {
        teamRepository.save(team); // TODO: Return value of the method is never used
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(Integer id) {
        teamRepository.deleteById(id);
    }


    @Override
    public Team findOne(Integer teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Integer teamId, Team updateTeam) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
        team.setName(updateTeam.getName());
        team.setCoach(updateTeam.getCoach());
        teamRepository.save(team);
    }

    @Override
    public Set<Event> getTeamInEvent(Integer id) {
        return teamRepository.findById(id)
                .map(Team::getEvents)
                .orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void assignAthleteInTeam(Integer id, Athlete selectedAthlete) {
        int athleteId = selectedAthlete.getId();
        Team team = teamRepository.findTeamWithAthletesById(id).orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + athleteId + " Not found"));
        team.addAthletes(athlete);
        teamRepository.save(team);

    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void releaseAthleteFromTeam(Integer id, Integer athleteId) {
        Team team = teamRepository.findTeamWithAthletesById(id).orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
        Athlete athlete = team.getAthletes().stream()
                .filter(athlete1 -> athlete1.getId() == athleteId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Athlete with id" + athleteId + " Not found"));
        team.removeAthletes(athlete);
        teamRepository.save(team);
    }

    @Override
    public Set<Athlete> getAthletesByTeamId(Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
        return team.getAthletes();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void releaseEventFromTeam(Integer id, Integer eventId) {
        Team team = teamRepository.findTeamWithEventsById(id).orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
        Event event = team.getEvents().stream()
                .filter(event1 -> event1.getId() == eventId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Event with id: " + eventId + " Not found"));
        team.removeEvents(event);
        teamRepository.save(team);
    }
}

