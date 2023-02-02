package com.belavus.sportsresult.service;


import com.belavus.sportsresult.model.Athlete;
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
public class AthleteService { // TODO: perform code formatting

    private final AthleteRepository athleteRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public AthleteService(AthleteRepository athleteRepository,
                          TeamRepository teamRepository) {
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
    }


    public List<Athlete> findAll() {
        return athleteRepository.findAll();
    }

    public void save(Athlete athlete) {
        athleteRepository.save(athlete);
    }

    public void deleteById(Integer id) {
        athleteRepository.deleteById(id);
    }

    public Athlete findOne(int id) {
        Optional<Athlete> foundAthlete = athleteRepository.findById(id);
        return foundAthlete.orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
    }

    public void update(int id, Athlete updateAthlete) {
        Athlete athlete = athleteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        athlete.setName(updateAthlete.getName());
        athlete.setSurname(updateAthlete.getSurname());
        athlete.setAge(updateAthlete.getAge());
        athleteRepository.save(athlete);
    }

    public Set<Team> getTeamsByAthleteId(int id) {
        Athlete athlete = athleteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        return athlete.getTeams();
    }

    public void assignAthlete(int id, Team selectedTeam) {
        int teamId = selectedTeam.getId();
        Athlete athlete = athleteRepository.findAthleteWithTeamsById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
        athlete.addTeam(team);
        athleteRepository.save(athlete);
    }

    //
    public Set<Athlete> getAthleteByTeamId(int id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            Hibernate.initialize(team.get().getAthletes());
            return team.get().getAthletes();
        } else {
            return Collections.emptySet();
        }
    }

    public void release(int id, int teamId) {
        Athlete athlete = athleteRepository.findAthleteWithTeamsById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        Team team = athlete.getTeams().stream()
                .filter(team1 -> team1.getId() == teamId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
        athlete.removeTeam(team);
        athleteRepository.save(athlete);
    }
}






