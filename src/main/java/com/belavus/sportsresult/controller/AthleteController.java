package com.belavus.sportsresult.controller;


// TODO: n.kvetko: don't forget about code formatting (ctrl + alt + L --- być może, ale nie pamiętam)

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.service.AthleteService;
import com.belavus.sportsresult.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/athletes")
public class AthleteController {

    public static final String redirectToAthletes = "redirect:/athletes";
    private final AthleteService athleteService;
    private final TeamService teamService;

    public AthleteController(AthleteService athleteService, TeamService teamService) {
        this.athleteService = athleteService;
        this.teamService = teamService;
    }

    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("athletes", athleteService.findAll());
        return "athlete/athlete-list";
    }

    @GetMapping("/new")
    public String createAthleteForm(@ModelAttribute("athlete") Athlete athlete) {
        return "athlete/athlete-create";
    }

    @PostMapping("")
    public String createAthlete(@ModelAttribute("athlete") Athlete athlete) {
        athleteService.save(athlete);
        return redirectToAthletes;
    }

    @GetMapping("/{id}/edit")
    public String editAthleteForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("athlete", athleteService.findOne(id));
        return "athlete/athlete-update";
    }

    @PatchMapping("/{id}")
    public String updateAthlete(@ModelAttribute("athlete") Athlete athlete, @PathVariable("id") int id) {
        athleteService.update(id, athlete);
        return redirectToAthletes;
    }

    @DeleteMapping("/{id}")
    public String deleteAthlete(@PathVariable("id") int id) {
        athleteService.deleteById(id);
        return redirectToAthletes;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("team") Team team) {

        model.addAttribute("athlete", athleteService.findOne(id));
//        if (!athleteInTeam.isEmpty()) {
        model.addAttribute("teamsInAthlete", athleteService.getTeamsByAthleteId(id));
//        } else
        model.addAttribute("teams", teamService.findAll());

        return "athlete/showAthlete";
    }

    @PatchMapping("/{id}/assign")
    public String addAthleteToTeam(@PathVariable("id") int id, @ModelAttribute("teamId") Team selectedTeam) {

        athleteService.assignAthlete(id, selectedTeam);
        return "redirect:/athletes/" + id;
    }

    @PatchMapping("/{id}//{teamId}/release")
    public String releaseAthleteFromTeam(@PathVariable("id") int id, @PathVariable("teamId") int teamId) {
        athleteService.release(id, teamId);
        return "redirect:/athletes/" + id;
    }
}

