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
public class AthleteService { // TODO: n.kvetko: perform code formatting

    private final AthleteRepository athleteRepository;
    private final TeamRepository teamRepository;

    @Autowired // TODO: n.kvetko: unnecessary annotation
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
        return foundAthlete.orElse(null);
    }

    public void update(int id, Athlete updateAthlete) {
        updateAthlete.setId(id);
        athleteRepository.save(updateAthlete);
    }

    public Set<Team> getTeamsByAthleteId(int id) {

        Optional<Athlete> athlete = athleteRepository.findById(id);

        if (athlete.isPresent()) {
            Hibernate.initialize(athlete.get().getTeams());

////        athleteRepository.findAllById(team);

//        Set teamSet = athleteRepository.findById(id).map(Athlete::getTeams).orElse(null);
            return athlete.get().getTeams();
        } else {
            return Collections.emptySet();
        }
    }

//    public Set<Team> getAthleteInEvent(int id) {
//        return teamRepository.findById(id).map(Team::getAthletes).orElse(null);
//    }



    public void assign(int id, Team selectedTeam) {
        athleteRepository.findById(id).ifPresent(
                athlete -> {
                    athlete.setTeams(Collections.singleton((selectedTeam)));
                });
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
}
//
//    public void release(int id) {
//        athleteRepository.findById(id).ifPresent(
//                athlete -> athlete.setOneTeam(null)
//        );
//    }



