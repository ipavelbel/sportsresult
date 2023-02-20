package com.belavus.sportsresult.service;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Team;

import java.util.List;
import java.util.Set;

public interface AthleteService {

    List<Athlete> findAllAthletes();

    void saveAthlete(Athlete athlete);

    void deleteAthleteById(Integer id);

    Athlete findOneAthlete(Integer id);

    void updateAthlete(Integer id, Athlete updateAthlete);

    Set<Team> getTeamsByAthleteId(Integer id);

    void assignTeamToAthlete(Integer id, Team selectedTeam);

    void releaseAthleteFromTeam(Integer id, Integer teamId);

    void releaseAthleteFromEvent(Integer id, Integer eventId);
}
