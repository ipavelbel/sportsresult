package com.belavus.sportsresult.service;


import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Event findById(Integer id){
        return eventRepository.getOne(id);
    }

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Event saveEvent(Event event){
        return eventRepository.save(event);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(Integer id){
        eventRepository.deleteById(id);
    }

    public Event findOne(int id) {
        Optional<Event> foundEvent =eventRepository.findById(id);
        return foundEvent.orElse(null);
    }

//    public List<Team> getTeamsByEventId(Integer id){
//        return getTeamsByEventId(id);
//    };

}
