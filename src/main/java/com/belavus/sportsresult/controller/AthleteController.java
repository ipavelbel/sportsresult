package com.belavus.sportsresult.controller;



import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AthleteController {

    private final AthleteService athleteService;

    @Autowired
    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @GetMapping("/athletes")
    public String findAll(Model model){
        List<Athlete> athletes= athleteService.findAll();
        model.addAttribute("athletes", athletes);
        return "athlete/athlete-list";
    }

    @GetMapping("/athlete-create")
    public String createAthleteForm(Athlete athlete){
        return "athlete/athlete-create";
    }

    @PostMapping("/athlete-create")
    public String createAthlete(Athlete athlete){
        athleteService.saveAthlete(athlete);
        return "redirect:/athletes";
    }

    @GetMapping("athlete-delete/{id}")
    public String deleteAthlete(@PathVariable("id") Integer id){
        athleteService.deleteById(id);
        return "redirect:/athletes";
    }

    @GetMapping("/athlete-update/{id}")
    public String updateAthleteForm(@PathVariable("id") Integer id, Model model){
        Athlete athlete = athleteService.findById(id);
        model.addAttribute("athlete", athlete);
        return "athlete/athlete-update";
    }

    @PostMapping("/athlete-update")
    public String updateAthlete(Athlete athlete){
        athleteService.saveAthlete(athlete);
        return "redirect:/athletes";
    }
}
