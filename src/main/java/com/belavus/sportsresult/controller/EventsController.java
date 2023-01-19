package com.belavus.sportsresult.controller;


import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.service.EventService;
import com.belavus.sportsresult.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventsController {

    public static final String redirectToEvents = "redirect:/events/";

    private final EventService eventService;
    private final TeamService teamService;

    @Autowired
    public EventsController(EventService eventService, TeamService teamService) {
        this.eventService = eventService;
        this.teamService = teamService;
    }


    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "event/event-list";
    }


    @GetMapping("/new")
    public String createEventForm(@ModelAttribute("event") Event event) { // TODO: n.kvetko: unused parameters
        return "event/event-create";
    }

    @PostMapping("")
    public String createEvent(@ModelAttribute("event") Event event) {
        eventService.save(event);
        return redirectToEvents;
    }


    @GetMapping("/{id}/edit")
    public String editEventForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("event", eventService.findOne(id));
        return "event/event-update";
    }

    @PatchMapping("/{id}")
    public String updateEvent(@ModelAttribute("event") Event event, @PathVariable("id") int id) {
        eventService.update(id, event);
        return redirectToEvents;
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable("id") int id) {
        eventService.deleteById(id);
        return redirectToEvents;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("event", eventService.findOne(id));
        model.addAttribute("teams", eventService.getTeamsByEventId(id));

        return "event/show";
    }

}
