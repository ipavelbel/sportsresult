package com.belavus.sportsresult.service;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Team;

import java.util.List;
import java.util.Set;

public interface AthleteService {

    List<Athlete> findAll();

    void save(Athlete athlete);

    void deleteById(Integer id);

    Athlete findOne(Integer id);

    void update(Integer id, Athlete updateAthlete);

    Set<Team> getTeamsByAthleteId(Integer id);

    void assignTeam(Integer id, Team selectedTeam);

    void releaseAthleteFromTeam(Integer id, Integer teamId);

    void releaseAthleteFromEvent(Integer id, Integer eventId);
}
