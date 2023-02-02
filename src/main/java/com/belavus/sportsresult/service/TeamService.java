package com.belavus.sportsresult.service;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamService { // TODO:  perform code formatting

    private final TeamRepository teamRepository;
    private final AthleteRepository athleteRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, AthleteRepository athleteRepository) {
        this.teamRepository = teamRepository;
        this.athleteRepository = athleteRepository;
    }


    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public void save(Team team) {
        teamRepository.save(team); // TODO: Return value of the method is never used
    }

    public void deleteById(Integer id) {
        teamRepository.deleteById(id);
    }


    public Team findOne(int teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
    }

    public void update(int teamId, Team updateTeam) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
        team.setName(updateTeam.getName());
        team.setCoach(updateTeam.getCoach());
        teamRepository.save(team);
    }

    public Set<Event> getTeamInEvent(int id) {
        return teamRepository.findById(id)
                .map(Team::getEvents)
                .orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
    }

    public void assignAthleteInTeam(int id, Athlete selectedAthlete) {
        int athleteId = selectedAthlete.getId();
        Team team = teamRepository.findTeamWithAthletesById(id).orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + athleteId + " Not found"));
        team.addAthletes(athlete);
        teamRepository.save(team);

    }

    public void releaseAthleteFromTeam(int id, int athleteId) {
        Team team = teamRepository.findTeamWithAthletesById(id).orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
        Athlete athlete = team.getAthletes().stream()
                .filter(athlete1 -> athlete1.getId() == athleteId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Athlete with id" + athleteId + " Not found"));
        team.removeAthletes(athlete);
        teamRepository.save(team);
    }

    public Set<Athlete> getAthletesByTeamId(int id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
        return team.getAthletes();
    }

    public void releaseEventFromTeam(int id, int eventId) {
        Team team = teamRepository.findTeamWithEventsById(id).orElseThrow(() -> new EntityNotFoundException("Team with id" + id + " Not found"));
        Event event = team.getEvents().stream()
                .filter(event1 -> event1.getId() == eventId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Event with id: " + eventId + " Not found"));
        team.removeEvents(event);
        teamRepository.save(team);
    }
}


