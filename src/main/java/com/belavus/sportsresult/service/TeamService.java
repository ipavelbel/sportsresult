package com.belavus.sportsresult.service;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;

import java.util.List;
import java.util.Set;

public interface TeamService {

    List<Team> findAllTeams();

    void saveTeam(Team team);

    void deleteTeamById(Integer id);

    Team findOneTeam(Integer teamId);

    void updateTeam(Integer teamId, Team updateTeam);

    Set<Event> getEventsByTeamId(Integer id);

    void assignAthleteToTeam(Integer id, Athlete selectedAthlete);

    void releaseAthleteFromTeam(Integer id, Integer athleteId);

    Set<Athlete> getAthletesByTeamId(Integer id);

    void releaseEventFromTeam(Integer id, Integer eventId);


}
