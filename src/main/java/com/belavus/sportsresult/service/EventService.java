package com.belavus.sportsresult.service;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;

import java.util.List;
import java.util.Set;

public interface EventService {

    List<Event> findAll();

    Event save(Event event);

    void deleteById(Integer id);

    Event findOne(Integer id);

    Set<Team> getTeamsByEventId(Integer id);

    void update(Integer id, Event updateEvent);

    void assignAthlete(Integer id, Athlete selectedAthlete);

    Set<Athlete> getAthletesByEventId(Integer id);

    void releaseAthlete(Integer id, Integer athleteId);

    void assignTeam(Integer id, Team selectedTeam);

    void releaseTeam(Integer id, Integer teamId);

    Set<Event> getEventsByAthleteId(Integer id);
}
