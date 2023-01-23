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
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@Controller
@RequestMapping("/teams")
public class TeamController {

    public static final String redirectToTeams = "redirect:/teams/";
    private final TeamService teamService;
    private final EventService eventService;
    private final AthleteService athleteService;


    @Autowired
    public TeamController(TeamService teamService, EventService eventService, AthleteService athleteService) {
        this.teamService = teamService;
        this.eventService = eventService;
        this.athleteService = athleteService;
    }

    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "team/team-list";
    }


    @GetMapping("/new")
    public String createTeamForm(@ModelAttribute("team") Team team) {

        return "team/team-create";
    }

    @PostMapping("")
    public String createTeam(@ModelAttribute("team") Team team) {
        teamService.save(team);
        return redirectToTeams;
    }

    @GetMapping("/{teamId}/edit")
    public String editTeamForm(@PathVariable("teamId") Integer teamId, Model model) {
        model.addAttribute("team", teamService.findOne(teamId));
        return "team/team-update";
    }

    @PatchMapping("/{teamId}")
    public String updateTeam(@ModelAttribute("team") Team team, @PathVariable int teamId) {
        teamService.update(teamId, team);
        return redirectToTeams;
    }

    @DeleteMapping("/{teamId}")
    public String deleteTeam(@PathVariable("teamId") Integer teamId) {
        teamService.deleteById(teamId);
        return redirectToTeams;
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("athlete") Athlete athlete) {
        model.addAttribute("team", teamService.findOne(id));

        Set<Event> teamsInEvent = teamService.getTeamInEvent(id);

//        if (!teamsInEvent.isEmpty())
            model.addAttribute("teamsInEvent", teamsInEvent);
//        else
            model.addAttribute("events", eventService.findAll());
            model.addAttribute("athletes", athleteService.findAll());
            model.addAttribute("athletesInTeams", teamService.getAthletesByTeamId(id));

        return "team/showTeam";
    }

    @PatchMapping("/{id}/assignTeam")
    public String addTeamToEvent(@PathVariable("id") int id, @ModelAttribute("athlete") Athlete selectedAthlete) {

        teamService.assign(id, selectedAthlete);
        return redirectToTeams + id;
    }


}
