package com.belavus.sportsresult.service.impl;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import com.belavus.sportsresult.service.AthleteService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AthleteServiceImpl implements AthleteService { // TODO: perform code formatting

    private final AthleteRepository athleteRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public AthleteServiceImpl(AthleteRepository athleteRepository,
                              TeamRepository teamRepository) {
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    public List<Athlete> findAll() {
        return athleteRepository.findAll();
    }

    @Override
    public void save(Athlete athlete) {
        athleteRepository.save(athlete);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(Integer id) {
        athleteRepository.deleteById(id);
    }

    @Override
    public Athlete findOne(Integer id) {
        Optional<Athlete> foundAthlete = athleteRepository.findById(id);
        return foundAthlete.orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Integer id, Athlete updateAthlete) {
        Athlete athlete = athleteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        athlete.setName(updateAthlete.getName());
        athlete.setSurname(updateAthlete.getSurname());
        athlete.setAge(updateAthlete.getAge());
        athleteRepository.save(athlete);
    }

    @Override
    public Set<Team> getTeamsByAthleteId(Integer id) {
        Athlete athlete = athleteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        return athlete.getTeams();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void assignTeam(Integer id, Team selectedTeam) {
        int teamId = selectedTeam.getId();
        Athlete athlete = athleteRepository.findAthleteWithTeamsById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
        athlete.addTeam(team);
        athleteRepository.save(athlete);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void releaseAthleteFromTeam(Integer id, Integer teamId) {
        Athlete athlete = athleteRepository.findAthleteWithTeamsById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        Team team = athlete.getTeams().stream()
                .filter(team1 -> team1.getId() == teamId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
        athlete.removeTeam(team);
        athleteRepository.save(athlete);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void releaseAthleteFromEvent(Integer id, Integer eventId) {
        Athlete athlete = athleteRepository.findAthleteWithEventsById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id" + id + " Not found"));
        Event event = athlete.getEvents().stream()
                .filter(event1 -> event1.getId() == eventId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Event with id" + eventId + " Not found"));
        athlete.removeAthlete(event);
        athleteRepository.save(athlete);
    }
}






