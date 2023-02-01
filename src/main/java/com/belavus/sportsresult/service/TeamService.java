package com.belavus.sportsresult.service;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public List<Team> getTeamsByEventId(Integer id) {

        return teamRepository.findTeamsByEventsId(id);
    }

    public Team findOne(int teamId) {
        Optional<Team> foundTeam = teamRepository.findById(teamId);
        return foundTeam.orElse(null);
    }

    public void update(int teamId, Team updateTeam) {
        String teamName = updateTeam.getName();
        String teamCoach = updateTeam.getCoach();
        Team team = teamRepository.findById(teamId).orElseThrow();
        team.setName(teamName);
        team.setCoach(teamCoach);
        teamRepository.save(team);
    }

    public Set<Event> getTeamInEvent(int id) {
        return teamRepository.findById(id).map(Team::getEvents).orElse(null);
    }

    public void assign(int id, Athlete selectedAthlete) {
        int athleteId = selectedAthlete.getId();
        Team team = teamRepository.findTeamWithAthletesById(id).orElseThrow();
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow();
        team.addAthletes(athlete);
        teamRepository.save(team);

    }

    public Set<Athlete> getAthletesByTeamId(int id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            Hibernate.initialize(team.get().getAthletes());
            return team.get().getAthletes();
        } else {
            return Collections.emptySet();
        }
    }
}


