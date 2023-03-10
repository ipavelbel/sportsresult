package com.belavus.sportsresult.controller;


import com.belavus.sportsresult.model.Athlete;
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
@RequestMapping("/athletes")
public class AthleteController {

    public static final String redirectToAthletes = "redirect:/athletes/";
    private final AthleteService athleteService;
    private final TeamService teamService;
    private final EventService eventService;

    @Autowired
    public AthleteController(AthleteService athleteService, TeamService teamService, EventService eventService) {
        this.athleteService = athleteService;
        this.teamService = teamService;
        this.eventService = eventService;
    }

    @GetMapping("")
    public String findAllAthletes(Model model) {
        model.addAttribute("athletes", athleteService.findAllAthletes());
        return "athlete/athlete-list";
    }

    @GetMapping("/new")
    public String createAthleteForm(@ModelAttribute("athlete") Athlete athlete) {
        return "athlete/athlete-create";
    }

    @PostMapping("")
    public String createAthlete(@ModelAttribute("athlete") @Valid Athlete athlete, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "athlete/athlete-create";
        }
        athleteService.saveAthlete(athlete);
        return redirectToAthletes;
    }

    @GetMapping("/{id}/edit")
    public String editAthleteForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("athlete", athleteService.findOneAthlete(id));
        return "athlete/athlete-update";
    }

    @PatchMapping("/{id}")
    public String updateAthlete(@ModelAttribute("athlete") @Valid Athlete athlete, BindingResult bindingResult,
                                @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "athlete/athlete-update";
        }
        athleteService.updateAthlete(id, athlete);
        return redirectToAthletes;
    }

    @DeleteMapping("/{id}")
    public String deleteAthlete(@PathVariable("id") int id) {
        athleteService.deleteAthleteById(id);
        return redirectToAthletes;
    }

    @GetMapping("/{id}")
    public String showOneAthlete(@PathVariable("id") int id, Model model, @ModelAttribute("team") Team team) {

        model.addAttribute("athlete", athleteService.findOneAthlete(id));
        model.addAttribute("teamsInAthlete", athleteService.getTeamsByAthleteId(id));
        model.addAttribute("teams", teamService.findAllTeams());
        model.addAttribute("eventsInAthlete", eventService.getEventsByAthleteId(id));

        return "athlete/showAthlete";
    }

    @PatchMapping("/{id}/assign")
    public String addAthleteToTeam(@PathVariable("id") int id, @ModelAttribute("teamId") Team selectedTeam) {
        athleteService.assignTeamToAthlete(id, selectedTeam);
        return redirectToAthletes + id;
    }

    @PatchMapping("/{id}/{teamId}/releaseAthlete")
    public String releaseAthleteFromTeam(@PathVariable("id") int id, @PathVariable("teamId") int teamId) {
        athleteService.releaseAthleteFromTeam(id, teamId);
        return redirectToAthletes + id;
    }

    @PatchMapping("/{id}/{eventId}/releaseEvent")
    public String releaseAthleteFromEvent(@PathVariable("id") int id, @PathVariable("eventId") int eventId) {
        athleteService.releaseAthleteFromEvent(id, eventId);
        return redirectToAthletes + id;
    }
}

