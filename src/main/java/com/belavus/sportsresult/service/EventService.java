package com.belavus.sportsresult.service;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team; // TODO: n.kvetko: unused import
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.EventRepository;
import com.belavus.sportsresult.repository.PeopleRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService { // TODO: n.kvetko: perform code formatting

    private final EventRepository eventRepository;
    private final PeopleRepository peopleRepository;
    private final AthleteRepository athleteRepository;
    private final TeamRepository teamRepository;

    @Autowired // TODO: n.kvetko: unnecessary annotation
    public EventService(EventRepository eventRepository,
                        PeopleRepository peopleRepository,
                        AthleteRepository athleteRepository, TeamRepository teamRepository) {
        this.eventRepository = eventRepository;
        this.peopleRepository = peopleRepository;
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
    }


    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public void save(Event event) { // TODO: n.kvetko: Return value of the method is never used
        eventRepository.save(event);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    public Event findOne(int id) { // TODO: n.kvetko: Method is never used
        Optional<Event> foundEvent = eventRepository.findById(id);
        return foundEvent.orElse(null);
    }

    public Optional<Event> getEventWithAthletes(int id) {
        return eventRepository.findEventWithAthletesById(id);
    }

    public Set<Team> getTeamsByEventId(Integer id) { // TODO: n.kvetko: Unused code should be deleted and can be retrieved from source control history if required.
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            Hibernate.initialize(event.get().getTeams());

            return event.get().getTeams();
        } else {
            return Collections.emptySet();
        }
    }

    public void update(int id, Event updateEvent) {
        updateEvent.setId(id);
        eventRepository.save(updateEvent);
    }

    public void assignAthlete(int id, Athlete selectedAthlete) {
        int athleteId = selectedAthlete.getId();
        Event event = eventRepository.findEventWithAthletesById(id).orElseThrow();
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow();
        event.addAthlete(athlete);
        eventRepository.save(event);
    }

    public Set<Athlete> getAthletesByEventId(int id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            Hibernate.initialize((event.get().getAthletes()));
            return event.get().getAthletes();
        } else {
            return Collections.emptySet();
        }

    }

    public void releaseAthlete(int id, int athleteId) {
        Event event = eventRepository.findEventWithAthletesById(id).orElseThrow();
        Athlete athlete = event.getAthletes().stream()
                .filter(athlete1 -> athlete1.getId() == athleteId)
                .findFirst().orElseThrow();
        event.removeAthlete(athlete);
        eventRepository.save(event);
    }

    public void assignTeam(int id, Team selectedTeam) {
        int teamId = selectedTeam.getId();
        Event event = eventRepository.findEventWithTeamsById(id).orElseThrow();
        Team team = teamRepository.findById(teamId).orElseThrow();
        event.addTeam(team);
        eventRepository.save(event);
    }

    public void releaseTeam(int id, int teamId) {
        Event event = eventRepository.findEventWithTeamsById(id).orElseThrow();
        Team team = event.getTeams().stream()
                .filter(team1 -> team1.getId() == teamId)
                .findFirst().orElseThrow();
        event.removeTeam(team);
        eventRepository.save(event);
    }
}
