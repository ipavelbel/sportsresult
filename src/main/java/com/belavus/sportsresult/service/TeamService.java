package com.belavus.sportsresult.service;



import com.belavus.sportsresult.model.Team;
import com.belavus.sportsresult.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team findById(Integer id){
        return teamRepository.getOne(id);
    }

    public List<Team> findAll(){
        return teamRepository.findAll();
    }

    public Team saveTeam(Team team){
        return teamRepository.save(team);
    }

    public void deleteById(Integer id){
        teamRepository.deleteById(id);
    }
}
