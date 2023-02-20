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
    public String findAllTeams(Model model) {
        model.addAttribute("teams", teamService.findAllTeams());
        return "team/team-list";
    }


    @GetMapping("/new")
    public String createTeamForm(@ModelAttribute("team") Team team) {
        return "team/team-create";
    }

    @PostMapping("")
    public String createTeam(@ModelAttribute("team") @Valid Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "team/team-create";
        }
        teamService.saveTeam(team);
        return redirectToTeams;
    }

    @GetMapping("/{teamId}/edit")
    public String editTeamForm(@PathVariable("teamId") Integer teamId, Model model) {
        model.addAttribute("team", teamService.findOneTeam(teamId));
        return "team/team-update";
    }

    @PatchMapping("/{teamId}")
    public String updateTeam(@ModelAttribute("team") @Valid Team team, BindingResult bindingResult,
                             @PathVariable int teamId) {
        if (bindingResult.hasErrors()) {
            return "team/team-update";
        }
        teamService.updateTeam(teamId, team);
        return redirectToTeams;
    }

    @DeleteMapping("/{teamId}")
    public String deleteTeam(@PathVariable("teamId") Integer teamId) {
        teamService.deleteTeamById(teamId);
        return redirectToTeams;
    }


    @GetMapping("/{id}")
    public String showOneTeam(@PathVariable("id") int id, Model model, @ModelAttribute("athlete") Athlete athlete) {
        model.addAttribute("team", teamService.findOneTeam(id));
        model.addAttribute("teamsInEvent", teamService.getEventsByTeamId(id));
        model.addAttribute("athletesInTeams", teamService.getAthletesByTeamId(id));
        model.addAttribute("events", eventService.findAllEvents());
        model.addAttribute("athletes", athleteService.findAllAthletes());

        return "team/showTeam";
    }

    @PatchMapping("/{id}/assignAthlete")
    public String addAthleteToTeam(@PathVariable("id") int id, @ModelAttribute("athlete") Athlete selectedAthlete) {

        teamService.assignAthleteToTeam(id, selectedAthlete);
        return redirectToTeams + id;
    }

    @PatchMapping("/{id}/{athleteId}/releaseAthlete")
    public String releaseAthleteFromTeam(@PathVariable("id") int id, @PathVariable("athleteId") int athleteId) {
        teamService.releaseAthleteFromTeam(id, athleteId);
        return "redirect:/teams/" + id;
    }

    @PatchMapping("/{id}/{eventId}/releaseEvent")
    public String releaseEventFromTeam(@PathVariable("id") int id, @PathVariable("eventId") int eventId) {
        teamService.releaseEventFromTeam(id, eventId);
        return "redirect:/teams/" + id;
    }


}
