package com.belavus.sportsresult.controller;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.service.AthleteService;
import com.belavus.sportsresult.service.EventService;
import com.belavus.sportsresult.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/events")
public class EventsController {

    public static final String redirectToEvents = "redirect:/events/";

    private final EventService eventService;
    private final TeamService teamService;
    private final AthleteService athleteService;


    @Autowired
    public EventsController(EventService eventService, TeamService teamService, AthleteService athleteService) {
        this.eventService = eventService;
        this.teamService = teamService;
        this.athleteService = athleteService;
    }


    @GetMapping("")
    public String findAllEvents(Model model) {
        model.addAttribute("events", eventService.findAllEvents());
        return "event/event-list";
    }


    @GetMapping("/new")
    public String createEventForm(@ModelAttribute("event") Event event) {
        return "event/event-create";
    }

    @PostMapping("")
    public String createEvent(@ModelAttribute("event") @Valid Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "event/event-create";
        }
        eventService.saveEvent(event);
        return redirectToEvents;
    }


    @GetMapping("/{id}/edit")
    public String editEventForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("event", eventService.findOneEvent(id));
        return "event/event-update";
    }

    @PatchMapping("/{id}")
    public String updateEvent(@ModelAttribute("event") @Valid Event event, BindingResult bindingResult,
                              @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "event/event-update";
        }
        eventService.updateEvent(id, event);
        return redirectToEvents;
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable("id") int id) {
        eventService.deleteEventById(id);
        return redirectToEvents;
    }

    @GetMapping("/{id}")
    public String showOneEvent(@PathVariable("id") int id, Model model, @ModelAttribute("athlete") Athlete athlete, @ModelAttribute("team") Team team) {
        model.addAttribute("event", eventService.findOneEvent(id));
        model.addAttribute("teams", teamService.findAllTeams());
        model.addAttribute("teamsInEvent", eventService.getTeamsByEventId(id));
        model.addAttribute("athletes", athleteService.findAllAthletes());
        model.addAttribute("athletesInEvents", eventService.getAthletesByEventId(id));

        return "event/show";
    }

    @PatchMapping("/{id}/assignAthlete")
    public String addAthleteToEvent(@PathVariable int id, @ModelAttribute("athleteId") Athlete selectedAthlete) {
        eventService.assignAthleteToEvent(id, selectedAthlete);
        return redirectToEvents + id;
    }

    @PatchMapping("/{id}/{athleteId}/releaseAthlete")
    public String releaseAthleteFromEvent(@PathVariable("id") int id, @PathVariable("athleteId") int athleteId) {
        eventService.releaseAthleteFromEvent(id, athleteId);
        return redirectToEvents + id;
    }

    @PatchMapping("/{id}/assignTeam")
    public String addTeamToEvent(@PathVariable int id, @ModelAttribute("teamId") Team selectedTeam) {
        eventService.assignTeamToEvent(id, selectedTeam);
        return redirectToEvents + id;
    }

    @PatchMapping("/{id}/{teamId}/releaseTeam")
    public String releaseTeamFromEvent(@PathVariable("id") int id, @PathVariable("teamId") int teamId) {
        eventService.releaseTeamFromEvent(id, teamId);
        return redirectToEvents + id;
    }


}
