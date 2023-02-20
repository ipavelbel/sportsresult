package com.belavus.sportsresult.service.impl;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.AthleteRepository;
import com.belavus.sportsresult.repository.EventRepository;
import com.belavus.sportsresult.repository.TeamRepository;
import com.belavus.sportsresult.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AthleteRepository athleteRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            AthleteRepository athleteRepository,
                            TeamRepository teamRepository) {
        this.eventRepository = eventRepository;
        this.athleteRepository = athleteRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }


    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteEventById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event findOneEvent(Integer id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateEvent(Integer id, Event updateEvent) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        event.setName(updateEvent.getName());
        event.setPlace(updateEvent.getPlace());
        event.setDateOfEvent(updateEvent.getDateOfEvent());
        eventRepository.save(event);

    }

    @Override
    public Set<Team> getTeamsByEventId(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        return event.getTeams();
    }


    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void assignAthleteToEvent(Integer id, Athlete selectedAthlete) {
        int athleteId = selectedAthlete.getId();
        Event event = eventRepository.findEventWithAthletesById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow();
        event.addAthlete(athlete);
        eventRepository.save(event);
    }

    @Override
    public Set<Athlete> getAthletesByEventId(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        return event.getAthletes();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void releaseAthleteFromEvent(Integer id, Integer athleteId) {
        Event event = eventRepository.findEventWithAthletesById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        Athlete athlete = event.getAthletes().stream()
                .filter(oneAthlete -> oneAthlete.getId() == athleteId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Athlete with id" + athleteId + " Not found"));
        event.removeAthlete(athlete);
        eventRepository.save(event);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void assignTeamToEvent(Integer id, Team selectedTeam) {
        int teamId = selectedTeam.getId();
        Event event = eventRepository.findEventWithTeamsById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        Team team = teamRepository.findById(teamId).orElseThrow();
        event.addTeam(team);
        eventRepository.save(event);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void releaseTeamFromEvent(Integer id, Integer teamId) {
        Event event = eventRepository.findEventWithTeamsById(id).orElseThrow(() -> new EntityNotFoundException("Event with id: " + id + " Not found"));
        Team team = event.getTeams().stream()
                .filter(oneTeam -> oneTeam.getId() == teamId)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Team with id" + teamId + " Not found"));
        event.removeTeam(team);
        eventRepository.save(event);
    }

    @Override
    public Set<Event> getEventsByAthleteId(Integer id) {
        Athlete athlete = athleteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Athlete with id: " + id + " Not found"));
        return athlete.getEvents();
    }
}
