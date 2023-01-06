package com.belavus.sportsresult.service;


import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team; // TODO: n.kvetko: unused import
import com.belavus.sportsresult.repository.EventRepository;
import com.belavus.sportsresult.repository.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EventService { // TODO: n.kvetko: perform code formatting

    private final EventRepository eventRepository;
    private final PeopleRepository peopleRepository;

    @Autowired // TODO: n.kvetko: unnecessary annotation
    public EventService(EventRepository eventRepository,
                        PeopleRepository peopleRepository) {
        this.eventRepository = eventRepository;
        this.peopleRepository = peopleRepository;
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public Event findById(int id){
//        return eventRepository.getOne(id); // TODO: n.kvetko: getOne is deprecated(!) Use getById(ID) instead.
//    }

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void save(Event event){ // TODO: n.kvetko: Return value of the method is never used
         eventRepository.save(event);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(int id){
        eventRepository.deleteById(id);
    }

    public Event findOne(int id) { // TODO: n.kvetko: Method is never used
        Optional<Event> foundEvent = eventRepository.findById(id);
        return foundEvent.orElse(null);
    }

    public Event getEventWithId(int id) {
        return eventRepository.findEventById(id);
    }

    public List<Team> getTeamsByEventId(Integer id){ // TODO: n.kvetko: Unused code should be deleted and can be retrieved from source control history if required.
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            Hibernate.initialize(event.get().getTeams());

            return event.get().getTeams();
        }
        else {
            return Collections.emptyList();
        }
    }

    public void update(int id, Event updateEvent) {
        updateEvent.setId(id);
        eventRepository.save(updateEvent);
    }

}
