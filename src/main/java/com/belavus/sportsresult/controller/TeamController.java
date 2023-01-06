package com.belavus.sportsresult.controller;


import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;

import com.belavus.sportsresult.service.EventService;
import com.belavus.sportsresult.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    public static final String redirectToTeams = "redirect:/teams/";
    private final TeamService teamService;
    private final EventService eventService;


    @Autowired
    public TeamController(TeamService teamService, EventService eventService) {
        this.teamService = teamService;
        this.eventService = eventService;
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
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("event") Event event) {
        model.addAttribute("team", teamService.findOne(id));

//        List<Event> teamInEvent = teamService.getTeamInEvent(id);
//
//        if (teamInEvent != null)
//            model.addAttribute("teamInEvent", teamInEvent);
//        else
            model.addAttribute("events", eventService.findAll());

        return "team/showTeam";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("event") List<Event> selectedEvent) {
        // У selectedPerson назначено только поле id, остальные поля - null
        teamService.assign(id, selectedEvent);
        return redirectToTeams + id;
    }




    //    @ModelAttribute("eventId")
//    public Event findEventId(@PathVariable int eventId) {
//        return eventService.findById(eventId);
//    }

    //    @GetMapping("/teams")
//    public String findAll(Model model) {
//        List<Team> teams = teamService.findAll();
//        model.addAttribute("teams", teams);
//        return "team/team-list";
//    }
//    @GetMapping("/team-create")
//    public String createTeamForm(Team team) { // TODO: n.kvetko: code formatting and unused parameter
//        return "team/team-create";
//    }
//
//    @PostMapping("/team-create")
//    public String createTeam(Team team) {
//        teamService.saveTeam(team);
//        return "redirect:/teams"; // TODO: n.kvetko: String literals should not be duplicated
//    }
//
//    @GetMapping("team-delete/{id}")
//    public String deleteTeam(@PathVariable("id") Integer id) {
//        teamService.deleteById(id);
//        return "redirect:/teams"; // TODO: n.kvetko: String literals should not be duplicated
//    }
//
//    @GetMapping("/team-update/{id}")
//    public String updateTeamForm(@PathVariable("id") Integer id, Model model) {
//        Team team = teamService.findById(id);
//        model.addAttribute("team", team);
//        return "team/team-update";
//    }
//
//    @PostMapping("/team-update")
//    public String updateUser(Team team) {
//        teamService.saveTeam(team);
//        return "redirect:/teams";
//    }

// TODO: n.kvetko What to do with these line breaks?
}
