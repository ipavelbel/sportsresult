package com.belavus.sportsresult.service;



import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;

    @Autowired
    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    public Athlete findById(Integer id){
        return athleteRepository.getOne(id);
    }

    public List<Athlete> findAll(){
        return athleteRepository.findAll();
    }

    public Athlete saveAthlete(Athlete athlete){
        return athleteRepository.save(athlete);
    }

    public void deleteById(Integer id){
        athleteRepository.deleteById(id);
    }
}

