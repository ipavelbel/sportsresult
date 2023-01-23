package com.belavus.sportsresult.controller;

import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.service.EventService;
import com.belavus.sportsresult.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/competition")
public class GeneralController {

    private final EventService eventService;
    private final TeamService teamService;

    public GeneralController(EventService eventService, TeamService teamService) {
        this.eventService = eventService;
        this.teamService = teamService;
    }


    @GetMapping()
    public String showAll(Model model, @ModelAttribute("event") Event event, @ModelAttribute("team") Team team) {

        model.addAttribute("events", eventService.findAll());
        model.addAttribute("teams", teamService.findAll());

        return "welcome/generalPage";
    }

    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute("event") Event event, @ModelAttribute("team") Team team) {
        System.out.println(event.getId());
        System.out.println(team.getId());

        return "redirect:/first";
    }

//    private final TeamService teamService;
//
//
//    public GeneralController(TeamService teamService) {
//        this.teamService = teamService;
//    }
//
//
//    @GetMapping("")
//    public String findAllTeamsByEventId(@PathVariable(value = "id") int id, Model model) {
//        List<Team> teamsInEvent = teamService.getTeamsByEventId(id);
//        model.addAttribute("teamsInEvent", teamsInEvent);
////        Event eventWithId = teamService.getTeamWithEvent(id);
////        model.addAttribute("events", eventWithId);
//        return "team/team-list";
//    }
}
