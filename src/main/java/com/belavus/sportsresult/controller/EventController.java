package com.belavus.sportsresult.controller;


import com.belavus.sportsresult.model.Event;

import com.belavus.sportsresult.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }



    @GetMapping("/events")
    public String findAll(Model model){
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        return "event/event-list";
    }

    @GetMapping("/event-create")
    public String createEventForm(Event event){
        return "event/event-create";
    }

    @PostMapping("/event-create")
    public String createEvent(Event event){
        eventService.saveEvent(event);
        return "redirect:/events";
    }

    @GetMapping("event-delete/{id}")
    public String deleteEvent(@PathVariable("id") Integer id){
        eventService.deleteById(id);
        return "redirect:/events";
    }

    @GetMapping("/event-update/{id}")
    public String updateEventForm(@PathVariable("id") Integer id, Model model){
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        return "event/event-update";
    }

    @PostMapping("/event-update")
    public String updateEvent(Event event){
        eventService.saveEvent(event);
        return "redirect:/events";
    }
//    @PostMapping("/event/{id}")
//    public String showEventId(@PathVariable("id") Integer id, Model model){
//        Event event = eventService.findById(id);
//        model.addAttribute("event", event);
//        return "team/team-list";
//    }
    @GetMapping("/event/{id}")
    public String showTeams(@PathVariable("id") Integer id, Model model){
        return "team/team-list";
    }

//    @GetMapping("event/{id}/teams")
//    public String getTeamsByEventId(@PathVariable("id") Integer id, Model model){
//      List<Team> teams = eventService.getTeamsByEventId(id);
//      model.addAttribute("teams",teams);
//        return "redirect:/team-list";
//    }

}
