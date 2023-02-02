package com.belavus.sportsresult.service;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team; // TODO: n.kvetko: unused import
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.EventRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@Transactional
public class EventService { // TODO: perform code formatting

    private final EventRepository eventRepository;
    private final AthleteRepository athleteRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public EventService(EventRepository eventRepository,
                        AthleteRepository athleteRepository,
                        TeamRepository teamRepository) {
        this.eventRepository = eventRepository;
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
    }


    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event save(Event event) { // TODO: n.kvetko: Return value of the method is never used
        return eventRepository.save(event);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    public Event findOne(int id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
    }

    public Set<Team> getTeamsByEventId(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        return event.getTeams();
    }

    public void update(int id, Event updateEvent) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        event.setName(updateEvent.getName());
        event.setPlace(updateEvent.getPlace());
        eventRepository.save(event);

    }

    public void assignAthlete(int id, Athlete selectedAthlete) {
        int athleteId = selectedAthlete.getId();
        Event event = eventRepository.findEventWithAthletesById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow();
        event.addAthlete(athlete);
        eventRepository.save(event);
    }

    public Set<Athlete> getAthletesByEventId(int id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        return event.getAthletes();
    }

    public void releaseAthlete(int id, int athleteId) {
        Event event = eventRepository.findEventWithAthletesById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        Athlete athlete = event.getAthletes().stream()
                .filter(athlete1 -> athlete1.getId() == athleteId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Athlete with id" + athleteId + " Not found"));
        event.removeAthlete(athlete);
        eventRepository.save(event);
    }

    public void assignTeam(int id, Team selectedTeam) {
        int teamId = selectedTeam.getId();
        Event event = eventRepository.findEventWithTeamsById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        Team team = teamRepository.findById(teamId).orElseThrow();
        event.addTeam(team);
        eventRepository.save(event);
    }

    public void releaseTeam(int id, int teamId) {
        Event event = eventRepository.findEventWithTeamsById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        Team team = event.getTeams().stream()
                .filter(team1 -> team1.getId() == teamId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
        event.removeTeam(team);
        eventRepository.save(event);
    }
}
