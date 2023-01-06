package com.belavus.sportsresult.service;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AthleteService { // TODO: n.kvetko: perform code formatting

    private final AthleteRepository athleteRepository;

    @Autowired // TODO: n.kvetko: unnecessary annotation
    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }


    public List<Athlete> findAll() {
        return athleteRepository.findAll();
    }

    public void save(Athlete athlete) {
        athleteRepository.save(athlete);
    }

    public void deleteById(Integer id) {
        athleteRepository.deleteById(id);
    }

    public Athlete findOne(int id) {
        Optional<Athlete> foundAthlete = athleteRepository.findById(id);
        return foundAthlete.orElse(null);
    }

    public void update(int id, Athlete updateAthlete) {
        updateAthlete.setId(id);
        athleteRepository.save(updateAthlete);
    }
}

