package com.belavus.sportsresult.controller;



import com.belavus.sportsresult.model.Team;

import com.belavus.sportsresult.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @GetMapping("/teams")
    public String findAll(Model model){
        List<Team> teams = teamService.findAll();
        model.addAttribute("teams", teams);
        return "team/team-list";
    }

    @GetMapping("/team-create")
    public String createTeamForm(Team team){
        return "team/team-create";
    }

    @PostMapping("/team-create")
    public String createTeam(Team team){
        teamService.saveTeam(team);
        return "redirect:/teams";
    }

    @GetMapping("team-delete/{id}")
    public String deleteTeam(@PathVariable("id") Integer id){
        teamService.deleteById(id);
        return "redirect:/teams";
    }

    @GetMapping("/team-update/{id}")
    public String updateTeamForm(@PathVariable("id") Integer id, Model model){
        Team team = teamService.findById(id);
        model.addAttribute("team", team);
        return "team/team-update";
    }

    @PostMapping("/team-update")
    public String updateUser(Team team){
        teamService.saveTeam(team);
        return "redirect:/teams";
    }



}
