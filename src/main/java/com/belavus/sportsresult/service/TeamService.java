package com.belavus.sportsresult.service;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;

import java.util.List;
import java.util.Set;

public interface TeamService {

    List<Team> findAll();
    void save(Team team);
    void deleteById(Integer id);
    Team findOne(Integer teamId);
    void update(Integer teamId, Team updateTeam);
    Set<Event> getTeamInEvent(Integer id);
    void assignAthleteInTeam(Integer id, Athlete selectedAthlete);
    void releaseAthleteFromTeam(Integer id, Integer athleteId);
    Set<Athlete> getAthletesByTeamId(Integer id);
    void releaseEventFromTeam(Integer id, Integer eventId);


}
