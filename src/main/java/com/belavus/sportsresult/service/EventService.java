package com.belavus.sportsresult.service;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;

import java.util.List;
import java.util.Set;

public interface EventService {

    List<Event> findAllEvents();

    Event saveEvent(Event event);

    void deleteEventById(Integer id);

    Event findOneEvent(Integer id);

    Set<Team> getTeamsByEventId(Integer id);

    void updateEvent(Integer id, Event updateEvent);

    void assignAthleteToEvent(Integer id, Athlete selectedAthlete);

    Set<Athlete> getAthletesByEventId(Integer id);

    void releaseAthleteFromEvent(Integer id, Integer athleteId);

    void assignTeamToEvent(Integer id, Team selectedTeam);

    void releaseTeamFromEvent(Integer id, Integer teamId);

    Set<Event> getEventsByAthleteId(Integer id);
}
